

data class Madeline(
    var x: Float,
    var y: Float,
    var xVelocity: Float,
    var yVelocity: Float,
    var state: PlayerState,
)

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