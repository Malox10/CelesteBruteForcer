object Config {
    // this is an example initial condition with y = -3695.274893701076 as InfoHud value
    val initialConditions: List<Madeline> = listOf(

        createYMadeline(93F, 0.750072956085F, 105.000419616699F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "" }. also { it.slowfallHeld = true }

    )
    // target ranges can not cut across 0.5
    val targets: List<Target> = listOf(

        Target(0.196010157466F, 0.196006357670F, 100F)

    )
    // number of frames to bruteforce
    const val maxDepth: Int = 19
    // useful if y-pos shouldn't change after manip is over
    val endWithGrab = false
    // target subpixel only or exact position
    val solutionSetting = SolutionSetting.ExactPosition

    // advanced settings
    val noGrabFrames: Set<Int> = setOf() //setOf(13, 17, 21, 25, 29, 33, 37)
    val noSlideFrames: Set<Int> = setOf() //setOf(2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val offsets: List<Madeline.() -> Unit> = listOf(
        { },
        /*
        { this.ySpeed = 7.5F; this.updatePosition() },
        { this.ySpeed = 15F; this.updatePosition() },
        { this.ySpeed = -105F; this.updatePosition() },

         */
        // { this.ySpeed = -89.999969482422F; this.updatePosition() },
        // { this.ySpeed = -105F; this.updatePosition(); this.updatePosition() },
        // { this.ySpeed = -105F; this.updatePosition(); this.ySpeed = -89.999969482422F; this.updatePosition() },
    )
}

enum class SolutionSetting {
    ExactPosition,
    SubpixelOnly
}
