package org.nield.kotlinstatistics

import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Implementations partially taken from **commons-math**
 */
class DescriptiveStatistics(val values: DoubleArray) {
    val mean: Double by lazy { values.average() }

    val variance: Double by lazy {
        return@lazy when (size) {
            0 -> Double.NaN
            1 -> 0.0
            else -> {
                var accum = 0.0
                var accum2 = 0.0
                for (value in values) {
                    val dev = value - mean
                    accum += dev * dev
                    accum2 += dev
                }
                (accum - accum2 * accum2 / size) / (size - 1)
            }
        }
    }

    val standardDeviation: Double by lazy { sqrt(variance) }
    val skewness: Double by lazy {
        var accum = 0.0
        var accum2 = 0.0
        for (value in values) {
            val d = value - mean
            accum += d * d
            accum2 += d
        }
        val variance = (accum - accum2 * accum2 / size) / (size - 1)

        var accum3 = 0.0
        for (value in values) {
            val d = value - mean
            accum3 += d * d * d
        }
        accum3 /= variance * sqrt(variance)

        // Get N
        val n0 = size.toDouble()

        // Calculate skewness
        return@lazy n0 / ((n0 - 1) * (n0 - 2)) * accum3
    }

    val kurtosis: Double by lazy {

        // Sum the ^4 of the distance from the mean divided by the
        // standard deviation
        val accum3 = values.sumByDouble { (it - mean).pow(4) } / standardDeviation.pow(4)

        // Get N
        val n0 = size.toDouble()

        val coefficientOne = n0 * (n0 + 1) / ((n0 - 1) * (n0 - 2) * (n0 - 3))
        val termTwo = 3 * (n0 - 1).pow(2) / ((n0 - 2) * (n0 - 3))

        // Calculate kurtosis
        return@lazy coefficientOne * accum3 - termTwo
    }

    val max: Double by lazy { values.max() ?: Double.NaN }
    val min: Double by lazy { values.min() ?: Double.NaN }
    val size: Int get() = values.size
    val sum: Double by lazy { values.sum() }
    val sumSquared: Double by lazy { values.sumByDouble { it * it } }

    fun percentile(percentile: Double): Double {
        TODO("implement algorithm")
//        if (percentile > 100 || percentile <= 0) error("$percentile is out of (0,100) range")
//        return when (size) {
//            0 -> Double.NaN
//            1 -> values[0] // always return single value for n = 1
//            else -> {
//                val work = getWorkArray(values, begin, length)
//                val pivotsHeap = getPivots(values)
//                if (work.size == 0) Double.NaN
//                else
//                    estimationType.evaluate(work, pivotsHeap, percentile, kthSelector)
//            }
//        }
    }

    fun get(index: Int): Double = values[index]
}

internal fun Sequence<Double>.stats(): DescriptiveStatistics =
    DescriptiveStatistics(this.toList().toDoubleArray())

internal fun DoubleArray.stats(): DescriptiveStatistics = DescriptiveStatistics(this)
