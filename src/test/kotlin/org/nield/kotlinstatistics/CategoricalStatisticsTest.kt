package org.nield.kotlinstatistics

import junit.framework.Assert.assertTrue
import org.amshove.kluent.shouldEqual
import org.junit.Test

class CategoricalStatisticsTest {
    @Test
    fun testMode1() {
        // 2 appears 3 times
        assertTrue(listOf(2,54,67,3,4,5,2,2).mode().toSet() == setOf(2))
    }
    @Test
    fun testMode2() {
        // 2 and 4 both appear 3 times
        assertTrue(listOf(2,4,54,4,67,3,4,5,2,2).mode().toSet() == setOf(2,4))
    }

    @Test
    fun testCountBy1() {
        // 2 appears 2 times, 1 appears 3 times
        listOf(2, 4, 2, 5, 8, 1,1,1).countBy().toSet() shouldEqual setOf(2 to 2, 4 to 1, 5 to 1, 8 to 1, 1 to 3)
    }

}