package org.nield.kotlinstatistics

import org.junit.Assert
import org.junit.Test

class DoubleStatisticsTest {

    val doubleVector = sequenceOf(0.0, 1.0, 3.0, 5.0, 11.0)
    val groups = sequenceOf("A", "B", "B", "C", "C")

    @Test
    fun sumBy() {
        val r = mapOf("A" to 0.0, "B" to 4.0, "C" to 16.0)

        groups.zip(doubleVector).sumBy().let { Assert.assertTrue(it["A"] == r["A"] && it["B"] == r["B"] && it["C"] == r["C"]) }

        groups.zip(doubleVector).sumBy(
                keySelector = {it.first},
                doubleSelector = {it.second}
        ).let { Assert.assertTrue(it["A"] == r["A"] && it["B"] == r["B"]) }
    }

    @Test
    fun medianBy() {
        val r = mapOf("A" to 0.0, "B" to 2.0, "C" to 8.0)

        groups.zip(doubleVector).medianBy().let { Assert.assertTrue(it == r) }

        groups.zip(doubleVector).medianBy(
                keySelector = {it.first},
                doubleSelector = {it.second}
        ).let { Assert.assertTrue(it == r) }
    }

    @Test
    fun averageBy() {
        val r = mapOf("A" to 0.0, "B" to 2.0, "C" to 8.0)

        groups.zip(doubleVector).averageBy(
                keySelector = {it.first},
                doubleSelector = {it.second}
        ).let { Assert.assertTrue(it == r ) }
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
                        valueSelector = {it.first},
                        rangeStart = 0.0
                )

        Assert.assertTrue(binned.bins.size == 3)
        println(binned.bins)
        Assert.assertTrue(binned[5.0]!!.range.let { it.lowerBound == 0.0 && it.upperBound == 100.0 })
        Assert.assertTrue(binned[105.0]!!.range.let { it.lowerBound == 100.0 && it.upperBound == 200.0 })
        Assert.assertTrue(binned[205.0]!!.range.let { it.lowerBound == 200.0 && it.upperBound == 300.0 })
    }
    private fun <T> Sequence<T>.repeat() : Sequence<T> = sequence {
        while(true) yieldAll(this@repeat)
    }
}