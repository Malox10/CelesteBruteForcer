import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GrabTest {
    @Test
    fun grabWhileSTClimb() {
        val madeline = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StClimb)
        madeline.update(Input.Grab)

        val actual = Madeline(0F, 0F, 0F, 9.000018119812F, state = PlayerState.StClimb)
        assertEquals(actual, madeline)
    }

    @Test
    fun grabWhileSTNormal() {
        val madeline = Madeline(0F, 0F, 0F, 120.000236511230F, state = PlayerState.StNormal)
        madeline.update(Input.Grab)

        val actual = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StClimb)
        assertEquals(actual, madeline)
    }
}

class NoneTest{
    @Test
    fun noneWhileStClimb() {
        val madeline = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StClimb)
        madeline.update(Input.None)

        val actual = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StNormal)
        assertEquals(actual, madeline)
    }

    @Test
    fun noneWhileStNormal() {
        val madeline = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StNormal)
        madeline.update(Input.None)

        val actual = Madeline(0F, 0F, 0F, 39.000076293945F, state = PlayerState.StNormal)
        assertEquals(actual, madeline)
    }
}

class RightTest() {
    @Test
    fun rightForOneFrameWhileStNormal() {
        val madeline = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StNormal)
        madeline.update(Input.Right)

        val actual = Madeline(0F, 0F, 0F, 20F, state = PlayerState.StNormal)
        assertEquals(actual, madeline)
    }

    @Test
    fun rightForTwoFramesWhileStNormal() {
        val madeline = Madeline(0F, 0F, 0F, 24.000047683716F, state = PlayerState.StNormal)
        madeline.update(Input.Right)
        madeline.update(Input.Right)

        val actual = Madeline(0F, 0F, 0F, 21.944444656372F, state = PlayerState.StNormal)
        assertEquals(actual, madeline)
    }
}
