package org.nield.kotlinstatistics

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class IntegerStatisticsTest {


    val intVector = sequenceOf(1, 3, 5, 11)
    val groups = sequenceOf("A", "A", "B", "B")


    @Test
    fun sumBy() {
        val r = mapOf("A" to 4, "B" to 16)

        assertEquals(groups.zip(intVector).sumBy(), r)

        groups.zip(intVector).sumBy(
            keySelector = { it.first },
            intSelector = { it.second }
        ).let { assertEquals(it, r) }
    }

    @Test
    fun averageBy() {
        val r = mapOf("A" to 2.0, "B" to 8.0)

        groups.zip(intVector).averageBy(
            keySelector = { it.first },
            intSelector = { it.second }
        ).let { assertEquals(it, r) }
    }

    @Test
    fun binTest() {
        val binned = sequenceOf(
            intVector,
            intVector.map { it + 100 },
            intVector.map { it + 200 }
        ).flatMap { it }
            .zip(groups.repeat())
            .binByInt(
                binSize = 100,
                valueSelector = { it.first },
                rangeStart = 0
            )

        assertEquals(binned.bins.size, 3)
        assertTrue(binned[5]!!.range.let { it.lowerBound == 0 && it.upperBound == 99 })
        assertTrue(binned[105]!!.range.let { it.lowerBound == 100 && it.upperBound == 199 })
        assertTrue(binned[205]!!.range.let { it.lowerBound == 200 && it.upperBound == 299 })
    }

    private fun <T> Sequence<T>.repeat(): Sequence<T> = sequence {
        while (true) yieldAll(this@repeat)
    }
}


