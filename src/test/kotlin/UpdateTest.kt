import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

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
        listOf(
            Input.Right,
            Input.Right,
            Input.Right,
            Input.Right,
            Input.Right,
            Input.Right
        ).forEachIndexed { index, input ->
            println("input ${index + 1}:")
            madeline.update(input)
            madeline.printExact()
        }

        val expected = createYMadeline(347F, 0.790782570839F, 27.777772903442F, PlayerState.StNormal)
        assertEquals(expected, madeline)
    }

    @Test
    fun worldAbyss2Test() {
        val madeline = createYMadeline(-3701F, 0.838162422180F, 120.000450134277F, PlayerState.StNormal)
        madeline.yMovementCounter.printAccurate()
//        madeline.printExact()

        listOf(
            Input.None,
            Input.None,
            Input.Right,
            Input.Grab,
            Input.None,
            Input.None,
            Input.None,
            Input.None,
            Input.Grab,
            Input.None,
            Input.None,
            Input.None,
            Input.Grab, //faulty
            Input.None,
            Input.Grab,
            Input.None,
            Input.None,
            Input.Grab,
            Input.None,
            Input.Grab,
            Input.None,
            Input.None,
        ).forEachIndexed { index, input ->
            madeline.update(input)

            println("input ${index + 1}:")
            madeline.yMovementCounter.printAccurate()
            println()

//            madeline.yMovementCounter.printAccurate()
//            madeline.printExact()
        }
    }

    @Test
    fun liftboostTest() {
        val madeline = createYMadeline(-2440F, 0.016839504242F, 41.000026702881F, PlayerState.StClimb) { -40F }
        listOf(Input.None, Input.Grab, Input.None, Input.Grab, Input.Grab, Input.Grab, Input.Grab, Input.Grab, Input.Right, Input.Right, Input.Right, Input.Right, Input. Grab).forEachIndexed { index, input ->
            println("input ${index + 1}: ${input}")
            madeline.update(input)
            madeline.printRaw()
        }
    }

    @Test
    fun debugTest() {
        val madeline = createYMadeline(-2447F, 0.233488678932F, 39.000022888184F, PlayerState.StClimb) { frame ->
            when {
                frame < 20 -> -40F
                frame == 20 -> -29.990854263306F
                frame > 20 -> 0F

                else -> error("how?")
            }
        }

        listOf(
            Input.None, Input.None,
            Input.Right,
            Input.None,
            Input.Right, Input.Right,
            Input.None, Input.None, Input.None, Input.None,
            Input.Right,
            Input.None,
            Input.Right,
            Input.Grab,
            Input.None, Input.None, Input.None,
            Input.Grab,
            Input.None, Input.None, Input.None, Input.None,
            Input.Grab,
            Input.None, Input.None, Input.None,
            Input.Grab,
            Input.None,
            Input.Grab
        ).forEachIndexed { index, input ->
            println("input ${index + 1}: ${input}")
            madeline.update(input)
            madeline.printRaw()
        }
    }

    @Test
    fun debugTest2() {
        val madeline = createYMadeline(-2447F, 0.233488678932F, 39.000022888184F, PlayerState.StClimb) { frame ->
            when {
                frame < 20 -> -40F
                frame == 20 -> -29.990854263306F
                frame > 20 -> 0F

                else -> error("how?")
            }
        }

        listOf(
            Input.None, Input.None,
            Input.Right, Input.Right,
            Input.None, Input.None, Input.None, Input.None,
            Input.Right, Input.Right, Input.Right,
            Input.None, Input.None,
            Input.Grab,
            Input.None, Input.None, Input.None,
            Input.Grab,
            Input.None, Input.None, Input.None, Input.None,
            Input.Grab,
            Input.None, Input.None, Input.None,
            Input.Grab,
            Input.None,
            Input.Grab,
        ).forEachIndexed { index, input ->
            println("input ${index + 1}: ${input}")
            madeline.update(input)
            madeline.printRaw()
        }
    }

    @Test
    fun debugTest3() {
        val madeline = createYMadeline(-2447F, 0.233488678932F, 39.000022888184F, PlayerState.StClimb) { frame ->
            when {
                frame == 17 -> 0F
                frame < 20 -> -40F
                frame == 20 -> -29.990854263306F
                frame > 20 -> 0F

                else -> error("how?")
            }
        }

        listOf(
            Input.None, Input.None,
            Input.Right,
            Input.None,
            Input.Right,
            Input.None, Input.None,
            Input.Right, Input.Right,
            Input.None, Input.None,
            Input.Right,
            Input.None,
            Input.Right,
            Input.None,
            Input.Grab,
            Input.None, Input.None, Input.None,
            Input.Grab,
            Input.None, Input.None, Input.None,
            Input.Grab,
            Input.None,
            Input.Grab,
            Input.None, Input.None,
            Input.Grab,
        ).forEachIndexed { index, input ->
            println("input ${index + 1}: ${input}")
            madeline.update(input)
            madeline.printRaw()
        }
    }
}

