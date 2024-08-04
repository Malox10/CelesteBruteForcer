@file:Suppress("FloatingPointLiteralPrecision")

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.math.sign


class Config(
    // this is an example initial condition with y = 93.750072956085 as InfoHud value
    val initialConditions: List<Madeline> = listOf(
        createYMadeline(-3702F, -0.497075915336F, 120.000450134277F, PlayerState.StNormal) { 0F }
            .also { it.InitialInputs = "\n 10L\n 5LJ" }
            .also { it.slowfallHeld = true },
    ),
    // target ranges can not cut across 0.5
    val targets: List<Target> = listOf(
        Target(-0.32843F, -0.32840F, -3692F),
        Target(-0.15686F, -0.15683F, -3695F),
        Target(0.01472F, 0.01469F, -3698F)
    ),
    // number of frames to bruteforce
    val maxDepth: Int = 20,
    // useful if y-pos shouldn't change after manip is over
    val endWithGrab: Boolean = false,
    // target subpixel only or exact position
    val solutionSetting: SolutionSetting = SolutionSetting.ExactPosition,

    // advanced settings
    val noGrabFrames: Set<Int> =  setOf(11, 15, 19, 23, 27, 31, 35),
    val noSlideFrames: Set<Int> =  setOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25),
    val offsets: List<Madeline.() -> Unit> = listOf(
        {}
    )
)

@Suppress("unused")
enum class SolutionSetting {
    @SerialName("ExactPosition")
    ExactPosition,

    @SerialName("SubPixelOnly")
    SubpixelOnly
}

const val configPath = "config.json"
fun parseConfig(): Config {
    val file = File(configPath)
    if(!file.exists()) error("File: $configPath doesn't exist")
    if(!file.isFile) error("$configPath is not a file")

    val jsonString = file.readText()
    val json = Json.decodeFromString<JsonConfig>(jsonString)

    return Config(
        json.initialConditions.map { it.toMadeline() },
        json.targets.map { it.toTarget() },
        json.maxDepth,
        json.endWithGrab,
        json.solutionSetting,
        json.noGrabFrames,
        json.noSlideFrames,
        offsets = listOf {}
    )
}

@Serializable
class JsonConfig(
    val initialConditions: List<JsonYMadeline>,
    val targets: List<TargetJson>,

    val solutionSetting: SolutionSetting = SolutionSetting.SubpixelOnly,
    val maxDepth: Int = 18,
    val endWithGrab: Boolean = true,
    val noGrabFrames: Set<Int> = setOf(),
    val noSlideFrames: Set<Int> = setOf(),
) {
    @Serializable
    data class TargetJson(val lowerBound: String, val upperBound: String) {
        fun toTarget(): Target {
            var (lowerBoundPixel, lowerBound) = lowerBound.toPixelAndSubPixel()
            if (sign(lowerBoundPixel) == -1F) {
                lowerBound = lowerBound * -1F
            }
            var (upperBoundPixel, upperBound) = upperBound.toPixelAndSubPixel()
            if (sign(upperBoundPixel) == -1F) {
                upperBound = upperBound * -1F
            }
            if(lowerBoundPixel != upperBoundPixel) error("lowerBound and UpperBound don't share the same Pixel")

            return Target(lowerBound, upperBound, lowerBoundPixel)
        }
    }
}

@Serializable
data class JsonYMadeline(
    val yPosition: String,
    val ySpeed: Float,
    val state: PlayerState = PlayerState.StNormal,
    val initialInputs: String = "",
    val slowFallHeld: Boolean = false
) {
    fun toMadeline(): Madeline {
        var (yPixel, ySubPixel) = yPosition.toPixelAndSubPixel()
        if (sign(yPixel) == -1F) {
            ySubPixel = ySubPixel * -1F
        }
        return createYMadeline(yPixel, ySubPixel, ySpeed, state)
            .also { it.InitialInputs = initialInputs }
            .also { it.slowfallHeld = slowFallHeld }
    }
}

fun String.toPixelAndSubPixel(): Pair<Float, Float> {
    var str = this
    if (!this.contains(".")) { str = str + ".0" }
    val (yString, ySubpixelString) = str.split(".").map { it.trim() }
    return yString.toFloat() to "0.$ySubpixelString".toFloat()
}
