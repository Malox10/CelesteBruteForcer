import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

fun createYMadeline(y: Float, ySubpixel: Float, ySpeed: Float, state: PlayerState): Madeline {
    val trueYSubPixel = if(ySubpixel > 0.5) ySubpixel - 1 else ySubpixel
    val trueYPixel = if(ySubpixel > 0.5) y + 1 else y
    return Madeline(0F, 0F, trueYPixel, trueYSubPixel, 0F, ySpeed, state)
}

class GrabTest {
    @Test
    fun grabWhileSTClimb() {
        //x = -1564.467612385750
//        val madeline = Madeline(0F, 0F, 345F, 0.400036633015F, 0F, 24.000047683716F, state = PlayerState.StClimb)
        val madeline = createYMadeline(345F, 0.400036633015F, 24.000047683716F, PlayerState.StClimb)
        madeline.update(Input.Grab)

        val expected = createYMadeline(345F, 0.550037264824F, 9.000018119812F, state = PlayerState.StClimb)
        assertEquals(expected, madeline)
    }

//    @Test
//    fun grabWhileSTNormal() {
//        val madeline = createYMadeline(0F, 0F, 0F, 120.000236511230F, state = PlayerState.StNormal)
//        madeline.update(Input.Grab)
//
//        val expected = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StClimb)
//        assertEquals(expected, madeline)
//    }
}

//class NoneTest{
//    @Test
//    fun noneWhileStClimb() {
//        val madeline = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StClimb)
//        madeline.update(Input.None)
//
//        val expected = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StNormal)
//        assertEquals(expected, madeline)
//    }
//
//    @Test
//    fun noneWhileStNormal() {
//        val madeline = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StNormal)
//        madeline.update(Input.None)
//
//        val expected = Madeline(0F, 0F, 0F, 39.000076293945F, state = PlayerState.StNormal)
//        assertEquals(expected, madeline)
//    }
//}
//
//class RightTest() {
//    @Test
//    fun rightForOneFrameWhileStNormal() {
//        val madeline = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StNormal)
//        madeline.update(Input.Right)
//
//        val expected = Madeline(0F, 0F, 0F, 20F, state = PlayerState.StNormal)
//        assertEquals(expected, madeline)
//    }
//
//    @Test
//    fun rightForTwoFramesWhileStNormal() {
//        val madeline = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StNormal)
//        madeline.update(Input.Right)
//        madeline.update(Input.Right)
//
//        val expected = Madeline(0F, 0F, 0F, 21.944444656372F, state = PlayerState.StNormal)
//        assertEquals(expected, madeline)
//    }
//
//    @Test
//    fun bla() {
//        val madeline = Madeline(0F, 0F, 0F, 15F, state = PlayerState.StNormal)
//        madeline.update(Input.Right)
//    }
//}
//
//class MovementTest() {
//    @Test
//    fun subpixelTest() {
//        val madeline = Madeline(0F, 0.400036633015F, 0f, 24.000047683716f, state = PlayerState.StClimb)
//        madeline.update(Input.Grab)
//
//        madeline.y.printAccurate()
//        (madeline.y.toDouble() + 345.0).printAccurate()
//        val expected = Madeline(0F, 0.550037264824F, 0f, 9.000018119812F, PlayerState.StClimb)
//        assertEquals(expected, madeline)
//    }
//}

class IntegrationTest() {
    @Test
    fun moveMadeline3Steps() {
        val madeline = createYMadeline(345F, 0.400036633015F, 24.000047683716F, PlayerState.StClimb)
        listOf(Input.None, Input.Right, Input.Grab).forEachIndexed { index, input ->
            println("input ${index + 1}:")
            madeline.update(input)
            madeline.printExact()
        }
    }

    @Test
    fun moveMadeline6Right() {
        val madeline = createYMadeline(345F, 0.400036633015F, 24.000047683716F, PlayerState.StClimb)
        listOf(Input.Right, Input.Right, Input.Right, Input.Right, Input.Right, Input.Right).forEachIndexed { index, input ->
            println("input ${index + 1}:")
            madeline.update(input)
            madeline.printExact()
        }

        val expected = createYMadeline(347F, 0.790782570839F, 27.777772903442F, PlayerState.StNormal)
        assertEquals(expected, madeline)
    }
}

