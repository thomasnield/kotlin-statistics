package org.nield.kotlinstatistics

import org.junit.Assert
import org.junit.Test
import kotlin.coroutines.experimental.buildSequence

class IntegerStatisticsTest {


    val intVector = sequenceOf(1, 3, 5, 11)
    val groups = sequenceOf("A","A","B", "B")


    @Test
    fun sumBy() {
        val r = mapOf("A" to 4, "B" to 16)

        groups.zip(intVector).sumBy().let { Assert.assertTrue(it == r) }

        groups.zip(intVector).sumBy(
                keySelector = {it.first},
                intSelector = {it.second}
        ).let { Assert.assertTrue(it == r) }
    }

    @Test
    fun averageBy() {
        val r = mapOf("A" to 2.0, "B" to 8.0)

        groups.zip(intVector).averageBy(
                keySelector = {it.first},
                intSelector = {it.second}
        ).let { Assert.assertTrue(it == r) }
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
                        valueSelector = {it.first},
                        rangeStart = 0
                )

        Assert.assertTrue(binned.bins.size == 3)
        Assert.assertTrue(binned[5]!!.range == 0..99)
        Assert.assertTrue(binned[105]!!.range.let { it.start == 100 && it.endInclusive == 199 })
        Assert.assertTrue(binned[205]!!.range.let { it.start == 200 && it.endInclusive == 299 })
    }
    private fun <T> Sequence<T>.repeat() : Sequence<T> = buildSequence {
        while(true) yieldAll(this@repeat)
    }
}


