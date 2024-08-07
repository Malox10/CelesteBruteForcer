import kotlin.math.roundToInt

fun Madeline.update(input: List<Input>) {
    this.frame++


    if (input.contains(Input.Grab)) { handleGrab(this, input) }
    if (input.contains(Input.None)) { handleNone(this, input) }
    if (input.contains(Input.Right)) { handleRight(this, input) }

    /*
    when(input) {
        Input.Grab -> handleGrab(this)
        Input.None -> handleNone(this)
        Input.Right -> handleRight(this)
    }

     */

    this.updatePosition()
}

fun Madeline.updatePosition() {
    val moveVAmount = ySpeed * EngineDeltaTime
    this.moveV(moveVAmount)
}

fun Madeline.moveV(amount: Float) {
    this.yMovementCounter += amount
    val num = this.yMovementCounter.toDouble().roundToInt() //MidpointRounding.ToEven can't specify rounding Behaviour
    if (num != 0) {
        this.yMovementCounter -= num.toFloat()
        this.y += num
        if (config.yGround != null && this.y > config.yGround) {
            this.y = config.yGround
            this.yMovementCounter = 0F
            this.ySpeed = 0F
        }
    }
}

fun handleGrab(madeline: Madeline, input: List<Input>) {
    when(madeline.state) {
        PlayerState.StClimb -> {

             if (false) {
            // if (madeline.y < -3697) {
                madeline.ySpeed = approach(madeline.ySpeed, 30F, 900f * EngineDeltaTime)
                madeline.state = PlayerState.StClimb
            } else {
                madeline.ySpeed = approach(madeline.ySpeed, 00F, 900f * EngineDeltaTime)
                madeline.state = PlayerState.StClimb
            }

            /*
            madeline.ySpeed = approach(madeline.ySpeed, 00F, 900f * EngineDeltaTime)
            madeline.state = PlayerState.StClimb
             */
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

fun handleNone(madeline: Madeline, input: List<Input>) {
    when(madeline.state) {
        PlayerState.StClimb -> {
            madeline.state = PlayerState.StNormal

            //apply liftboost
            val yLiftBoost = madeline.yLiftboost(madeline.frame)
            if(yLiftBoost != 0F) {
                madeline.ySpeed += yLiftBoost
            }
        }
        PlayerState.StNormal -> {
            if (madeline.y != config.yGround) {
                if (input.contains(Input.Jump) && madeline.ySpeed < 40f && madeline.ySpeed > -40f) {
                    madeline.ySpeed = approach(madeline.ySpeed, maxFall, 450f * EngineDeltaTime)
                } else {
                    madeline.ySpeed = approach(madeline.ySpeed, maxFall, 900f * EngineDeltaTime)
                }
            }
            madeline.state = PlayerState.StNormal
        }
    }
}

fun handleRight(madeline: Madeline, input: List<Input>) {
    when(madeline.state) {
        PlayerState.StClimb -> {
            madeline.state = PlayerState.StNormal

            //apply liftboost
            val yLiftBoost = madeline.yLiftboost(madeline.frame)
            if(yLiftBoost != 0F) {
                madeline.ySpeed += yLiftBoost
            }
        }
        PlayerState.StNormal -> {
            if (madeline.y != config.yGround) {
                val target = madeline.updateWallSlideTimer()
                if (input.contains(Input.Jump) && madeline.ySpeed < 40f && madeline.ySpeed > -40f) {
                    madeline.ySpeed = approach(madeline.ySpeed, target, 450f * EngineDeltaTime)
                } else {
                    madeline.ySpeed = approach(madeline.ySpeed, target, 900f * EngineDeltaTime)
                }
            }
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
