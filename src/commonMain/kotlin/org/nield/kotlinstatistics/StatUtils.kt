package org.nield.kotlinstatistics

expect object StatUtils {
    fun geometricMean(array: DoubleArray): Double
    fun percentile(array: DoubleArray, percentile: Double): Double
    fun variance(array: DoubleArray): Double
    fun sumSq(array: DoubleArray): Double
    fun normalize(array: DoubleArray): DoubleArray
}