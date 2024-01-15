import kotlin.math.sign

const val maxDepth = 30
val noGrabFrames = setOf(12, 16, 20, 24, 28, 32, 36)
fun main() {
    val solutions = simulateAll(
        listOf(
            createYMadeline(-3701F, 0.673947572708F, 120.000450134277F, PlayerState.StNormal) { 0F },
            createYMadeline(-3702F, 0.923952579498F, 112.500434875488F, PlayerState.StNormal) { 0F },
            createYMadeline(-3703F, -0.048953175544F, 105.000419616699F, PlayerState.StNormal) { 0F },
            createYMadeline(-3703F, -0.298954129219F, 105.000419616699F, PlayerState.StNormal) { 0F },
            createYMadeline(-3703F, -0.173953652382F, 105.000419616699F, PlayerState.StNormal) { 0F },
        ),
        listOf(
            Target(-0.328422635794F, -0.328415542841F, -3692F),
            Target(-0.156855210662F, -0.156848177314F, -3696F)
        ), //lambda Madeline -> Bool as success filter
    )

    //val maddy1flate = createYMadeline(-3703F, -0.173953652382F, 105.000419616699F, PlayerState.StNormal) { 0F }
//    simulate(
////        createYMadeline(-3701F, 0.673947572708F, 120.000450134277F, PlayerState.StNormal) { 0F },
////        createYMadeline(-3702F, 0.923952579498F, 112.500434875488F, PlayerState.StNormal) { 0F },
////        createYMadeline(-3703F, -0.048953175544F, 105.000419616699F, PlayerState.StNormal) { 0F },
////        createYMadeline(-3703F, -0.298954129219F, 105.000419616699F, PlayerState.StNormal) { 0F },
//        createYMadeline(-3703F, -0.173953652382F, 105.000419616699F, PlayerState.StNormal) { 0F },
//        listOf(
//            Target(-0.328422635794F, -0.328415542841F, -3692F),
//            Target(-0.156855210662F, -0.156848177314F, - 3696F)
//        ), //lambda Madeline -> Bool as success filter
//    )

    solutions.map { it }.sortedBy { it.first.second.size }.forEach { (solution, start) ->
        val (end, sequence) = solution
        println(end)
        end.printExact()
        println(sequence)
        println()
        println("steps taken: ${sequence.size}")
        println(sequence.toTasFile())
        println("With Initial Condition: $start")
        println()
    }

    println(solutions.size)
}

fun simulateAll(startingPositions: List<Madeline>, targets: List<Target>): List<Pair<Pair<Madeline, InputSequence>, Madeline>> {
    val allSolutions = startingPositions.flatMap { startingState ->
        val simulator = Simulator()
        simulator.simulate(startingState, targets)
        simulator.solutions.map { it.value to startingState }
    }

    return allSolutions
}


/*
initial conditions:
createYMadeline(-3701F, 0.673947572708F, 120.000450134277F, PlayerState.StNormal) { 0F },
createYMadeline(-3703F, 0.923952579498F, 112.500434875488F, PlayerState.StNormal) { 0F },

targets:
Target(-0.328422635794F, -0.328415542841F, -3693F)
Target(-0.156855210662, -0.156848177314F, - 3696F)
 */


/*
fun secondWorldAbyssSaveWIP() {
    simulate(
        createYMadeline(-3702F, 0.411834239960F, 112.500434875488F, PlayerState.StNormal),
//        createYMadeline(-3701F, 0.838162422180F, 120.000450134277F, PlayerState.StNormal),
//        Madeline(0F, 0F, -3701F, 0.838162422180F, 0F, 120.000450134277F, PlayerState.StNormal),
        Target(-0.328422665600F, -0.328420996670F), //lambda Madeline -> Bool as success filter
    )
}

fun firstWorldAbyssSave() {
    simulate(
        Madeline(0F, 0F, 345F, 0.400036633015F, 0F, 24.000047683716F, PlayerState.StClimb),
        Target(0.639446F, 0.6397066F), //lambda Madeline -> Bool as success filter
    )
}

 */

fun List<Input>.toTasFile(): String {
    val output = mutableListOf<String>("--- TAS starts here ---")

    var counter = 1
    var lastInput: Input = this.first()
    for (input in this.drop(1)) {
        if (input == lastInput) {
            counter++
        } else {
            output.add("$counter, ${lastInput.TASkey}")
            counter = 1
            lastInput = input
        }
    }

    output.add("$counter, ${lastInput.TASkey}")
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
    val trueYSubPixel = if (ySubpixel > 0.5) sign(y) * (ySubpixel - 1) else ySubpixel
    val trueYPixel = if (ySubpixel > 0.5) y + sign(y) else y
    return Madeline(0F, 0F, trueYPixel, trueYSubPixel, 0F, ySpeed, state, yLiftBoost)
}