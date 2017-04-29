package org.nield.kotlinstatistics

import org.amshove.kluent.shouldEqualTo
import org.junit.Test

/**
 * Created by Av on 3/25/2017.
 */
class IntegerStatisticsTest {

    @Test
    fun testMedian1() = listOf(1, 9, 10).median() shouldEqualTo 9.0

    @Test
    fun testMedian2() = sequenceOf(1, 4, 5, 90).median() shouldEqualTo 4.5

    @Test
    fun testRange1() = listOf(2, 1, 10).range() shouldEqualTo 9

    @Test
    fun testRange2() = (1..30).range() shouldEqualTo 29

}


