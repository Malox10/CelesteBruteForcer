
typealias InputSequence = List<Input>
data class YData(val yPos: Float, val ySubPixel: Float, val ySpeed: Float, val state: PlayerState)

val solutions: MutableMap<YData, Pair<Madeline, InputSequence>> = mutableMapOf()
val cache: HashSet<YData> = hashSetOf()
fun simulate(startMadeline: Madeline, target: Target, path: List<Input> = emptyList()) {
    if(startMadeline.frame >= maxDepth) return
    if(startMadeline.y > -2435) return

    val key = YData(startMadeline.y, startMadeline.yMovementCounter, startMadeline.ySpeed, startMadeline.state)
    if(cache.contains(key)) {
        return
    } else {
        cache.add(key)
        if(startMadeline.yMovementCounter <= target.upperBound && startMadeline.yMovementCounter >= target.lowerBound) {
            if(path.last() != Input.Grab) {
                cache.remove(key)
                return
            }
            solutions[key] = startMadeline to path
            println(startMadeline.yMovementCounter)
            println(path.toTasFile())
        }
    }


    val inputs = if(startMadeline.ySpeed < 0F) mutableListOf(Input.None) else when(startMadeline.state) {
        PlayerState.StClimb -> {
            if(startMadeline.ySpeed < 15) {
                listOf(Input.None)
            } else {
                listOf(Input.None, Input.Grab)
            }
        }
        else -> {
            if(startMadeline.frame + 1 > 16) {
                listOf(Input.None, Input.Grab)
            } else {
                listOf(Input.None, Input.Grab, Input.Right)
            }
        }
    }.toMutableList()

    val noGrabFrames = setOf(1, 28, 30, 32, 34, 36, 37, 39)
    if(noGrabFrames.contains(startMadeline.frame + 1)) inputs.remove(Input.Grab)

//    val inputMap = listOf(
//        Input.None, Input.None,
//        Input.Right, Input.Right,
//        Input.None, Input.None, Input.None, Input.None,
//        Input.Right, Input.Right, Input.Right,
//        Input.None, Input.None,
//        Input.Grab,
//        Input.None, Input.None, Input.None,
//        Input.Grab,
//        Input.None, Input.None, Input.None, Input.None,
//        Input.Grab,
//        Input.None, Input.None, Input.None,
//        Input.Grab,
//        Input.None,
//        Input.Grab,
//    ).mapIndexed { index, value -> index to value }.associate { it }
//inputMap[startMadeline.frame]!!

    inputs.map { input ->
        val newMadeline = startMadeline.copy()
        newMadeline.update(input)
        val newPath = path + listOf(input)
        simulate(newMadeline, target, newPath)
    }
}

class Target(lowerBoundParam: Float, upperBoundParam: Float) {
    val upperBound: Float
    val lowerBound: Float

    init {
        upperBound = if(upperBoundParam > 0.5F) upperBoundParam - 1F else upperBoundParam
        lowerBound = if(lowerBoundParam > 0.5F) lowerBoundParam - 1F else lowerBoundParam
        if(upperBound < lowerBound) error("parameters in wrong order upperBound: $upperBound is not bigger than lowerBound: $lowerBound")
    }
}
