package org.nield.kotlinstatistics

import org.apache.commons.math.stat.StatUtils
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics

val Iterable<Int>.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }
val Sequence<Int>.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }
val Array<out Int>.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }
val IntArray.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }

fun Iterable<Int>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )
fun Sequence<Int>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )
fun Array<out Int>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )
fun IntArray.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )

fun Iterable<Int>.median() = percentile(50.0)
fun Sequence<Int>.median() = percentile(50.0)
fun Array<out Int>.median() = percentile(50.0)
fun IntArray.median() = percentile(50.0)

fun Iterable<Int>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)
fun Sequence<Int>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)
fun Array<out Int>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray() , percentile)
fun IntArray.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)

fun Iterable<Int>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Int>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Int>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun IntArray.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())

fun Iterable<Int>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Int>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Int>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun IntArray.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())

fun Iterable<Int>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Sequence<Int>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Array<out Int>.standardDeviation() = descriptiveStatistics.standardDeviation
fun IntArray.standardDeviation() = descriptiveStatistics.standardDeviation

fun Iterable<Int>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Int>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Int>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun IntArray.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())

val Iterable<Int>.kurtosis get() = descriptiveStatistics.kurtosis
val Sequence<Int>.kurtosis get() = descriptiveStatistics.kurtosis
val Array<out Int>.kurtosis get() = descriptiveStatistics.kurtosis
val IntArray.kurtosis get() = descriptiveStatistics.kurtosis

val Iterable<Int>.skewness get() = descriptiveStatistics.skewness
val Sequence<Int>.skewness get() = descriptiveStatistics.skewness
val Array<out Int>.skewness get() = descriptiveStatistics.skewness
val IntArray.skewness get() = descriptiveStatistics.skewness


// AGGREGATION OPERATORS
inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        groupApply(keySelector, intMapper) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        asSequence().sumBy(keySelector, intMapper)

inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        groupApply(keySelector, intMapper) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        asSequence().averageBy(keySelector, intMapper)

inline fun <T,K> Sequence<T>.minBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        groupApply(keySelector, intMapper) { it.min() }

inline fun <T,K> Iterable<T>.minBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        asSequence().minBy(keySelector, intMapper)

inline fun <T,K> Sequence<T>.maxBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        groupApply(keySelector, intMapper) { it.max() }

inline fun <T,K> Iterable<T>.maxBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        asSequence().maxBy(keySelector, intMapper)

inline fun <T,K> Sequence<T>.medianBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        groupApply(keySelector, intMapper) { it.median() }

inline fun <T,K> Iterable<T>.medianBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        asSequence().medianBy(keySelector, intMapper)

inline fun <T,K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        groupApply(keySelector, intMapper) { it.variance() }

inline fun <T,K> Iterable<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        asSequence().varianceBy(keySelector, intMapper)

inline fun <T,K> Sequence<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        groupApply(keySelector, intMapper) { it.standardDeviation() }

inline fun <T,K> Iterable<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        asSequence().standardDeviationBy(keySelector, intMapper)
