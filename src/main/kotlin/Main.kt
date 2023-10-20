fun main() {
    simulate(
        Madeline(0F, 0F, 345F, 0.400036633015F, 0F, 24.000047683716F, PlayerState.StClimb),
        15,
    )
}

fun Float.printAccurate() = println(String.format("%.12f", this))
fun Double.printAccurate() = println(String.format("%.12f", this))
