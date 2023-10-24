import kotlin.math.sign

const val yLiftboost: Float = -40f //set to 0 if no liftboost
fun main() {
    simulate(
        createYMadeline(-2453F, -0.016500830650F, 0F, PlayerState.StNormal),
        Target(-0.080259978771F, -0.080258071423F), //lambda Madeline -> Bool as success filter
        30
        )
    //0.328420996670F

    //0.32842165
    solutions.map { it }.sortedBy { it.value.second.size }.forEach { (key, value) ->
        println(key)
        value.first.printExact()
        println(value.second)
        println()
        println("steps taken: ${value.second.size}")
        println(value.second.toTasFile())
    }

    println(solutions.size)
}

fun secondWorldAbyssSaveWIP() {
    simulate(
        createYMadeline(-3702F, 0.411834239960F, 112.500434875488F, PlayerState.StNormal),
//        createYMadeline(-3701F, 0.838162422180F, 120.000450134277F, PlayerState.StNormal),
//        Madeline(0F, 0F, -3701F, 0.838162422180F, 0F, 120.000450134277F, PlayerState.StNormal),
        Target(-0.328422665600F, -0.328420996670F), //lambda Madeline -> Bool as success filter
        25
    )
}

fun firstWorldAbyssSave() {
    simulate(
        Madeline(0F, 0F, 345F, 0.400036633015F, 0F, 24.000047683716F, PlayerState.StClimb),
        Target(0.639446F, 0.6397066F), //lambda Madeline -> Bool as success filter
        20
    )
}

fun List<Input>.toTasFile(): String {
    val output = mutableListOf<String>("--- TAS starts here ---")

    var counter = 1
    var lastInput: Input = this.first()
    for(input in this.drop(1)) {
        if(input == lastInput) {
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

fun createYMadeline(y: Float, ySubpixel: Float, ySpeed: Float, state: PlayerState): Madeline {
    val trueYSubPixel = if (ySubpixel > 0.5) sign(y) * (ySubpixel - 1) else ySubpixel
    val trueYPixel = if (ySubpixel > 0.5) y + sign(y) else y
    return Madeline(0F, 0F, trueYPixel, trueYSubPixel, 0F, ySpeed, state)
}