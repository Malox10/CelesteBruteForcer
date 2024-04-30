import kotlin.math.abs
import kotlin.math.sign

typealias InputSequence = List<FrameInputs>

// for smart caching
//data class YData(val yPos: Float, val ySubPixel: Float, val ySpeed: Float, val slowfall: Boolean, val state: PlayerState, val frame: Int)

//for solution caching
data class YData(val yPos: Float, val ySubPixel: Float, val ySpeed: Float, val state: PlayerState, val frame: Int)


val cache: HashSet<YData> = hashSetOf()
typealias Solutions = MutableMap<YData, Pair<Madeline, InputSequence>>
class Simulator() {
    var solutionCounter = 0
    var initialMadeline: Madeline = Madeline(1F, 1F, 1F, 1F, 1F, 1F, PlayerState.StClimb)
    val solutions: Solutions = mutableMapOf()
    fun simulate(startMadeline: Madeline, targets: List<Target>, additionalMoves: List<Madeline.() -> Unit>, path: List<FrameInputs> = emptyList()) {
        if (startMadeline.frame > Config.maxDepth) return

        // too low end-early condition (ignores upwards liftboost!)
        if (Config.solutionSetting == SolutionSetting.ExactPosition) {
            var tooLow = true
            for (target in targets) {
                if (!tooLow) { break }
                for (additionalMove in additionalMoves) {
                    val movedMadeline = startMadeline.copy().also(additionalMove)
                    if (movedMadeline.y <= target.YPixel) {
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
                    val oldSolutionSize = solutions.size
                    solutions[key] = movedMadeline to path
                    if (oldSolutionSize != solutions.size) {
                        solutionCounter++
                        println("SOLUTION $solutionCounter:\n")
                        printSolution(solutions[key]!!, initialMadeline)
                    }
                    return
                }
            }
        }

        /* smart cache solutions (causes oom)
        val key = YData(startMadeline.y, startMadeline.yMovementCounter, startMadeline.ySpeed, startMadeline.slowfallHeld, startMadeline.state, startMadeline.frame)
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
         */

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
        if (Config.noGrabFrames.contains(startMadeline.frame + 1)) possibleInputs.remove(Input.Grab)
        if (Config.noSlideFrames.contains(startMadeline.frame + 1)) possibleInputs.remove(Input.Right)


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
                    || startMadeline.frame == 0) {
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

    fun checkIfSolution(target: Target, madeline: Madeline, path: List<FrameInputs>): Boolean {
        if (!target.contains(madeline.yMovementCounter)) {
            return false
        }
        if (Config.endWithGrab && (!(path.last().contains(Input.Grab)) || madeline.ySpeed >= 15F)) {
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