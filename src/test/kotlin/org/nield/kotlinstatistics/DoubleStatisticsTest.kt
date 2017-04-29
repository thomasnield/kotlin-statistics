package org.nield.kotlinstatistics

import junit.framework.Assert.assertTrue
import org.amshove.kluent.shouldEqualTo
import org.junit.Test
import java.math.BigDecimal

class DoubleStatisticsTest {

    @Test
    fun testMedian1() = assertTrue(sequenceOf(1.0, 3.0, 5.0).median() == 3.0)

    fun test() {
        val median = sequenceOf(1.0, 3.0, 5.0).median()

        println(median)
    }

    @Test
    fun testMedian2() = assertTrue(arrayOf(1.0, 3.0, 4.0, 5.0).median() == 3.5)

    @Test
    fun testVariance1() = listOf(2.0, 3.0, 4.0).variance() shouldEqualTo 0.66666666666666666666666666666667

    @Test
    fun testVariance2() = listOf(1.0, 4.0, 6.0, 10.0).variance() shouldEqualTo 10.6875

    @Test
    fun testByOperators() {
        class Item(val name: String, val value: Double)

        val sequence = sequenceOf(
                Item("Alpha", 4.0),
                Item("Beta", 6.0),
                Item("Gamma", 7.2),
                Item("Delta", 9.2),
                Item("Epsilon", 6.8),
                Item("Zeta", 2.4),
                Item("Iota", 8.8)
        )

        // find sums by name length
        val sumsByLengths = sequence
               .sumBy(keySelector = { it.name.length }, doubleMapper = {it.value} )

        println("Sums by lengths: $sumsByLengths")

        // find averages by name length
        val averagesByLength = sequence
                .averageBy(keySelector = { it.name.length }, doubleMapper = {it.value})

        println("Averages by lengths: $averagesByLength")

        //find standard deviations by name length
        val standardDeviationsByLength = sequence
                .standardDeviationBy(keySelector = { it.name.length }, doubleMapper = {it.value})

        println("Std Devs by lengths: $standardDeviationsByLength")
    }

    @Test
    fun testMultipleKeys() {

        //declare Product class
        class Product(val id: Int,
                      val name: String,
                      val category: String,
                      val section: Int,
                      val defectRate: Double)

        // Create list of Products
        val products = listOf(Product(1, "Rayzeon", "ABR", 3, 1.1),
                Product(2, "ZenFire", "ABZ", 4, 0.7),
                Product(3, "HydroFlux", "ABR", 3, 1.9),
                Product(4, "IceFlyer", "ZBN", 1, 2.4),
                Product(5, "FireCoyote", "ABZ", 4, 3.2),
                Product(6, "LightFiber", "ABZ",2,  5.1),
                Product(7, "PyroKit", "ABR", 3, 1.4),
                Product(8, "BladeKit", "ZBN", 1, 0.5),
                Product(9, "NightHawk", "ZBN", 1, 3.5),
                Product(10, "NoctoSquirrel", "ABR", 2, 1.1),
                Product(11, "WolverinePack", "ABR", 3, 1.2)
                )

        // Data Class for Grouping
        data class CategoryAndSection(val category: String, val section: Int)

        // Get Count by Category and Section
        val countByCategoryAndSection =
                products.countBy { CategoryAndSection(it.category, it.section) }

        println("Counts by Category and Section")
        countByCategoryAndSection.entries.forEach { println(it) }

        // Get Average Defect Rate by Category and Section
        val averageDefectByCategoryAndSection =
                products.averageBy(keySelector = { CategoryAndSection(it.category, it.section) }, doubleMapper = { it.defectRate })

        println("\nAverage Defect Rate by Category and Section")
        averageDefectByCategoryAndSection.entries.forEach { println(it) }
    }
}