package org.nield.kotlinstatistics

import org.junit.Assert
import org.junit.Test
import kotlin.coroutines.experimental.buildSequence

class LongStatisticsTest {

    val longVector = sequenceOf(1L, 3L, 5L, 11L)
    val groups = sequenceOf("A","A","B", "B")


    @Test
    fun sumBy() {
        val r = mapOf("A" to 4L, "B" to 16L)

        groups.zip(longVector).sumBy().let { Assert.assertTrue(it == r) }

        groups.zip(longVector).sumBy(
                keySelector = {it.first},
                longSelector = {it.second}
        ).let { Assert.assertTrue(it["A"] == r["A"] && it["B"] == r["B"]) }
    }

    @Test
    fun averageBy() {
        val r = mapOf("A" to 2.0, "B" to 8.0)

        groups.zip(longVector).averageBy(
                keySelector = {it.first},
                longSelector = {it.second}
        ).let { Assert.assertTrue(it == r) }
    }


    @Test
    fun binTest() {
        val binned = sequenceOf(
                longVector,
                longVector.map { it + 100L },
                longVector.map { it + 200L }
        ).flatMap { it }
        .zip(groups.repeat())
        .binByLong(
                binSize = 100L,
                valueSelector = {it.first},
                rangeStart = 0L
        )

        Assert.assertTrue(binned.bins.size == 3)
        Assert.assertTrue(binned[5L]!!.range == 0L..99L)
        Assert.assertTrue(binned[105L]!!.range.let { it.start == 100L && it.endInclusive == 199L })
        Assert.assertTrue(binned[205L]!!.range.let { it.start == 200L && it.endInclusive == 299L })
    }
    private fun <T> Sequence<T>.repeat() : Sequence<T> = buildSequence {
        while(true) yieldAll(this@repeat)
    }
}