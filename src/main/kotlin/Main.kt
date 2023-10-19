import kotlin.math.max

const val EngineDeltaTime: Float = 0.0166667F
const val maxFall: Float = 160f
fun main() {
    printRightTables()
    println("Hello World!")
    val madeline = Madeline(00F,00F, 00F, 00F, state = PlayerState.StNormal)
//    update(madeline)

    val delta = 900f * EngineDeltaTime.toFloat()
    println(String.format("%.12f", delta))
    val initial = 15.000029563904.toFloat()
    val new = initial * 0.2.toFloat()

    val firstFall = initial + delta
    println(String.format("additions is: %.12f", firstFall))
    println(new)

    val secondFall = firstFall + delta
    println(String.format("additions is: %.12f", secondFall))
    println(new)

    val thirdFall = secondFall + delta
    println(String.format("additions is: %.12f", thirdFall))
    println(new)


    var wallSlideTimer = 1.2F
    for(i in 1..10) {
        val result = lerp(160F, 20F, wallSlideTimer / 1.2F)

        result.printAccurate()
        wallSlideTimer = max(wallSlideTimer - EngineDeltaTime, 0f)
    }


    val first = 21.944442749023F - 21.944444656372F
    first.printAccurate()
    val second = 23.888885498047F - 23.888881683350F
    second.printAccurate()
//    (first * 2).printAccurate()
    val third = 25.833328247070F - 25.833328247070F
    third.printAccurate()
    val fourth = 27.777770996094F - 27.777772903442F
    fourth.printAccurate()
    val fifth = 29.722213745117F - 29.722209930420F
    fifth.printAccurate()
}



fun Float.printAccurate() = println(String.format("%.12f", this))

fun printRightTables() {
    var wallSlideTimer = 1.2F
    var result = 0F
    for(i in 1..40) {
        result = lerp(160F, 20F, wallSlideTimer / 1.2F)

        result.printAccurate()
        wallSlideTimer = max(wallSlideTimer - EngineDeltaTime, 0f) - 0.000001907349F
        result = lerp(160F, 20F, wallSlideTimer / 1.2F)

        result.printAccurate()
        wallSlideTimer = max(wallSlideTimer - EngineDeltaTime, 0f) + 0.000003814697F
        result = lerp(160F, 20F, wallSlideTimer / 1.2F)

        result.printAccurate()
        wallSlideTimer = max(wallSlideTimer - EngineDeltaTime, 0f)

    }

}