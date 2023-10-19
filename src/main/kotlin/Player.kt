import kotlin.math.max

data class Madeline(
    var x: Float,
    var y: Float,
    var xSpeed: Float,
    var ySpeed: Float,
    var state: PlayerState
) {
    var wallSlideTimer = 1.2F

    fun updateWallSlideTimer(): Float {
        val target = lerp(160F, 20F, wallSlideTimer / 1.2F)
        wallSlideTimer = max(wallSlideTimer - EngineDeltaTime, 0f)
        return target
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