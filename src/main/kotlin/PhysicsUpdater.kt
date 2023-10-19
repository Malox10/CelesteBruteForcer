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
            madeline.ySpeed = approach(madeline.ySpeed, 00F, 900f * EngineDeltaTime)
        }
        PlayerState.StNormal -> {
            madeline.ySpeed *= 0.2F
            madeline.state = PlayerState.StClimb
        }
    }
}

fun handleNone(madeline: Madeline) {
    when(madeline.state) {
        PlayerState.StClimb -> {
            madeline.state = PlayerState.StNormal
        }
        PlayerState.StNormal -> {
            madeline.ySpeed = approach(madeline.ySpeed, maxFall, 900f * EngineDeltaTime)
            madeline.state = PlayerState.StNormal
        }
    }
}

fun handleRight(madeline: Madeline) {
    when(madeline.state) {
        PlayerState.StClimb -> {
            madeline.state = PlayerState.StNormal
        }
        PlayerState.StNormal -> {
            var wallSlideTimer = 1.2F
            for(i in 1..10) {
                val result = lerp(160F, 20F, wallSlideTimer / 1.2F)

                result.printAccurate()
                wallSlideTimer = max(wallSlideTimer - EngineDeltaTime, 0f)
            }
            val target = madeline.updateWallSlideTimer()
            madeline.ySpeed = approach(madeline.ySpeed, target, 900f * EngineDeltaTime)
            madeline.state = PlayerState.StNormal
        }
    }
}





fun approach(value: Float, target: Float, maxMove: Float): Float {
    return if (value <= target) {
        Math.min(value + maxMove, target)
    } else Math.max(value - maxMove, target)
}



fun lerp(value1: Float, value2: Float, amount: Float) = (value1.toDouble() + (value2.toDouble() - value1.toDouble()) * amount).toFloat()