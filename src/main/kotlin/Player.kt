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
        val actualXMovementCounter = if(xMovementCounter < 0) xMovementCounter + 1 else xMovementCounter
        val actualX = if(xMovementCounter < 0) x - 1 else x

        val actualYMovementCounter = if(yMovementCounter < 0) yMovementCounter + 1 else yMovementCounter
        val actualY = if(yMovementCounter < 0) y - 1 else y

        val newY = y + yMovementCounter
        val newYMovementCounter = if(newY < 0) {
            1F - yMovementCounter
        } else {
            yMovementCounter
        }

        println("double y: ${y.toDouble() + yMovementCounter.toDouble()}")
        println("x: ${(actualX - actualXMovementCounter).toInt()}" + String.format("%.12f", actualXMovementCounter).toCharArray().map { it }.drop(1).toCharArray().concatToString())
        println("y: ${newY.toInt()} ySubPixel: " + String.format("%.12f", newYMovementCounter))//.toCharArray().map { it }.drop(1).toCharArray().concatToString())
        print("xSpeed: "); xSpeed.printAccurate()
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