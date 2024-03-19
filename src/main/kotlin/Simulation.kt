import kotlin.math.abs
import kotlin.math.sign

typealias InputSequence = List<Input>
data class YData(val yPos: Float, val ySubPixel: Float, val ySpeed: Float)


val cache: HashSet<YData> = hashSetOf()
typealias Solutions = MutableMap<YData, Pair<Madeline, InputSequence>>
class Simulator() {
    val solutions: Solutions = mutableMapOf()
    fun simulate(startMadeline: Madeline, targets: List<Target>, additionalMoves: List<Madeline.() -> Unit>, path: List<Input> = emptyList()) {
        if (startMadeline.frame > Config.maxDepth) return

        val key = YData(startMadeline.y, startMadeline.yMovementCounter, startMadeline.ySpeed)
        if (cache.contains(key) && startMadeline.frame != 1) {
            return
        } else {
            cache.add(key)
            for (target in targets) {
                for (additionalMove in additionalMoves) {
                    val movedMadeline = startMadeline.copy().also(additionalMove)
                    if (checkIfSolution(target, movedMadeline, path)) {
                        solutions[key] = movedMadeline to path
                        // println(movedMadeline.yMovementCounter)
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
        if (Config.noGrabFrames.contains(startMadeline.frame + 1)) inputs.remove(Input.Grab)
        if (Config.noSlideFrames.contains(startMadeline.frame + 1)) inputs.remove(Input.Right)


        //start recursive calls based on input
        inputs.map { input ->
            if (startMadeline.slowfallHeld) {
                val newMadeline1 = startMadeline.copy()
                newMadeline1.update(listOf(input, Input.Jump))
                val newPath1 = path + listOf(input)
                simulate(newMadeline1, targets, additionalMoves, newPath1)
                if (startMadeline.state == PlayerState.StNormal && input == Input.None) {
                    val newMadeline2 = startMadeline.copy()
                    newMadeline2.slowfallHeld = false
                    newMadeline2.update(listOf(input))
                    val newPath2 = path + listOf(input, Input.Jump)
                    simulate(newMadeline2, targets, additionalMoves, newPath2)
                }
            } else {
                val newMadeline = startMadeline.copy()
                newMadeline.update(listOf(input))
                val newPath = path + listOf(input)
                simulate(newMadeline, targets, additionalMoves, newPath)
            }
        }
    }

    fun checkIfSolution(target: Target, madeline: Madeline, path: List<Input>): Boolean {
        if (!target.contains(madeline.yMovementCounter)) {
            return false
        }
        if (Config.endWithGrab && path.last() != Input.Grab && madeline.ySpeed <= 15F) {
            return false
        }
        if (Config.solutionSetting == SolutionSetting.ExactPosition && target.YPixel != madeline.y) {
            return false
        }
        return true
    }
}

class Target(lowerBoundParam: Float, upperBoundParam: Float, pixel: Float) {
    var upperBoundYMovementCounter: Float
    var lowerBoundYMovementCounter: Float
    var YPixel = pixel

    init {
        upperBoundYMovementCounter = upperBoundParam
        lowerBoundYMovementCounter = lowerBoundParam

        if (abs(upperBoundParam) > 0.5F) {
            upperBoundYMovementCounter = (1F - abs(upperBoundParam)) * (sign(upperBoundParam) * -1)
            YPixel += sign(upperBoundParam) * 1
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