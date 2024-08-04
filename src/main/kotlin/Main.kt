import kotlin.math.abs
import kotlin.math.sign
import kotlin.system.measureTimeMillis


const val useConfigFile = true
val config = if(useConfigFile) parseConfig() else Config()
fun main() {
    val time = measureTimeMillis {
        simulateAll(
            config.initialConditions,
            config.targets,
            config.offsets
        )
    }
    println("Total runtime in ms: $time")
}

fun printSolution(solution: Solution, additionalMove: Madeline.() -> Unit) {
    val end = solution.endState
    val sequence = solution.inputSequence
    val start = solution.initialMadeline
    end.printExact()
    println(sequence)
    println()
    println(
        "With Initial Condition:\n" +
                "YSpeed: ${start.ySpeed}, " +
                "YPixel: ${start.y}, " +
                "YMovementCounter: ${start.yMovementCounter}, " +
                "InitialInputs: ${start.InitialInputs} "
    )
    println()
    println("steps taken: ${sequence.size}")
    println(sequence.toTasFile())
    println()
}

fun simulateAll(startingPositions: List<Madeline>, targets: List<Target>, offsets: List<Madeline.() -> Unit>) {
    val simulator = Simulator()
    startingPositions.forEach { startingState ->
        simulator.initialMadeline = startingState
        simulator.simulate(startingState, targets, offsets)
    }
    // print results
    println("----------------- SIMULATION RESULTS -----------------")
    println()
    println("Number of unique solutions found: ${simulator.solutions.size}")
    val countingArray: Array<Int> = Array<Int>(config.maxDepth + 1) { 0 }
    var fastestSolution: Solution? = null
    simulator.solutions.forEach {
        val length = it.value.inputSequence.size
        countingArray[length] += 1
        if (fastestSolution == null) {
            fastestSolution = it.value
        } else {
            if (length < fastestSolution!!.inputSequence.size) {
                fastestSolution = it.value
            }
        }
    }
    if (fastestSolution != null) {
        println("solution distribution:")
        for (i in 0..config.maxDepth) {
            if (countingArray[i] != 0) {
                println("$i-frame solutions: ${countingArray[i]}")
            }
        }
        println()
        println("FASTEST SOLUTION FOUND:")
        printSolution(fastestSolution!!, {})
    }
}

fun List<FrameInputs>.toTasFile(): String {
    val output = mutableListOf<String>("--- TAS starts here ---")

    var counter = 1
    var lastInput: FrameInputs = this.first()
    for (frameInputs in this.drop(1)) {
        if (frameInputs == lastInput) {
            counter++
        } else {
            //output.add("$counter, ${lastInput.TASkey}")
            var tasInputLine = "$counter"
            for (input in lastInput) {
                if (input != Input.None) {
                    tasInputLine += ",${input.TASkey}"
                }
            }
            output.add(tasInputLine)
            counter = 1
            lastInput = frameInputs
        }
    }

    var tasInputLine = "$counter"
    for (input in lastInput) {
        if (input != Input.None) {
            tasInputLine += ",${input.TASkey}"
        }
    }
    output.add(tasInputLine)
    output.add("--- TAS ends here ---")
    return output.joinToString("\n")
}

fun Float.printAccurate() = println(String.format("%.12f", this))

fun createYMadeline(
    y: Float,
    ySubpixel: Float,
    ySpeed: Float,
    state: PlayerState,
    yLiftBoost: (Int) -> Float = { 0F },
): Madeline {
    var trueYSubPixel = ySubpixel
    var trueYPixel = y


    if (abs(ySubpixel) > 0.5F) {
        trueYSubPixel = (1F - abs(ySubpixel)) * (sign(ySubpixel) * -1)
        trueYPixel += sign(ySubpixel) * 1
    }

    return Madeline(0F, 0F, trueYPixel, trueYSubPixel, 0F, ySpeed, state, yLiftBoost)
}