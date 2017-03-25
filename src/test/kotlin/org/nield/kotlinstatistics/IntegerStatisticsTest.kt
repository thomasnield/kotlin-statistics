package org.nield.kotlinstatistics

import org.amshove.kluent.shouldEqualTo
import org.junit.Test

/**
 * Created by Av on 3/25/2017.
 */
class IntegerStatisticsTest {

    @Test
    fun testMedianList() = listOf(1, 9, 10).median() shouldEqualTo 9.0

    @Test
    fun testMedianSequence() = sequenceOf(1, 4, 5, 90).median() shouldEqualTo 4.5

    @Test
    fun testMedianArray() = arrayOf(1, 11, 19).median() shouldEqualTo 11.0


    @Test
    fun testVarianceList() = listOf(2, 3, 4).variance() shouldEqualTo 0.66666666666666666666666666666667

    @Test
    fun testVarianceSequence() = sequenceOf(1, 4, 6, 10).variance() shouldEqualTo 10.6875

    @Test
    fun testVarianceArray() = arrayOf(0, 5, 7).variance() shouldEqualTo 8.6666666666666666666666666666667


    @Test
    fun testStdDeviationList() = listOf(2, 3, 4).standardDeviation() shouldEqualTo 0.81649658092772603273242802490197

    @Test
    fun testStdDeviationSequence() = sequenceOf(0, 5, 7).standardDeviation() shouldEqualTo 2.9439202887759489515880142423198

    @Test
    fun testStdDeviationArray() = arrayOf(1, 4, 6, 10).standardDeviation() shouldEqualTo 3.2691742076555051641777364878947

}


