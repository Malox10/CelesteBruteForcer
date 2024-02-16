object Config {
    // this is an example initial condition with y = -3695.274893701076 as InfoHud value
    val initialConditions: List<Madeline> = listOf(
        createYMadeline(-3695F, -0.274893701076F, 0F, PlayerState.StClimb)
    )
    // target ranges can not cut across 0.5
    val targets: List<Target> = listOf(
        Target(-0.70001F, -0.70002F, -3687F)
    )
    // number of frames to bruteforce
    const val maxDepth: Int = 18
    // useful if y-pos shouldn't change after manip is over
    val endWithGrab = true
    // target subpixel only or exact position
    val solutionSetting = SolutionSetting.SubpixelOnly

    // advanced
    val noGrabFrames: Set<Int> = setOf()
    val noSlideFrames: Set<Int> = setOf()
    val offsets: List<Madeline.() -> Unit> = listOf(
        { }
    )
}

enum class SolutionSetting {
    ExactPosition,
    SubpixelOnly
}