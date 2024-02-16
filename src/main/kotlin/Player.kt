import kotlin.math.max

const val EngineDeltaTime: Float = 0.0166667F
const val maxFall: Float = 160f

data class Madeline(
    var x: Float,
    var xMovementCounter: Float = 0F, //should always be between 0.5, this could lead to bugs when initializing custom positions

    var y: Float,
    var yMovementCounter: Float = 0F, //should always be between 0.5, this could lead to bugs when initializing custom positions

    var xSpeed: Float,
    var ySpeed: Float,
    var state: PlayerState,

    var yLiftboost: (Int) -> Float = { 0F },
    var frame: Int = 0,
    var wallSlideTimer: Float = 1.2F,
    var InitialInputs: String = "empty"
) {
    fun updateWallSlideTimer(): Float {
        val target = lerp(160F, 20F, wallSlideTimer / 1.2F)
        wallSlideTimer = max(wallSlideTimer - EngineDeltaTime, 0f)
        return target
    }

    fun printExact() {
        println("double y: ${y.toDouble() + yMovementCounter.toDouble()}")
        println("yPixel: ${y.toInt()} yMovementCounter: " + String.format("%.12f", yMovementCounter))//.toCharArray().map { it }.drop(1).toCharArray().concatToString())
        print("ySpeed: "); ySpeed.printAccurate()
        print("state: "); println(state)
    }

    fun printRaw() {
        println("x: ${x.toInt()}, xMovementCounter: " + String.format("%.12f", xMovementCounter))
        println("y: ${y.toInt()}, yMovementCounter: " + String.format("%.12f", yMovementCounter))
        print("xSpeed: "); xSpeed.printAccurate()
        print("ySpeed: "); ySpeed.printAccurate()
        print("state: "); println(state)
    }
}

enum class PlayerState {
    StClimb,
    StNormal
}

enum class Input(val TASkey: String) {
    Grab("G"),
    None(""),
    Right("R"),
//    Left("L")
}