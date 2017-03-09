package org.nield.kotlinstatistics

import junit.framework.Assert.assertTrue
import org.junit.Test

class CategoricalStatisticsTest {
    @Test
    fun testMode1() {
        assertTrue(listOf(2,54,67,3,4,5,2,2).mode().toSet() == setOf(2))
    }
    @Test
    fun testMode2() {
        assertTrue(listOf(2,4,54,4,67,3,4,5,2,2).mode().toSet() == setOf(2,4))
    }
}