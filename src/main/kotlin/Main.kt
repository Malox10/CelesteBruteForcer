import kotlin.math.sign

const val maxDepth = 22
val noGrabFrames = setOf(12, 16, 20, 24, 28, 32, 36)
fun main() {
    val solutions = simulateAll(
        listOf(
            createYMadeline(-3701F, 0.673947572708F, 120.000450134277F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 15L" },
            createYMadeline(-3701F, 0.548947095871F, 120.000450134277F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1LJ,\n 1,\n 1R,\n 1LJ,\n 15L" },
            createYMadeline(-3701F, 0.798948287964F, 120.000450134277F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1LJ,\n 1J,\n 1R,\n 1LJ,\n 15L" },
            createYMadeline(-3703F, -0.423951268196F, 120.000450134277F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
            also { it.InitialInputs = "\n 1LJ,\n 1J,\n 1R,\n 2LJ,\n 15L" },
            createYMadeline(-3703F, -0.423943161964F, 120.000450134277F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
            also { it.InitialInputs = "\n 1LJ,\n 2,\n 1R,\n 1LJ,\n 15L" },
            createYMadeline(-3703F, -0.090609192848F, 120.000450134277F, PlayerState.StNormal) { 0F }. also { it.frame = 2 }.
            also { it.InitialInputs = "\n 1LJ,\n 2,\n 1R,\n 1L,\n 1LJ,\n 15L" },

            createYMadeline(-3702F, 0.923952579498F, 112.500434875488F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 5L,\n 1LJ,\n 9L" },
            createYMadeline(-3702F, 0.798952102661F, 112.500434875488F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 6L,\n 1LJ,\n 8L" },
            createYMadeline(-3702F, 0.673951625824F, 112.500434875488F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 7L,\n 1LJ,\n 7L" },
            createYMadeline(-3702F, 0.548951148987F, 112.500434875488F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 8L,\n 1LJ,\n 6L" },
            createYMadeline(-3702F, -0.423950791359F, 112.500434875488F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 9L,\n 1LJ,\n 5L" },
            createYMadeline(-3702F, -0.298950314522F, 112.500434875488F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1LJ,\n 1,\n 1R,\n 1LJ,\n 9L,\n 1LJ,\n 5L" },

            createYMadeline(-3703F, -0.048953175544F, 105.000419616699F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 9L,\n 2LJ,\n 4L" },
            createYMadeline(-3703F, -0.298954129219F, 105.000419616699F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 8L,\n 2LJ,\n 5L" },
            createYMadeline(-3703F, -0.173953652382F, 105.000419616699F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 8L,\n 1LJ,\n 1L,\n 1LJ,\n 4L" },
            createYMadeline(-3703F, -0.423954606056F, 105.000419616699F, PlayerState.StNormal) { 0F }.
            also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 7L,\n 1LJ,\n 1L,\n 1LJ,\n 5L" },
            createYMadeline(-3702F, 0.548947572708F, 105.000419616699F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
            also { it.InitialInputs = "\n 1Lj,\n 1,\n 1R,\n 2L,\n 1LJ,\n 14L" },

            createYMadeline(-3703F, -0.173950314522F, 97.500404357910F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
            also { it.InitialInputs = "\n 1LJ,\n 1,\n 1R,\n 2L,\n 1LJ,\n 9L,\n 1LJ,\n 4L" },
            createYMadeline(-3703F, -0.298950791359F, 97.500404357910F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
            also { it.InitialInputs = "\n 1LJ,\n 1,\n 1R,\n 2L,\n 1LJ,\n 8L,\n 1LJ,\n 5L" },
            createYMadeline(-3703F, -0.423951148987F, 97.500404357910F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
            also { it.InitialInputs = "\n 1LJ,\n 1,\n 1R,\n 2L,\n 1LJ,\n 7L,\n 1LJ,\n 6L" },

            createYMadeline(-3703F, -0.257284998894F, 90.000389099121F, PlayerState.StNormal) { 0F }. also { it.frame = 3 }.
            also { it.InitialInputs = "\n 1LJ,\n 2,\n 1R,\n 2L,\n 1LJ,\n 8L,\n 4LJ,\n 3L" },
        ),
        listOf(
            Target(-0.328422635794F, -0.328415542841F, -3692F),
            Target(-0.156855210662F, -0.156848177314F, -3695F),
            Target(0.014712393284F, 0.014719247818F, - 3698F)
        ), //lambda Madeline -> Bool as success filter
        listOf(
            { },
            { this.ySpeed = -105F; this.updatePosition() },
            { this.ySpeed = -89.999969482422F; this.updatePosition() },
            { this.ySpeed = -105F; this.updatePosition(); this.updatePosition() },
            { this.ySpeed = -105F; this.updatePosition(); this.ySpeed = -89.999969482422F; this.updatePosition() },
        )
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
        println(
            "With Initial Condition:\n" +
                    "YSpeed: ${start.ySpeed}, " +
                    "YPixel: ${start.y}, " +
                    "YMovementCounter: ${start.yMovementCounter}, " +
                    "InitialInputs: ${start.InitialInputs} "
        )
//        println("With Initial Condition: $start")
        println()
    }

    println("Number of solutions found: ${solutions.size}")
}

fun simulateAll(startingPositions: List<Madeline>, targets: List<Target>, offsets: List<Madeline.() -> Unit>): List<Pair<Pair<Madeline, InputSequence>, Madeline>> {
    val allSolutions = startingPositions.flatMap { startingState ->
        val simulator = Simulator()
        simulator.simulate(startingState, targets, offsets)
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