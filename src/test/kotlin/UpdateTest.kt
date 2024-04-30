import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GrabTest {
    @Test
    fun grabWhileSTClimb() {
        //x = -1564.467612385750
//        val madeline = Madeline(0F, 0F, 345F, 0.400036633015F, 0F, 24.000047683716F, state = PlayerState.StClimb)
        val madeline = createYMadeline(345F, 0.400036633015F, 24.000047683716F, PlayerState.StClimb)
        madeline.update(listOf(Input.Grab))

        val expected = createYMadeline(345F, 0.550037264824F, 9.000018119812F, state = PlayerState.StClimb).also { it.frame = 1 }
        assertEquals(expected, madeline)
    }
}

class IntegrationTest() {
    @Test
    fun moveMadeline3Steps() {
        val madeline = createYMadeline(345F, 0.400036633015F, 24.000047683716F, PlayerState.StClimb)
        listOf(Input.None, Input.Right, Input.Grab).forEachIndexed { index, input ->
            println("input ${index + 1}:")
            madeline.update(listOf(input))
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
            madeline.update(listOf(input))
            madeline.printExact()
        }

        val expected = createYMadeline(347F, 0.790782570839F, 27.777772903442F, PlayerState.StNormal)
            .also { it.wallSlideTimer = 1.116666793823F }
            .also { it.frame = 6 }
        assertEquals(expected, madeline)
    }

    @Test
    fun liftboostTest() {
        val madeline = createYMadeline(-2440F, 0.016839504242F, 41.000026702881F, PlayerState.StClimb) { -40F }
        listOf(Input.None, Input.Grab, Input.None, Input.Grab, Input.Grab, Input.Grab, Input.Grab, Input.Grab, Input.Right, Input.Right, Input.Right, Input.Right, Input. Grab).forEachIndexed { index, input ->
            println("input ${index + 1}: ${input}")
            madeline.update(listOf(input))
            madeline.printRaw()
        }
    }

    @Test
    fun fullTest1() {
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
            madeline.update(listOf(input))
            madeline.printRaw()
        }
    }

    @Test
    fun fullTest2() {
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
            madeline.update(listOf(input))
            madeline.printRaw()
        }
    }

    @Test
    fun fullTest3() {
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
            madeline.update(listOf(input))
            madeline.printRaw()
        }
    }

    @Test
    fun printTest() {
       val path = listOf(listOf(Input.Right, Input.Jump))
        print(path.toTasFile())
    }
}

class MainDebugTest {
    @Test
    fun mainDebugTest() {
        val madeline = createYMadeline(93F, 0.750072956085F, 105.000419616699F, PlayerState.StNormal) { 0F }.
        also { it.InitialInputs = "" }. also { it.slowfallHeld = true }
        madeline.yMovementCounter.printAccurate()

        listOf(
            Input.Grab,
            Input.None,
            Input.None,
            Input.Right,
            Input.Grab,
            Input.None,
            Input.Right,
            Input.Grab,
            Input.None,
            Input.Right,
            Input.Grab,
            Input.Grab,
            Input.None
        ).forEachIndexed { index, input ->
            madeline.update(listOf(input, Input.Jump))

            println("input ${index + 1}:")
            madeline.printExact()
            println()
        }
        listOf(
            Input.Right,
            Input.Right,
            Input.None,
            Input.Grab,
            Input.None,
            Input.Right,
            Input.None,
            Input.None,
            Input.None
        ).forEachIndexed { index, input ->
            madeline.update(listOf(input))

            println("input ${index + 1}:")
            madeline.printExact()
            println()
        }
    }
}

