package org.nield.kotlinstatistics

import org.apache.commons.math.stat.StatUtils
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics


fun Iterable<Double>.median() = percentile(50.0)
fun Sequence<Double>.median() = percentile(50.0)
fun Array<out Double>.median() = percentile(50.0)
fun DoubleArray.median() = percentile(50.0)

fun Iterable<Double>.percentile(percentile: Double) = StatUtils.percentile(toList().toDoubleArray(), percentile)
fun Sequence<Double>.percentile(percentile: Double) = StatUtils.percentile(toList().toDoubleArray(), percentile)
fun Array<out Double>.percentile(percentile: Double) = StatUtils.percentile(toDoubleArray(), percentile)
fun DoubleArray.percentile(percentile: Double) = StatUtils.percentile(this, percentile)

fun Iterable<Double>.variance() = StatUtils.variance(toList().toDoubleArray())
fun Sequence<Double>.variance() = StatUtils.variance(toList().toDoubleArray())
fun Array<out Double>.variance() = StatUtils.variance(toDoubleArray())
fun DoubleArray.variance() = StatUtils.variance(this)

fun Iterable<Double>.sumOfSquares() = StatUtils.sumSq(toList().toDoubleArray())
fun Sequence<Double>.sumOfSquares() = StatUtils.sumSq(toList().toDoubleArray())
fun Array<out Double>.sumOfSquares() = StatUtils.sumSq(toDoubleArray())
fun DoubleArray.sumOfSquares() = StatUtils.sumSq(this)

fun Iterable<Double>.standardDeviation() = DescriptiveStatistics().apply { forEach { addValue(it) }}.standardDeviation
fun Sequence<Double>.standardDeviation() = DescriptiveStatistics().apply { forEach { addValue(it) }}.standardDeviation
fun Array<out Double>.standardDeviation() = DescriptiveStatistics().apply { forEach { addValue(it) }}.standardDeviation
fun DoubleArray.standardDeviation() = DescriptiveStatistics().apply { forEach { addValue(it) }}.standardDeviation


fun Iterable<Double>.normalize() = DescriptiveStatistics().apply { forEach { addValue(it) }}
fun Sequence<Double>.normalize() = DescriptiveStatistics().apply { forEach { addValue(it) }}.standardDeviation
fun Array<out Double>.normalize() = DescriptiveStatistics().apply { forEach { addValue(it) }}.standardDeviation
fun DoubleArray.normalize() = DescriptiveStatistics().apply { forEach { addValue(it) }}.standardDeviation

// AGGREGATION OPERATORS

inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().sumBy(keySelector, doubleMapper)

inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().averageBy(keySelector, doubleMapper)

inline fun <T,K> Sequence<T>.minBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.min() }

inline fun <T,K> Iterable<T>.minBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().minBy(keySelector, doubleMapper)

inline fun <T,K> Sequence<T>.maxBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.max() }

inline fun <T,K> Iterable<T>.maxBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().maxBy(keySelector, doubleMapper)

inline fun <T,K> Sequence<T>.medianBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.median() }

inline fun <T,K> Iterable<T>.medianBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().medianBy(keySelector, doubleMapper)

inline fun <T,K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.variance() }

inline fun <T,K> Iterable<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().varianceBy(keySelector, doubleMapper)

inline fun <T,K> Sequence<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.standardDeviation() }

inline fun <T,K> Iterable<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().standardDeviationBy(keySelector, doubleMapper)