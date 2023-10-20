fun simulate(startMadeline: Madeline, depth: Int, path: List<Input> = emptyList()) {
    if(depth <= 0) return

    listOf(Input.None, Input.Right, Input.Grab).map { input ->
        val newMadeline = startMadeline.copy()
        newMadeline.update(input)
        val newPath = path + listOf(input)

        if(newMadeline.yMovementCounter < 0.6397066F - 1F && newMadeline.yMovementCounter > 0.639446F - 1F) {
            newMadeline.printExact()
            println(newPath)
            return
        }
        simulate(newMadeline, depth - 1, newPath)
    }
}
