package org.nield.kotlinstatistics

import org.junit.Test

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

        assertTrue(doubleVector.descriptiveStatistics.min == 1.0)
        assertTrue(intVector.descriptiveStatistics.min == 1.0)
        assertTrue(longVector.descriptiveStatistics.min == 1.0)
        assertTrue(bigDecimalVector.descriptiveStatistics.min == 1.0)

        assertTrue(doubleVector.descriptiveStatistics.max == 11.0)
        assertTrue(intVector.descriptiveStatistics.max == 11.0)
        assertTrue(longVector.descriptiveStatistics.max == 11.0)
        assertTrue(bigDecimalVector.descriptiveStatistics.max == 11.0)


        assertTrue(doubleVector.descriptiveStatistics.size == 4L)
        assertTrue(intVector.descriptiveStatistics.size == 4L)
        assertTrue(longVector.descriptiveStatistics.size == 4L)
        assertTrue(bigDecimalVector.descriptiveStatistics.size == 4L)
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
                valueSelector = {it.second}
                )
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


        groups.zip(doubleVector).medianBy(
                keySelector = {it.first},
                valueSelector = {it.second}
        )
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

        groups.zip(bigDecimalVector).percentileBy(
                    percentile = 50.0,
                    keySelector = {it.first},
                    valueSelector = {it.second}
                )
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

        groups.zip(bigDecimalVector).varianceBy(
                keySelector = {it.first},
                valueSelector = {it.second}
        )
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

        val r = mapOf("A" to 1.4142135623730951, "B" to 4.242640687119285)

        groups.zip(doubleVector).standardDeviationBy()
                .let {
                    assertTrue(it == r)
                }
        groups.zip(intVector).standardDeviationBy()
                .let {
                    assertTrue(it == r)
                }
        groups.zip(longVector).standardDeviationBy()
                .let {
                    assertTrue(it == r)
                }
        groups.zip(bigDecimalVector).standardDeviationBy()
                .let {
                    assertTrue(it == r)
                }

        groups.zip(bigDecimalVector).standardDeviationBy(
                keySelector = {it.first},
                valueSelector = {it.second}
        )
        .let {
            assertTrue(it == r)
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

    @Test
    fun kurtosis() {
        val r = 1.4999999999999947

        assertTrue(doubleVector.kurtosis == r)
        assertTrue(intVector.kurtosis == r)
        assertTrue(longVector.kurtosis == r)
        assertTrue(bigDecimalVector.kurtosis == r)
    }

    @Test
    fun skewness() {
        val r = 1.1903401282789945

        assertTrue(doubleVector.skewness == r)
        assertTrue(intVector.skewness == r)
        assertTrue(longVector.skewness == r)
        assertTrue(bigDecimalVector.skewness == r)
    }


    @Test
    fun geometricMeanBy() {

        val r = mapOf("A" to 1.7320508075688774, "B" to 7.416198487095664)

        groups.zip(doubleVector).geometricMeanBy()
                .let {
                    assertTrue(it == r)
                }
        groups.zip(intVector).geometricMeanBy()
                .let {
                    assertTrue(it == r)
                }
        groups.zip(longVector).geometricMeanBy()
                .let {
                    assertTrue(it == r)
                }
        groups.zip(bigDecimalVector).geometricMeanBy()
                .let {
                    assertTrue(it == r)
                }

        groups.zip(bigDecimalVector).geometricMeanBy(
                keySelector = {it.first},
                valueSelector = {it.second}
        )
        .let {
            assertTrue(it == r)
        }
    }

    @Test
    fun simpleRegression() {
        doubleVector.zip(doubleVector.map { it * 2 })
                .simpleRegression()
                .let { assertTrue(it.slope == 2.0) }

        intVector.zip(intVector.map { it * 2 })
                .simpleRegression()
                .let { assertTrue(it.slope == 2.0) }

        longVector.zip(longVector.map { it * 2 })
                .simpleRegression()
                .let { assertTrue(it.slope == 2.0) }

        bigDecimalVector.zip(bigDecimalVector.map { it * BigDecimal.valueOf(2L) })
                .simpleRegression()
                .let { assertTrue(it.slope == 2.0) }

        intVector.zip(intVector.map { it * 2 })
                .simpleRegression(
                        xSelector = {it.first},
                        ySelector = {it.second}
                )
                .let { assertTrue(it.slope == 2.0) }
    }
}