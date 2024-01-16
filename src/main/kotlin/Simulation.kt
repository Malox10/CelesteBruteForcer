typealias InputSequence = List<Input>
data class YData(val yPos: Float, val ySubPixel: Float, val ySpeed: Float)


val cache: HashSet<YData> = hashSetOf()
typealias Solutions = MutableMap<YData, Pair<Madeline, InputSequence>>
class Simulator() {
    val solutions: Solutions = mutableMapOf()
    fun simulate(startMadeline: Madeline, targets: List<Target>, additionalMoves: List<Madeline.() -> Unit>, path: List<Input> = emptyList()) {
        if (startMadeline.frame >= maxDepth) return
        //if(startMadeline.y > -2435) return

        val key = YData(startMadeline.y, startMadeline.yMovementCounter, startMadeline.ySpeed)
        if (cache.contains(key)) {
            return
        } else {
            cache.add(key)
            for (target in targets) {
                for (additionalMove in additionalMoves) {
                    val movedMadeline = startMadeline.copy().also(additionalMove)

                    if (target.contains(movedMadeline.yMovementCounter)
                        && movedMadeline.y == target.pixel
                    ) {
//                        if (path.last() != Input.Grab) {
//                            Unit
//                        }
                        solutions[key] = movedMadeline to path
                        println(movedMadeline.yMovementCounter)
                        cache.remove(key)
                        return
                    }
                }
            }
        }

        val inputs = if (startMadeline.ySpeed < 0F) mutableListOf(Input.None) else when (startMadeline.state) {
            PlayerState.StClimb -> {
                if (startMadeline.ySpeed < 15) {
                    listOf(Input.None)
                } else {
                    listOf(Input.None, Input.Grab)
                }
            }

            else -> {
                if (startMadeline.frame != 1) {
                    listOf(Input.None, Input.Grab)
                } else {
                    listOf(Input.None, Input.Grab, Input.Right)
                }
            }
        }.toMutableList()
        if (noGrabFrames.contains(startMadeline.frame + 1)) inputs.remove(Input.Grab)

        inputs.map { input ->
            val newMadeline = startMadeline.copy()
            newMadeline.update(input)
            val newPath = path + listOf(input)
            simulate(newMadeline, targets, additionalMoves, newPath)
        }
    }
}

class Target(lowerBoundParam: Float, upperBoundParam: Float, val pixel: Float) {
    val upperBound: Float
    val lowerBound: Float

    fun contains(position: Float) = position in lowerBound..upperBound

    init {
        upperBound = if(upperBoundParam > 0.5F) upperBoundParam - 1F else upperBoundParam
        lowerBound = if(lowerBoundParam > 0.5F) lowerBoundParam - 1F else lowerBoundParam
        if(upperBound < lowerBound) error("parameters in wrong order upperBound: $upperBound is not bigger than lowerBound: $lowerBound")
    }
}