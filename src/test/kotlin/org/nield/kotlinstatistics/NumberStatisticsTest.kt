package org.nield.kotlinstatistics

import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class NumberStatisticsTest {

    val doubleVector = sequenceOf(1.0, 3.0, 5.0, 11.0)
    val intVector = doubleVector.map { it.toInt() }
    val longVector = intVector.map { it.toLong() }
    val bigDecimalVector = doubleVector.map(BigDecimal::valueOf)

    val groups = sequenceOf("A","A","B", "B")

    @Test
    fun descriptiveStatistics() {

        assertTrue(doubleVector.descriptiveStatistics.mean == 5.0)
        assertTrue(intVector.descriptiveStatistics.mean == 5.0)
        assertTrue(longVector.descriptiveStatistics.mean == 5.0)
        assertTrue(bigDecimalVector.descriptiveStatistics.mean == 5.0)
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
    }

    @Test
    fun geometricMean() {
        doubleVector.asSequence().geometricMean().let { assertTrue(it == 3.584024634215721) }
        intVector.asSequence().geometricMean().let { assertTrue(it == 3.584024634215721) }
        longVector.asSequence().geometricMean().let { assertTrue(it == 3.584024634215721) }
        bigDecimalVector.asSequence().geometricMean().let { assertTrue(it == 3.584024634215721) }
    }

    @Test
    fun median() {
        doubleVector.asSequence().median().let { assertTrue(it == 4.0) }
        doubleVector.asSequence().take(3).median().let { assertTrue(it == 3.0) }
        intVector.asSequence().median().let { assertTrue(it == 4.0) }
        longVector.asSequence().median().let { assertTrue(it == 4.0) }
        bigDecimalVector.asSequence().median().let { assertTrue(it == 4.0) }
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
    }

    @Test
    fun percentile() {
        doubleVector.asSequence().percentile(50.0).let { assertTrue(it == 4.0) }
        intVector.asSequence().percentile(50.0).let { assertTrue(it == 4.0) }
        longVector.asSequence().percentile(50.0).let { assertTrue(it == 4.0) }
        bigDecimalVector.asSequence().percentile(50.0).let { assertTrue(it == 4.0) }
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
    }

    @Test
    fun variance() {
        val r = 18.666666666666668
        doubleVector.asSequence().variance().let { assertTrue(it == r) }
        intVector.asSequence().variance().let { assertTrue(it == r) }
        longVector.asSequence().variance().let { assertTrue(it == r) }
        bigDecimalVector.asSequence().variance().let { assertTrue(it == r) }
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
    }

    @Test
    fun sumOfSquares() {
        val r = 156.0
        doubleVector.asSequence().sumOfSquares().let { assertTrue(it == r) }
        intVector.asSequence().sumOfSquares().let { assertTrue(it == r) }
        longVector.asSequence().sumOfSquares().let { assertTrue(it == r) }
        bigDecimalVector.asSequence().sumOfSquares().let { assertTrue(it == r) }
    }

    @Test
    fun standardDeviation() {
        val r = 4.320493798938574
        doubleVector.asSequence().standardDeviation().let { assertTrue(it == r) }
        intVector.asSequence().standardDeviation().let { assertTrue(it == r) }
        longVector.asSequence().standardDeviation().let { assertTrue(it == r) }
        bigDecimalVector.asSequence().standardDeviation().let { assertTrue(it == r) }
    }

    @Test
    fun standardDeviationBy() {
        println(doubleVector.standardDeviation())
        groups.zip(doubleVector).standardDeviationBy()
                .let {
                    assertTrue(it["A"]!! == 2.0 && it["B"]!! == 18.0)
                }
        groups.zip(intVector).standardDeviationBy()
                .let {
                    assertTrue(it["A"]!! == 2.0 && it["B"]!! == 18.0)
                }
        groups.zip(longVector).standardDeviationBy()
                .let {
                    assertTrue(it["A"]!! == 2.0 && it["B"]!! == 18.0)
                }
        groups.zip(bigDecimalVector).standardDeviationBy()
                .let {
                    assertTrue(it["A"]!! == 2.0 && it["B"]!! == 18.0)
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
        doubleVector.asSequence().normalize().let { assertTrue(Arrays.equals(it, r)) }
        intVector.asSequence().normalize().let { assertTrue(Arrays.equals(it, r)) }
        longVector.asSequence().normalize().let { assertTrue(Arrays.equals(it, r)) }
        bigDecimalVector.asSequence().normalize().let { assertTrue(Arrays.equals(it, r)) }
    }
}