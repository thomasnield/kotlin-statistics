package org.nield.kotlinstatistics

import kotlin.test.Test
import kotlin.test.assertEquals


class ComparableStatisticsTest {

    val doubleVector = sequenceOf(1.0, 3.0, 5.0, 11.0)
    val groups = sequenceOf("A", "A", "B", "B")

    val grouped = groups.zip(doubleVector)

    @Test
    fun minBy() {

        val r = mapOf("A" to 1.0, "B" to 5.0)

        assertEquals(grouped.minBy(), r)

        grouped.minBy(
            keySelector = { it.first },
            valueSelector = { it.second }
        ).let { assertEquals(it, r) }

    }

    @Test
    fun maxBy() {

        val r = mapOf("A" to 3.0, "B" to 11.0)

        assertEquals(grouped.maxBy(), r)

        grouped.maxBy(
            keySelector = { it.first },
            valueSelector = { it.second }
        ).let { assertEquals(it, r) }

    }
}
