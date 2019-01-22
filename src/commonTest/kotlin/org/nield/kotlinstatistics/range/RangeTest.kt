package org.nield.kotlinstatistics.range

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class RangeTest {

    @Test
    fun testOpenRange() {

        val rng = OpenRange(0.0, 1.0)

        assertTrue(.5 in rng)
        assertTrue(.1 in rng)

        assertFalse(1.0 in rng)
        assertFalse(0.0 in rng)
        assertFalse(-0.1 in rng)


        //val exception = ExpectedException.none()

        assertFailsWith(InvalidRangeException::class) {
            OpenRange(1.0, 1.0)
        }

        assertFailsWith(InvalidRangeException::class) {
            OpenRange(1.0, 0.0)
        }
    }

    @Test
    fun testClosedOpenRange() {

        val rng = ClosedOpenRange(0.0, 1.0)

        assertTrue(.5 in rng)
        assertTrue(.1 in rng)

        assertFalse(1.0 in rng)
        assertTrue(0.0 in rng)
        assertFalse(-0.1 in rng)

        ClosedOpenRange(1.0, 1.0)

        assertFailsWith(InvalidRangeException::class) {
            ClosedOpenRange(1.0, 0.0)
        }
    }

    @Test
    fun testOpenClosedRange() {

        val rng = OpenClosedRange(0.0, 1.0)

        assertTrue(.5 in rng)
        assertTrue(.1 in rng)

        assertTrue(1.0 in rng)
        assertFalse(0.0 in rng)
        assertFalse(-0.1 in rng)

        OpenClosedRange(1.0, 1.0)

        assertFailsWith(InvalidRangeException::class) {
            OpenClosedRange(1.0, 0.0)
        }
    }
}