package org.nield.kotlinstatistics

import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import kotlin.coroutines.experimental.buildSequence

class BigDecimalStatisticsTest {

    val bigDecimalVector = sequenceOf(1, 3, 5, 11).map { BigDecimal.valueOf(it.toDouble())}
    val groups = sequenceOf("A","A","B", "B")


    @Test
    fun sumBy() {
        val r = mapOf("A" to BigDecimal.valueOf(4.0), "B" to BigDecimal.valueOf(16.0))

        groups.zip(bigDecimalVector).sumBy().let { Assert.assertTrue(it == r) }

        groups.zip(bigDecimalVector).sumBy(
                keySelector = {it.first},
                bigDecimalSelector = {it.second}
        ).let { Assert.assertTrue(it == r) }
    }

    @Test
    fun averageBy() {

        groups.zip(bigDecimalVector).averageBy(
                keySelector = {it.first},
                bigDecimalSelector = {it.second}
        ).let { Assert.assertTrue(it["A"] == BigDecimal.valueOf(2.0) && it["B"] == BigDecimal.valueOf(8.0)) }
    }
}