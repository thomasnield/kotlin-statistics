package org.nield.kotlinstatistics

import org.junit.Test
import org.junit.Assert.assertTrue

class ComparableStatisticsTest {

    val doubleVector = sequenceOf(1.0, 3.0, 5.0, 11.0)
    val groups = sequenceOf("A","A","B", "B")

    val grouped = groups.zip(doubleVector)

    @Test
    fun minBy() {

        val r = mapOf("A" to 1.0, "B" to 5.0)

        assertTrue(grouped.minBy() == r)

        grouped.minBy(
                keySelector = { it.first },
                valueSelector = {it.second }
        ).let { assertTrue(it == r)}

    }

    @Test
    fun maxBy() {

        val r = mapOf("A" to 3.0, "B" to 11.0)

        assertTrue(grouped.maxBy() == r)

        grouped.maxBy(
                keySelector = { it.first },
                valueSelector = {it.second }
        ).let { assertTrue(it == r)}

    }
}
