package org.nield.kotlinstatistics

import junit.framework.Assert.assertTrue
import org.amshove.kluent.shouldEqualTo
import org.junit.Test

class DoubleStatisticsTest {

    @Test
    fun testMedian1() = assertTrue(sequenceOf(1.0, 3.0, 5.0).median() == 3.0)

    @Test
    fun testMedian2() = assertTrue(arrayOf(1.0, 3.0, 4.0, 5.0).median() == 3.5)

    @Test
    fun testVariance1() = listOf(2.0, 3.0, 4.0).variance() shouldEqualTo 0.66666666666666666666666666666667

    @Test
    fun testVariance2() = listOf(1.0, 4.0, 6.0, 10.0).variance() shouldEqualTo 10.6875


}