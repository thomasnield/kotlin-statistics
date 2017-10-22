package org.nield.kotlinstatistics

import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class NumberStatisticsTest {

    val doubleVector = sequenceOf(1.0, 3.0, 5.0, 11.0)
    val intVector = sequenceOf(1, 3, 5, 11)
    val longVector = sequenceOf(1L, 3L, 5L, 11L)
    val bigDecimalVector = sequenceOf(BigDecimal.valueOf(1.0), BigDecimal.valueOf(3.0), BigDecimal.valueOf(5.0), BigDecimal.valueOf(11.0))

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

}