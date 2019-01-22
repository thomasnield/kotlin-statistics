package org.nield.kotlinstatistics


interface Descriptives {
    val windowSize: Int
    val mean: Double
    val geometricMean: Double
    val variance: Double
    val standardDeviation: Double
    val skewness: Double
    val kurtosis: Double
    val max: Double
    val min: Double
    val size: Long
    val sum: Double
    val sumSquared: Double
    val values: DoubleArray
    fun percentile(percentile: Double): Double
    operator fun get(index: Int): Double
}

internal expect fun Sequence<Double>.descriptiveStatistics(): Descriptives
