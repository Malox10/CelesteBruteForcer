@file:Suppress("FloatingPointLiteralPrecision")

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File


class Config(
    // this is an example initial condition with y = 93.750072956085 as InfoHud value
    val initialConditions: List<Madeline> = listOf(
        createYMadeline(93F, 0.750072956085F, 105.000419616699F, PlayerState.StNormal) { 0F }
            .also { it.InitialInputs = "" }
            .also { it.slowfallHeld = true }
    ),
    // target ranges can not cut across 0.5
    val targets: List<Target> = listOf(
//        Target(0.196010157466F, 0.196006357670F, 100F)
    ),
    // number of frames to bruteforce
    val maxDepth: Int = 19,
    // useful if y-pos shouldn't change after manip is over
    val endWithGrab: Boolean = false,
    // target subpixel only or exact position
    val solutionSetting: SolutionSetting = SolutionSetting.ExactPosition,

    // advanced settings
    val noGrabFrames: Set<Int> = setOf(), //setOf(13, 17, 21, 25, 29, 33, 37)
    val noSlideFrames: Set<Int> = setOf(), //setOf(2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25)
    val offsets: List<Madeline.() -> Unit> = listOf { },
    /*
    { this.ySpeed = 7.5F; this.updatePosition() },
    { this.ySpeed = 15F; this.updatePosition() },
    { this.ySpeed = -105F; this.updatePosition() },

 */
    // { this.ySpeed = -89.999969482422F; this.updatePosition() },
    // { this.ySpeed = -105F; this.updatePosition(); this.updatePosition() },
    // { this.ySpeed = -105F; this.updatePosition(); this.ySpeed = -89.999969482422F; this.updatePosition() },
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
            val (lowerBoundPixel, lowerBound) = lowerBound.toPixelAndSubPixel()
            val (upperBoundPixel, upperBound) = upperBound.toPixelAndSubPixel()
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
        val (yString, ySubpixelString) = yPosition.split(".").map { it.trim() }
        return createYMadeline(yString.toFloat(), "0.$ySubpixelString".toFloat(), ySpeed, state)
            .also { it.InitialInputs = initialInputs }
            .also { it.slowfallHeld = slowFallHeld }
    }
}

fun String.toPixelAndSubPixel(): Pair<Float, Float> {
    val (yString, ySubpixelString) = this.split(".").map { it.trim() }
    return yString.toFloat() to "0.$ySubpixelString".toFloat()
}
