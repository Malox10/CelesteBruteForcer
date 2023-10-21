import kotlin.system.measureTimeMillis

fun main() {
    println(measureTimeMillis {
        simulate(
            Madeline(0F, 0F, 345F, 0.400036633015F, 0F, 24.000047683716F, PlayerState.StClimb),
            Target(0.639446F, 0.6397066F), //lambda Madeline -> Bool as success filter
            20,
        )
    })

    solutions.map { it }.sortedBy { it.key.ySubPixel }.forEach { (key, value) ->
        println(key)
        value.first.printExact()
        println(value.second)
        println()
        println(value.second.toTasFile())
    }

    println(solutions.size)
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
