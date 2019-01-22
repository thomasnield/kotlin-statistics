package org.nield.kotlinstatistics

import java.math.BigDecimal
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NumberStatisticsTest {

    val doubleVector = sequenceOf(1.0, 3.0, 5.0, 11.0)
    val intVector = doubleVector.map { it.toInt() }
    val longVector = intVector.map { it.toLong() }
    val bigDecimalVector = doubleVector.map(BigDecimal::valueOf)

    val groups = sequenceOf("A", "A", "B", "B")


    @Test
    fun descriptiveStatistics() {

        assertEquals(doubleVector.descriptiveStatistics.mean, 5.0)
        assertEquals(intVector.descriptiveStatistics.mean, 5.0)
        assertEquals(longVector.descriptiveStatistics.mean, 5.0)
        assertEquals(bigDecimalVector.descriptiveStatistics.mean, 5.0)

        assertEquals(doubleVector.descriptiveStatistics.min, 1.0)
        assertEquals(intVector.descriptiveStatistics.min, 1.0)
        assertEquals(longVector.descriptiveStatistics.min, 1.0)
        assertEquals(bigDecimalVector.descriptiveStatistics.min, 1.0)

        assertEquals(doubleVector.descriptiveStatistics.max, 11.0)
        assertEquals(intVector.descriptiveStatistics.max, 11.0)
        assertEquals(longVector.descriptiveStatistics.max, 11.0)
        assertEquals(bigDecimalVector.descriptiveStatistics.max, 11.0)


        assertEquals(doubleVector.descriptiveStatistics.size, 4L)
        assertEquals(intVector.descriptiveStatistics.size, 4L)
        assertEquals(longVector.descriptiveStatistics.size, 4L)
        assertEquals(bigDecimalVector.descriptiveStatistics.size, 4L)
    }

    @Test
    fun descriptiveBy() {
        groups.zip(doubleVector).descriptiveStatisticsBy()
            .let {
                assertTrue(it["A"]!!.mean == 2.0 && it["B"]!!.mean == 8.0)
            }

        groups.zip(intVector).descriptiveStatisticsBy()
            .let {
                assertTrue(it["A"]!!.mean == 2.0 && it["B"]!!.mean == 8.0)
            }

        groups.zip(longVector).descriptiveStatisticsBy()
            .let {
                assertTrue(it["A"]!!.mean == 2.0 && it["B"]!!.mean == 8.0)
            }

        groups.zip(bigDecimalVector).descriptiveStatisticsBy()
            .let {
                assertTrue(it["A"]!!.mean == 2.0 && it["B"]!!.mean == 8.0)
            }



        groups.zip(doubleVector).descriptiveStatisticsBy(
            keySelector = { it.first },
            valueSelector = { it.second }
        )
            .let {
                assertTrue(it["A"]!!.mean == 2.0 && it["B"]!!.mean == 8.0)
            }
    }


    @Test
    fun geometricMean() {
        assertEquals(doubleVector.asSequence().geometricMean(), 3.584024634215721)
        assertEquals(intVector.asSequence().geometricMean(), 3.584024634215721)
        assertEquals(longVector.asSequence().geometricMean(), 3.584024634215721)
        assertEquals(bigDecimalVector.asSequence().geometricMean(), 3.584024634215721)
    }

    @Test
    fun median() {
        assertEquals(doubleVector.asSequence().median(), 4.0)
        doubleVector.asSequence().take(3).median().let { assertEquals(it, 3.0) }
        assertEquals(intVector.asSequence().median(), 4.0)
        assertEquals(longVector.asSequence().median(), 4.0)
        assertEquals(bigDecimalVector.asSequence().median(), 4.0)
    }

    @Test
    fun medianBy() {
        groups.zip(doubleVector).medianBy()
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 8.0)
            }
        groups.zip(intVector).medianBy()
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 8.0)
            }
        groups.zip(longVector).medianBy()
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 8.0)
            }
        groups.zip(bigDecimalVector).medianBy()
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 8.0)
            }


        groups.zip(doubleVector).medianBy(
            keySelector = { it.first },
            valueSelector = { it.second }
        )
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 8.0)
            }
    }


    @Test
    fun percentile() {
        assertEquals(doubleVector.asSequence().percentile(50.0), 4.0)
        assertEquals(intVector.asSequence().percentile(50.0), 4.0)
        assertEquals(longVector.asSequence().percentile(50.0), 4.0)
        assertEquals(bigDecimalVector.asSequence().percentile(50.0), 4.0)
    }

    @Test
    fun percentileBy() {
        groups.zip(doubleVector).percentileBy(50.0)
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 8.0)
            }
        groups.zip(intVector).percentileBy(50.0)
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 8.0)
            }
        groups.zip(longVector).percentileBy(50.0)
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 8.0)
            }
        groups.zip(bigDecimalVector).percentileBy(50.0)
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 8.0)
            }

        groups.zip(bigDecimalVector).percentileBy(
            percentile = 50.0,
            keySelector = { it.first },
            valueSelector = { it.second }
        )
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 8.0)
            }
    }

    @Test
    fun variance() {
        val r = 18.666666666666668
        assertEquals(doubleVector.asSequence().variance(), r)
        assertEquals(intVector.asSequence().variance(), r)
        assertEquals(longVector.asSequence().variance(), r)
        assertEquals(bigDecimalVector.asSequence().variance(), r)
    }

    @Test
    fun varianceBy() {
        groups.zip(doubleVector).varianceBy()
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 18.0)
            }
        groups.zip(intVector).varianceBy()
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 18.0)
            }
        groups.zip(longVector).varianceBy()
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 18.0)
            }
        groups.zip(bigDecimalVector).varianceBy()
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 18.0)
            }

        groups.zip(bigDecimalVector).varianceBy(
            keySelector = { it.first },
            valueSelector = { it.second }
        )
            .let {
                assertTrue(it["A"]!! == 2.0 && it["B"]!! == 18.0)
            }
    }

    @Test
    fun sumOfSquares() {
        val r = 156.0
        assertEquals(doubleVector.asSequence().sumOfSquares(), r)
        assertEquals(intVector.asSequence().sumOfSquares(), r)
        assertEquals(longVector.asSequence().sumOfSquares(), r)
        assertEquals(bigDecimalVector.asSequence().sumOfSquares(), r)
    }

    @Test
    fun standardDeviation() {
        val r = 4.320493798938574
        assertEquals(doubleVector.asSequence().standardDeviation(), r)
        assertEquals(intVector.asSequence().standardDeviation(), r)
        assertEquals(longVector.asSequence().standardDeviation(), r)
        assertEquals(bigDecimalVector.asSequence().standardDeviation(), r)
    }

    @Test
    fun standardDeviationBy() {

        val r = mapOf("A" to 1.4142135623730951, "B" to 4.242640687119285)

        assertEquals(groups.zip(doubleVector).standardDeviationBy(), r)
        assertEquals(groups.zip(intVector).standardDeviationBy(), r)
        assertEquals(groups.zip(longVector).standardDeviationBy(), r)
        assertEquals(groups.zip(bigDecimalVector).standardDeviationBy(), r)

        groups.zip(bigDecimalVector).standardDeviationBy(
            keySelector = { it.first },
            valueSelector = { it.second }
        )
            .let {
                assertEquals(it, r)
            }
    }

    @Test
    fun normalize() {
        val r = doubleArrayOf(
            -0.9258200997725514,
            -0.4629100498862757,
            0.0,
            1.3887301496588271
        )
        assertTrue(Arrays.equals(doubleVector.asSequence().normalize(), r))
        assertTrue(Arrays.equals(intVector.asSequence().normalize(), r))
        assertTrue(Arrays.equals(longVector.asSequence().normalize(), r))
        assertTrue(Arrays.equals(bigDecimalVector.asSequence().normalize(), r))
    }

    @Test
    fun kurtosis() {
        val r = 1.4999999999999947

        assertEquals(doubleVector.kurtosis, r)
        assertEquals(intVector.kurtosis, r)
        assertEquals(longVector.kurtosis, r)
        assertEquals(bigDecimalVector.kurtosis, r)
    }

    @Test
    fun skewness() {
        val r = 1.1903401282789945

        assertEquals(doubleVector.skewness, r)
        assertEquals(intVector.skewness, r)
        assertEquals(longVector.skewness, r)
        assertEquals(bigDecimalVector.skewness, r)
    }


    @Test
    fun geometricMeanBy() {

        val r = mapOf("A" to 1.7320508075688774, "B" to 7.416198487095664)

        assertEquals(groups.zip(doubleVector).geometricMeanBy(), r)
        assertEquals(groups.zip(intVector).geometricMeanBy(), r)
        assertEquals(groups.zip(longVector).geometricMeanBy(), r)
        assertEquals(groups.zip(bigDecimalVector).geometricMeanBy(), r)

        groups.zip(bigDecimalVector).geometricMeanBy(
            keySelector = { it.first },
            valueSelector = { it.second }
        )
            .let {
                assertEquals(it, r)
            }
    }

    @Test
    fun simpleRegression() {
        doubleVector.zip(doubleVector.map { it * 2 })
            .simpleRegression()
            .let { assertEquals(it.slope, 2.0) }

        intVector.zip(intVector.map { it * 2 })
            .simpleRegression()
            .let { assertEquals(it.slope, 2.0) }

        longVector.zip(longVector.map { it * 2 })
            .simpleRegression()
            .let { assertEquals(it.slope, 2.0) }

        bigDecimalVector.zip(bigDecimalVector.map { it * BigDecimal.valueOf(2L) })
            .simpleRegression()
            .let { assertEquals(it.slope, 2.0) }

        intVector.zip(intVector.map { it * 2 })
            .simpleRegression(
                xSelector = { it.first },
                ySelector = { it.second }
            )
            .let { assertEquals(it.slope, 2.0) }
    }
}