import kotlin.math.roundToInt

fun Madeline.update(input: Input) {
    when(input) {
        Input.Grab -> handleGrab(this)
        Input.None -> handleNone(this)
        Input.Right -> handleRight(this)
    }

    this.updatePosition()
}

fun Madeline.updatePosition() {
    val moveV = ySpeed * EngineDeltaTime

    this.yMovementCounter += moveV
    val num = this.yMovementCounter.toDouble().roundToInt() //MidpointRounding.ToEven can't specify rounding Behaviour
    if (num != 0) {
        this.yMovementCounter -= num.toFloat()
        this.y += num
    }
}

fun handleGrab(madeline: Madeline) {
    when(madeline.state) {
        PlayerState.StClimb -> {
            madeline.ySpeed = approach(madeline.ySpeed, 00F, 900f * EngineDeltaTime)
            madeline.state = PlayerState.StClimb
        }
        PlayerState.StNormal -> {
            //can only grab if falling down or zero speed; then approach maxFall
            if(madeline.ySpeed < 0F) {
                madeline.ySpeed = approach(madeline.ySpeed, maxFall, 900f * EngineDeltaTime)
                madeline.state = PlayerState.StNormal
                return
            }

            madeline.ySpeed *= 0.2F
            madeline.state = PlayerState.StClimb
            madeline.wallSlideTimer = 1.2F
        }
    }
}

fun handleNone(madeline: Madeline) {
    when(madeline.state) {
        PlayerState.StClimb -> {
            madeline.state = PlayerState.StNormal

            //apply liftboost
            if(yLiftboost != 0F) {
                madeline.ySpeed += yLiftboost
            }
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

            //apply liftboost
            if(yLiftboost != 0F) {
                madeline.ySpeed += yLiftboost
            }
        }
        PlayerState.StNormal -> {
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
