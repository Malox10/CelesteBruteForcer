import kotlin.math.abs
import kotlin.math.sign
import kotlin.system.measureTimeMillis

fun main() {
    val time = measureTimeMillis {
        val solutions = simulateAll(
            Config.initialConditions,
            Config.targets,
            Config.offsets
        )
        //solutions are now printed during runtime
        //printSolutions(solutions)
        //println("Number of solutions found: ${solutions.size}")
    }
    println("Total runtime in ms: $time")
}

fun printSolutions(solutions: List<Pair<Pair<Madeline, InputSequence>, Madeline>>) {
    solutions.map { it }.sortedBy { it.first.second.size }.forEach { (solution, start) ->
        printSolution(solution, start)
    }
}

fun printSolution(solution: Pair<Madeline, InputSequence>, start: Madeline) {
    val (end, sequence) = solution
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

fun simulateAll(startingPositions: List<Madeline>, targets: List<Target>, offsets: List<Madeline.() -> Unit>): List<Pair<Pair<Madeline, InputSequence>, Madeline>> {
    val simulator = Simulator()
    val allSolutions = startingPositions.flatMap { startingState ->
        simulator.initialMadeline = startingState
        simulator.simulate(startingState, targets, offsets)
        simulator.solutions.map { it.value to startingState }
    }
    println("Number of unique solutions found: ${simulator.solutions.size}")

    return allSolutions
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