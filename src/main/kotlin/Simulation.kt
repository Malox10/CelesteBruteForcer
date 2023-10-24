
typealias InputSequence = List<Input>
data class YData(val yPos: Float, val ySubPixel: Float, val ySpeed: Float)

val solutions: MutableMap<YData, Pair<Madeline, InputSequence>> = mutableMapOf()
val cache: HashSet<YData> = hashSetOf()
fun simulate(startMadeline: Madeline, target: Target, depth: Int, path: List<Input> = emptyList()) {
    if(depth - 1 < 0) return
    val key = YData(startMadeline.y, startMadeline.yMovementCounter, startMadeline.ySpeed)
    if(cache.contains(key)) {
        return
    } else {
        cache.add(key)
        if(startMadeline.yMovementCounter <= target.upperBound && startMadeline.yMovementCounter >= target.lowerBound) {
            solutions[key] = startMadeline to path
            println(startMadeline.yMovementCounter)
            println(path.toTasFile())
        }
    }

    val inputs = when(startMadeline.state) {
        PlayerState.StClimb -> {
            if(startMadeline.ySpeed < 15) {
                listOf(Input.None)
            } else {
                listOf(Input.None, Input.Grab)
            }
        }
        else -> listOf(Input.None, Input.Grab, )//Input.Right
    }

    inputs.map { input ->
        val newMadeline = startMadeline.copy()
        newMadeline.update(input)
        val newPath = path + listOf(input)
        simulate(newMadeline, target, depth - 1, newPath)
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
