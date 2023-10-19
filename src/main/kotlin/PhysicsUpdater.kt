import kotlin.math.max

fun Madeline.update(input: Input) {
    when(input) {
        Input.Grab -> handleGrab(this)
        Input.None -> handleNone(this)
        Input.Right -> handleRight(this)
    }

//    updatePosition()
}

fun handleGrab(madeline: Madeline) {
    when(madeline.state) {
        PlayerState.StClimb -> {
            madeline.yVelocity = approach(madeline.yVelocity, 00F, 900f * EngineDeltaTime)
        }
        PlayerState.StNormal -> {
            madeline.yVelocity *= 0.2F
            madeline.state = PlayerState.StClimb
        }
    }
}

fun handleNone(madeline: Madeline) {
    TODO()
}

fun handleRight(madeline: Madeline) {
    TODO()
}


fun approach(value: Float, target: Float, maxMove: Float): Float {
    return if (value <= target) {
        Math.min(value + maxMove, target)
    } else Math.max(value - maxMove, target)
}



fun lerp(value1: Float, value2: Float, amount: Float) = (value1.toDouble() + (value2.toDouble() - value1.toDouble()) * amount).toFloat()