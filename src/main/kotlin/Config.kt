object Config {
    // this is an example initial condition with y = -3695.274893701076 as InfoHud value
    val initialConditions: List<Madeline> = listOf(
        createYMadeline(-3701F, -0.752329349518F, 120.000450134277F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 15L" }. also { it.slowfallHeld = true },
        createYMadeline(-3701F, -0.752329349518F-0.125F, 120.000450134277F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 15L (1 8th higher)" }. also { it.slowfallHeld = true },
        createYMadeline(-3702F, -0.752329349518F+0.75F, 120.000450134277F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 15L (2 8th higher)" }. also { it.slowfallHeld = true },
        createYMadeline(-3702F, -0.752329349518F+0.875F, 120.000450134277F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 15L (3 8th higher)" }. also { it.slowfallHeld = true },
        createYMadeline(-3702F, -0.752329349518F+1F, 120.000450134277F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 15L (4 8th higher)" }. also { it.slowfallHeld = true },

        createYMadeline(-3703F, -0.002334594726F, 112.500434875488F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 5L,\n 1LJ,\n 9L" }. also { it.slowfallHeld = true },
        createYMadeline(-3702F, -0.8773341178790F, 112.500434875488F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 6L,\n 1LJ,\n 8L" }. also { it.slowfallHeld = true },
        createYMadeline(-3702F, -0.752333641052F, 112.500434875488F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 7L,\n 1LJ,\n 7L" }. also { it.slowfallHeld = true },
        createYMadeline(-3702F, -0.627333164215F, 112.500434875488F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 8L,\n 1LJ,\n 6L" }. also { it.slowfallHeld = true },
        createYMadeline(-3702F, -0.502332448960F, 112.500434875488F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 9L,\n 1LJ,\n 5L" }. also { it.slowfallHeld = true },

        createYMadeline(-3703F, -0.127334952354F, 105.000419616699F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 9L,\n 2LJ,\n 4L" }. also { it.slowfallHeld = true },
        createYMadeline(-3703F, -0.252335429192F, 105.000419616699F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 8L,\n 1LJ,\n 1L,\n 1LJ,\n 4L" }. also { it.slowfallHeld = true },
        createYMadeline(-3703F, -0.377335906028F, 105.000419616699F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "\n 2,\n 1L,\n 1R,\n 1LJ,\n 8L,\n 2LJ,\n 5L" }. also { it.slowfallHeld = true },

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
        /*
        Target(-0.32844F, -0.32839F, -3692F),
        Target(-0.15686F, -0.15683F, -3695F),2
        Target(0.014732F, 0.01469F, -3698F)
         */
        Target(-0.328422635794F, -0.328415542841F, -3692F),
        Target(-0.156855210662F, -0.156848177314F, -3695F),
        Target(0.014712393284F, 0.014719247818F, -3698F)
    )
    // number of frames to bruteforce
    const val maxDepth: Int = 20
    // useful if y-pos shouldn't change after manip is over
    val endWithGrab = false
    // target subpixel only or exact position
    val solutionSetting = SolutionSetting.ExactPosition

    // advanced
    val noGrabFrames: Set<Int> = setOf(14, 18, 22, 26, 30, 34, 38)
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
