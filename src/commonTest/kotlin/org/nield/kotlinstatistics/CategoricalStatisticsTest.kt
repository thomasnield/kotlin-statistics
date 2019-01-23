package org.nield.kotlinstatistics

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CategoricalStatisticsTest {

    val strings = sequenceOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon")

    @Test
    fun testMode1() {
        assertTrue(listOf(2, 54, 67, 3, 4, 5, 2, 2).mode().toSet() == setOf(2))
    }

    @Test
    fun testMode2() {
        assertTrue(listOf(2, 4, 54, 4, 67, 3, 4, 5, 2, 2).mode().toSet() == setOf(2, 4))
    }

    @Test
    fun testGroupApply() {
        assertEquals(
            strings.groupApply(
                { it.length },
                {
                    it.asSequence().flatMap { it.split("").asSequence() }.filter { it.isNotEmpty() }.count()
                }), mapOf(5 to 15, 4 to 4, 7 to 7)
        )
    }

    @Test
    fun countBy() {

        strings.map { it.length }.countBy()
            .let { it == mapOf(5 to 3, 4 to 1, 7 to 1) }

        strings.countBy { it.length }
            .let { it == mapOf(5 to 3, 4 to 1, 7 to 1) }
    }
}