package org.nield.kotlinstatistics

import junit.framework.Assert.assertTrue
import org.junit.Test

class DoubleStatisticsTest {

    @Test
    fun testMedian1() = assertTrue(sequenceOf(1.0, 3.0, 5.0).median() == 3.0)

    @Test
    fun testMedian2() = assertTrue(arrayOf(1.0, 3.0, 4.0, 5.0).median() == 3.5)
}