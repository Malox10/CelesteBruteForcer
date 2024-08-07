import kotlin.math.abs
import kotlin.math.sign

typealias InputSequence = List<FrameInputs>

data class YData(val yPos: Float, val ySubPixel: Float, val ySpeed: Float, val state: PlayerState, val frame: Int)
data class Solution(val endState: Madeline, val inputSequence: InputSequence, val initialMadeline: Madeline)

typealias Solutions = MutableMap<YData, Solution>

class Simulator {
    private var solutionCounter = 0
    var initialMadeline: Madeline = Madeline(1F, 1F, 1F, 1F, 1F, 1F, PlayerState.StClimb)
    val solutions: Solutions = mutableMapOf()
    fun simulate(startMadeline: Madeline, targets: List<Target>, additionalMoves: List<Madeline.() -> Unit>, path: List<FrameInputs> = emptyList()) {
        if (startMadeline.frame > config.maxDepth) return

        // too low end-early condition (ignores upwards liftboost!)
        if (config.solutionSetting == SolutionSetting.ExactPosition) {
            var tooLow = true
            for (target in targets) {
                if (!tooLow) { break }
                for (additionalMove in additionalMoves) {
                    val movedMadeline = startMadeline.copy().also(additionalMove)
                    if (movedMadeline.y <= target.yPixel) {
                        tooLow = false
                        break
                    }
                }
            }
            if (tooLow) { return }
        }


        for (target in targets) {
            for (additionalMove in additionalMoves) {
                val movedMadeline = startMadeline.copy().also(additionalMove)
                if (checkIfSolution(target, movedMadeline, path)) {
                    val key = YData(startMadeline.y, startMadeline.yMovementCounter, startMadeline.ySpeed, startMadeline.state, startMadeline.frame)

                    if (!solutions.containsKey(key)) {
                        val solution = Solution(movedMadeline, path, initialMadeline)
                        solutions[key] = solution
                        solutionCounter++
                        println("SOLUTION $solutionCounter:\n")
                        printSolution(solution, additionalMove)
                    }

                    return
                }
            }
        }

        val possibleInputs = if (startMadeline.ySpeed < 0F) mutableListOf(Input.None) else when (startMadeline.state) {
            PlayerState.StClimb -> {
                if (startMadeline.ySpeed == 0F) {
                    listOf(Input.None)
                } else {
                    listOf(Input.None, Input.Grab)
                }
            }

            PlayerState.StNormal -> {
                if (startMadeline.ySpeed == 0F) {
                    listOf(Input.None, Input.Right)
                } else {
                    listOf(Input.None, Input.Grab, Input.Right)
                }
            }

        }.toMutableList()
        if (config.noGrabFrames.contains(startMadeline.frame + 1)) possibleInputs.remove(Input.Grab)
        if (config.noSlideFrames.contains(startMadeline.frame + 1)) possibleInputs.remove(Input.Right)
        if (config.yGround == startMadeline.y) {
            if (startMadeline.ySpeed == 0F) {
                possibleInputs.clear()
            } else {
                possibleInputs.remove(Input.Right)
            }
        }


        //start recursive calls based on input
        possibleInputs.map { input ->
            if (startMadeline.slowfallHeld) {
                // simulate with jump held
                val newMadeline1 = startMadeline.copy()
                newMadeline1.update(listOf(Input.Jump, input))
                val newPath1: List<FrameInputs> = path + listOf(listOf(Input.Jump, input))
                simulate(newMadeline1, targets, additionalMoves, newPath1)
                // consider releasing jump from here
                if ((startMadeline.state == PlayerState.StNormal && (input != Input.Grab))
                    || startMadeline.frame == 0) { // this condition is probably useless
                    val newMadeline2 = startMadeline.copy()
                    newMadeline2.slowfallHeld = false
                    newMadeline2.update(listOf(input))
                    val newPath2 = path + listOf(listOf(input))
                    simulate(newMadeline2, targets, additionalMoves, newPath2)
                }
            } else {
                val newMadeline = startMadeline.copy()
                newMadeline.update(listOf(input))
                val newPath = path + listOf(listOf(input))
                simulate(newMadeline, targets, additionalMoves, newPath)
            }
        }
    }

    private fun checkIfSolution(target: Target, madeline: Madeline, path: List<FrameInputs>): Boolean {
        if (!target.contains(madeline.yMovementCounter)) {
            return false
        }
        if (config.endWithGrab && (!(path.last().contains(Input.Grab)) || madeline.ySpeed >= 15F)) {
            return false
        }
        if (config.solutionSetting == SolutionSetting.ExactPosition && target.yPixel != madeline.y) {
            return false
        }
        return true
    }
}

class Target(lowerBoundParam: Float, upperBoundParam: Float, pixel: Float) {
    private var upperBoundYMovementCounter: Float
    private var lowerBoundYMovementCounter: Float
    var yPixel: Float = pixel

    init {
        upperBoundYMovementCounter = upperBoundParam
        lowerBoundYMovementCounter = lowerBoundParam

        if (abs(upperBoundParam) > 0.5F) {
            upperBoundYMovementCounter = (1F - abs(upperBoundParam)) * (sign(upperBoundParam) * -1)
            yPixel += sign(upperBoundParam) * 1
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