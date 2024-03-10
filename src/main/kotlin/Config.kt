object Config {
    // this is an example initial condition with y = -3695.274893701076 as InfoHud value
    val initialConditions: List<Madeline> = listOf(
        createYMadeline(-3702F, -0.007295966148F, 120.000450134277F, PlayerState.StNormal) { 1F }.
        also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 15L" },
        createYMadeline(-3701F, -0.757295131684F, 120.000450134277F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 1,\n 1L,\n 1R,\n 1LJ,\n 15L setup2" },

        createYMadeline(-3703F, -0.257300972938F, 112.500434875488F, PlayerState.StNormal) { 1F }.
        also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 5L,\n 1LJ,\n 9L" },
        createYMadeline(-3703F, -0.132300496102F, 112.500434875488F, PlayerState.StNormal) { 1F }.
        also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 6L,\n 1LJ,\n 8L" },
        createYMadeline(-3703F, -0.007300138474F, 112.500434875488F, PlayerState.StNormal) { 1F }.
        also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 7L,\n 1LJ,\n 7L" },
        createYMadeline(-3702F, -0.882299423218F, 112.500434875488F, PlayerState.StNormal) { 1F }.
        also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 8L,\n 1LJ,\n 6L" },
        createYMadeline(-3702F, -0.757298946380F, 112.500434875488F, PlayerState.StNormal) { 1F }.
        also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 9L,\n 1LJ,\n 5L" },

        createYMadeline(-3703F, -0.007300138474F, 112.500434875488F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 1,\n 1L,\n 1R,\n 1LJ,\n 5L,\n 1LJ,\n 9L setup2" },
        createYMadeline(-3703F, -0.882299423218F, 112.500434875488F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 1,\n 1L,\n 1R,\n 1LJ,\n 6L,\n 1LJ,\n 8L setup2" },
        createYMadeline(-3703F, -0.757298946380F, 112.500434875488F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 1,\n 1L,\n 1R,\n 1LJ,\n 7L,\n 1LJ,\n 7L setup2" },
        createYMadeline(-3702F, -0.632298469544F, 112.500434875488F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 1,\n 1L,\n 1R,\n 1LJ,\n 8L,\n 1LJ,\n 6L setup2" },
        createYMadeline(-3702F, -0.507297992706F, 112.500434875488F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 1,\n 1L,\n 1R,\n 1LJ,\n 9L,\n 1LJ,\n 5L setup2" },

        createYMadeline(-3703F, -0.382301330566F, 105.000419616699F, PlayerState.StNormal) { 1F }.
        also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 9L,\n 2LJ,\n 4L" },

        /*
        createYMadeline(-3703F, -0.298954129219F, 105.000419616699F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 8L,\n 2LJ,\n 5L" },
        createYMadeline(-3703F, -0.173953652382F, 105.000419616699F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 8L,\n 1LJ,\n 1L,\n 1LJ,\n 4L" },
        createYMadeline(-3703F, -0.423954606056F, 105.000419616699F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 1L,\n 1,\n 1R,\n 1LJ,\n 7L,\n 1LJ,\n 1L,\n 1LJ,\n 5L" },
        createYMadeline(-3702F, 0.548947572708F, 105.000419616699F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
        also { it.InitialInputs = "\n 1Lj,\n 1,\n 1R,\n 2L,\n 1LJ,\n 14L" },
        createYMadeline(-3703F, -0.374941349030F, 105.000419616699F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
        also { it.InitialInputs = "\nLong2 \n 1R,\n 1LJ,\n 9L,\n 2LJ,\n 4L" },
        createYMadeline(-3703F, -0.499941825866F, 105.000419616699F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
        also { it.InitialInputs = "\nLong2 \n 1R,\n 1LJ,\n 8L,\n 1LJ,\n 1L,\n 1LJ,\n 4L" },


        createYMadeline(-3703F, -0.173950314522F, 97.500404357910F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
        also { it.InitialInputs = "\n 1LJ,\n 1,\n 1R,\n 2L,\n 1LJ,\n 9L,\n 1LJ,\n 4L" },
        createYMadeline(-3703F, -0.298950791359F, 97.500404357910F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
        also { it.InitialInputs = "\n 1LJ,\n 1,\n 1R,\n 2L,\n 1LJ,\n 8L,\n 1LJ,\n 5L" },
        createYMadeline(-3703F, -0.423951148987F, 97.500404357910F, PlayerState.StNormal) { 0F }. also { it.frame = 1 }.
        also { it.InitialInputs = "\n 1LJ,\n 1,\n 1R,\n 2L,\n 1LJ,\n 7L,\n 1LJ,\n 6L" },

        createYMadeline(-3703F, -0.257284998894F, 90.000389099121F, PlayerState.StNormal) { 0F }. also { it.frame = 3 }.
        also { it.InitialInputs = "\n 1LJ,\n 2,\n 1R,\n 2L,\n 1LJ,\n 8L,\n 4LJ,\n 3L" },

         */
    )
    // target ranges can not cut across 0.5
    val targets: List<Target> = listOf(
        Target(-0.32845F, -0.32838F, -3692F),
        Target(-0.15688F, -0.15681F, -3695F),
        Target(0.01474F, 0.01468F, -3698F)

        /*
        Target(-0.328422635794F, -0.328415542841F, -3692F),
        Target(-0.156855210662F, -0.156848177314F, -3695F),
        Target(0.014712393284F, 0.014719247818F, -3698F)

         */
    )
    // number of frames to bruteforce
    const val maxDepth: Int = 20
    // useful if y-pos shouldn't change after manip is over
    val endWithGrab = false
    // target subpixel only or exact position
    val solutionSetting = SolutionSetting.ExactPosition

    // advanced
    val noGrabFrames: Set<Int> = setOf(13, 17, 21, 25, 29, 33, 37)
    val noSlideFrames: Set<Int> = setOf(2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val offsets: List<Madeline.() -> Unit> = listOf(
        { },
        { this.ySpeed = -105F; this.updatePosition() },
        { this.ySpeed = -89.999969482422F; this.updatePosition() },
        { this.ySpeed = -105F; this.updatePosition(); this.updatePosition() },
        { this.ySpeed = -105F; this.updatePosition(); this.ySpeed = -89.999969482422F; this.updatePosition() },
    )
}

enum class SolutionSetting {
    ExactPosition,
    SubpixelOnly
}
