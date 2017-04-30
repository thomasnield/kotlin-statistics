package org.nield.kotlinstatistics

import org.apache.commons.math.stat.StatUtils
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics

val Iterable<Short>.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }
val Sequence<Short>.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }
val Array<out Short>.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }
val ShortArray.descriptiveStatistics get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }

fun Iterable<Short>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )
fun Sequence<Short>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )
fun Array<out Short>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )
fun ShortArray.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )

fun Iterable<Short>.median() = percentile(50.0)
fun Sequence<Short>.median() = percentile(50.0)
fun Array<out Short>.median() = percentile(50.0)
fun ShortArray.median() = percentile(50.0)

fun Iterable<Short>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)
fun Sequence<Short>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)
fun Array<out Short>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray() , percentile)
fun ShortArray.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)

fun Iterable<Short>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Short>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Short>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun ShortArray.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())

fun Iterable<Short>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Short>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Short>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun ShortArray.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())

fun Iterable<Short>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Sequence<Short>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Array<out Short>.standardDeviation() = descriptiveStatistics.standardDeviation
fun ShortArray.standardDeviation() = descriptiveStatistics.standardDeviation

fun Iterable<Short>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Short>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Short>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun ShortArray.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())

val Iterable<Short>.kurtosis get() = descriptiveStatistics.kurtosis
val Sequence<Short>.kurtosis get() = descriptiveStatistics.kurtosis
val Array<out Short>.kurtosis get() = descriptiveStatistics.kurtosis
val ShortArray.kurtosis get() = descriptiveStatistics.kurtosis

val Iterable<Short>.skewness get() = descriptiveStatistics.skewness
val Sequence<Short>.skewness get() = descriptiveStatistics.skewness
val Array<out Short>.skewness get() = descriptiveStatistics.skewness
val ShortArray.skewness get() = descriptiveStatistics.skewness



// AGGREGATION OPERATORS

inline fun <T,K> Sequence<T>.descriptiveStatisticsBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.descriptiveStatistics }

inline fun <T,K> Iterable<T>.descriptiveStatisticsBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().descriptiveStatisticsBy(keySelector, shortMapper)


inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().sumBy(keySelector, shortMapper)


inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().averageBy(keySelector, shortMapper)

inline fun <T,K> Sequence<T>.minBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.min() }

inline fun <T,K> Iterable<T>.minBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().minBy(keySelector, shortMapper)

inline fun <T,K> Sequence<T>.maxBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.max() }

inline fun <T,K> Iterable<T>.maxBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().maxBy(keySelector, shortMapper)

inline fun <T,K> Sequence<T>.medianBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.median() }

inline fun <T,K> Iterable<T>.medianBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().medianBy(keySelector, shortMapper)

inline fun <T,K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.variance() }

inline fun <T,K> Iterable<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().varianceBy(keySelector, shortMapper)

inline fun <T,K> Sequence<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.standardDeviation() }

inline fun <T,K> Iterable<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().standardDeviationBy(keySelector, shortMapper)

inline fun <T,K> Sequence<T>.geometricMeanBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.geometricMean() }

inline fun <T,K> Iterable<T>.geometricMeanBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().geometricMeanBy(keySelector, shortMapper)
