package org.nield.kotlinstatistics

import org.amshove.kluent.shouldEqualTo
import org.junit.Test

class IntegerStatisticsTest {

    @Test
    fun testMedian1() = listOf(1, 9, 10).median() shouldEqualTo 9.0

    @Test
    fun testMedian2() = sequenceOf(1, 4, 5, 90).median() shouldEqualTo 4.5
}


