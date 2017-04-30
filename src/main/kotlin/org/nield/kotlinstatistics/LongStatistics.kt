package org.nield.kotlinstatistics

import org.apache.commons.math.stat.StatUtils
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics

val Iterable<Long>.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }
val Sequence<Long>.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }
val Array<out Long>.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }
val LongArray.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }

fun Iterable<Long>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )
fun Sequence<Long>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )
fun Array<out Long>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )
fun LongArray.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )

fun Iterable<Long>.median() = percentile(50.0)
fun Sequence<Long>.median() = percentile(50.0)
fun Array<out Long>.median() = percentile(50.0)
fun LongArray.median() = percentile(50.0)

fun Iterable<Long>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)
fun Sequence<Long>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)
fun Array<out Long>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray() , percentile)
fun LongArray.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)

fun Iterable<Long>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Long>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Long>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun LongArray.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())

fun Iterable<Long>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Long>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Long>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun LongArray.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())

fun Iterable<Long>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Sequence<Long>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Array<out Long>.standardDeviation() = descriptiveStatistics.standardDeviation
fun LongArray.standardDeviation() = descriptiveStatistics.standardDeviation

fun Iterable<Long>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Long>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Long>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun LongArray.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())

val Iterable<Long>.kurtosis get() = descriptiveStatistics.kurtosis
val Sequence<Long>.kurtosis get() = descriptiveStatistics.kurtosis
val Array<out Long>.kurtosis get() = descriptiveStatistics.kurtosis
val LongArray.kurtosis get() = descriptiveStatistics.kurtosis

val Iterable<Long>.skewness get() = descriptiveStatistics.skewness
val Sequence<Long>.skewness get() = descriptiveStatistics.skewness
val Array<out Long>.skewness get() = descriptiveStatistics.skewness
val LongArray.skewness get() = descriptiveStatistics.skewness



// AGGREGATION OPERATORS
inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().sumBy(keySelector, longMapper)


inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().averageBy(keySelector, longMapper)

inline fun <T,K> Sequence<T>.minBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.min() }

inline fun <T,K> Iterable<T>.minBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().minBy(keySelector, longMapper)

inline fun <T,K> Sequence<T>.maxBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.max() }

inline fun <T,K> Iterable<T>.maxBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().maxBy(keySelector, longMapper)

inline fun <T,K> Sequence<T>.medianBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.median() }

inline fun <T,K> Iterable<T>.medianBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().medianBy(keySelector, longMapper)

inline fun <T,K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.variance() }

inline fun <T,K> Iterable<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().varianceBy(keySelector, longMapper)

inline fun <T,K> Sequence<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.standardDeviation() }

inline fun <T,K> Iterable<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().standardDeviationBy(keySelector, longMapper)

