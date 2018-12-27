package org.nield.kotlinstatistics.range

import org.junit.Assert
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.*


class RangeTest {

    @Test
    fun testOpenRange() {

        val rng = OpenRange(0.0, 1.0)

        Assert.assertTrue(.5 in rng)
        Assert.assertTrue(.1 in rng)

        Assert.assertFalse(1.0 in rng)
        Assert.assertFalse(0.0 in rng)
        Assert.assertFalse(-0.1 in rng)


        val exception = ExpectedException.none()

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

        Assert.assertTrue(.5 in rng)
        Assert.assertTrue(.1 in rng)

        Assert.assertFalse(1.0 in rng)
        Assert.assertTrue(0.0 in rng)
        Assert.assertFalse(-0.1 in rng)

        ClosedOpenRange(1.0, 1.0)

        assertFailsWith(InvalidRangeException::class) {
            ClosedOpenRange(1.0, 0.0)
        }
    }

    @Test
    fun testOpenClosedRange() {

        val rng = OpenClosedRange(0.0, 1.0)

        Assert.assertTrue(.5 in rng)
        Assert.assertTrue(.1 in rng)

        Assert.assertTrue(1.0 in rng)
        Assert.assertFalse(0.0 in rng)
        Assert.assertFalse(-0.1 in rng)

        OpenClosedRange(1.0, 1.0)

        assertFailsWith(InvalidRangeException::class) {
            OpenClosedRange(1.0, 0.0)
        }
    }
}