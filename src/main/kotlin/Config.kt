object Config {
    val initialConditions: List<Madeline> = listOf(
        createYMadeline(-3695F, -0.274893701076F, 0F, PlayerState.StClimb)
    )
    // target ranges can not cross 0.5
    val targets: List<Target> = listOf(
        Target(-0.70001F, -0.70002F, 355F)
    )
    const val maxDepth: Int = 21
    val endWithGrab = true

    // advanced
    val noGrabFrames: Set<Int> = setOf()
    val noSlideFrames: Set<Int> = setOf()
    val offsets: List<Madeline.() -> Unit> = listOf(
        { }
    )
}