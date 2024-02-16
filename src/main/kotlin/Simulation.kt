import kotlin.math.abs
import kotlin.math.sign

typealias InputSequence = List<Input>
data class YData(val yPos: Float, val ySubPixel: Float, val ySpeed: Float)


val cache: HashSet<YData> = hashSetOf()
typealias Solutions = MutableMap<YData, Pair<Madeline, InputSequence>>
class Simulator() {
    val solutions: Solutions = mutableMapOf()
    fun simulate(startMadeline: Madeline, targets: List<Target>, additionalMoves: List<Madeline.() -> Unit>, path: List<Input> = emptyList()) {
        if (startMadeline.frame >= maxDepth) return

        val key = YData(startMadeline.y, startMadeline.yMovementCounter, startMadeline.ySpeed)
        if (cache.contains(key) && startMadeline.frame != 1) {
            return
        } else {
            cache.add(key)
            for (target in targets) {
                for (additionalMove in additionalMoves) {
                    val movedMadeline = startMadeline.copy().also(additionalMove)
                    if (target.contains(movedMadeline.yMovementCounter)
                         // && movedMadeline.y == target.pixel
                    ) {
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

            PlayerState.StNormal -> {
                listOf(Input.None, Input.Grab, Input.Right)
            }

        }.toMutableList()
        if (noGrabFrames.contains(startMadeline.frame + 1)) inputs.remove(Input.Grab)
        if (noSlideFrames.contains(startMadeline.frame + 1)) inputs.remove(Input.Right)

        inputs.map { input ->
            val newMadeline = startMadeline.copy()
            newMadeline.update(input)
            val newPath = path + listOf(input)
            simulate(newMadeline, targets, additionalMoves, newPath)
        }
    }
}

class Target(lowerBoundParam: Float, upperBoundParam: Float, val pixel: Float) {
    var upperBoundYMovementCounter: Float
    var lowerBoundYMovementCounter: Float

    init {
        upperBoundYMovementCounter = upperBoundParam
        lowerBoundYMovementCounter = lowerBoundParam
        var trueYPixel = pixel


        if (abs(upperBoundParam) > 0.5F) {
            upperBoundYMovementCounter = (1F - abs(upperBoundParam)) * (sign(upperBoundParam) * -1)
            trueYPixel += sign(upperBoundParam) * 1
        }
        if (abs(lowerBoundParam) > 0.5F) {
            lowerBoundYMovementCounter = (1F - abs(lowerBoundParam)) * (sign(lowerBoundParam) * -1)
        }
        // swap if needed
        if(upperBoundYMovementCounter < lowerBoundYMovementCounter) {
            val temp = lowerBoundYMovementCounter
            lowerBoundYMovementCounter = upperBoundYMovementCounter
            upperBoundYMovementCounter = temp
        }
    }

    fun contains(position: Float) = position in lowerBoundYMovementCounter..upperBoundYMovementCounter
}