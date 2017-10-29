package org.nield.kotlinstatistics

import org.junit.Assert
import org.junit.Test
import kotlin.coroutines.experimental.buildSequence

class DoubleStatisticsTest {

    val doubleVector = sequenceOf(1.0, 3.0, 5.0, 11.0)
    val groups = sequenceOf("A","A","B", "B")

    @Test
    fun sumBy() {
        val r = mapOf("A" to 4.0, "B" to 16.0)

        groups.zip(doubleVector).sumBy().let { Assert.assertTrue(it == r) }

        groups.zip(doubleVector).sumBy(
                keySelector = {it.first},
                doubleSelector = {it.second}
        ).let { Assert.assertTrue(it["A"] == r["A"] && it["B"] == r["B"]) }
    }

    @Test
    fun averageBy() {
        val r = mapOf("A" to 2.0, "B" to 8.0)

        groups.zip(doubleVector).averageBy(
                keySelector = {it.first},
                doubleSelector = {it.second}
        ).let { Assert.assertTrue(it == r) }
    }


    @Test
    fun binTest() {
        val binned = sequenceOf(
                doubleVector,
                doubleVector.map { it + 100.0 },
                doubleVector.map { it + 200.0 }
        ).flatMap { it }
                .zip(groups.repeat())
                .binByDouble(
                        binSize = 100.0,
                        gapSize = .01,
                        valueSelector = {it.first},
                        rangeStart = 0.0
                )

        Assert.assertTrue(binned.bins.size == 3)
        println(binned.bins)
        Assert.assertTrue(binned[5.0]!!.range == 0.0..100.00)
        Assert.assertTrue(binned[105.0]!!.range.let { it.start == 100.01 && it.endInclusive == 200.0 })
        Assert.assertTrue(binned[205.0]!!.range.let { it.start == 200.01 && it.endInclusive == 300.00 })
    }
    private fun <T> Sequence<T>.repeat() : Sequence<T> = buildSequence {
        while(true) yieldAll(this@repeat)
    }
}