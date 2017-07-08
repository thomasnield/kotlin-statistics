package org.nield.kotlinstatistics

import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

fun Int.abs() = let { if (it < 0) (it * -1) else it  }
val Iterable<Int>.descriptiveStatistics get(): Descriptives = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)
val Sequence<Int>.descriptiveStatistics get(): Descriptives = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)
val Array<out Int>.descriptiveStatistics get(): Descriptives = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)
val IntArray.descriptiveStatistics get(): Descriptives = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)

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

inline fun <T,K> Sequence<T>.descriptiveStatisticsBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        groupApply(keySelector, intMapper) { it.descriptiveStatistics }

inline fun <T,K> Iterable<T>.descriptiveStatisticsBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Int) =
        asSequence().descriptiveStatisticsBy(keySelector, intMapper)


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


// bin operators


inline fun <T> Sequence<T>.binByInt(binSize: Int,
                                    crossinline valueMapper: (T) -> Int,
                                    rangeStart: Int? = null
): BinModel<List<T>, Int> = toList().binByInt(binSize, valueMapper, { it }, rangeStart)

inline fun <T, G> Sequence<T>.binByInt(binSize: Int,
                                       crossinline valueMapper: (T) -> Int,
                                       crossinline groupOp: (List<T>) -> G,
                                       rangeStart: Int? = null
) = toList().binByInt(binSize, valueMapper, groupOp, rangeStart)

inline fun <T> Iterable<T>.binByInt(binSize: Int,
                                crossinline valueMapper: (T) -> Int,
                                rangeStart: Int? = null
): BinModel<List<T>, Int> = toList().binByInt(binSize, valueMapper, { it }, rangeStart)

inline fun <T, G> Iterable<T>.binByInt(binSize: Int,
                                   crossinline valueMapper: (T) -> Int,
                                   crossinline groupOp: (List<T>) -> G,
                                   rangeStart: Int? = null
) = toList().binByInt(binSize, valueMapper, groupOp, rangeStart)


inline fun <T> List<T>.binByInt(binSize: Int,
                                 crossinline valueMapper: (T) -> Int,
                                 rangeStart: Int? = null
): BinModel<List<T>, Int> = binByInt(binSize, valueMapper, { it }, rangeStart)

inline fun <T, G> List<T>.binByInt(binSize: Int,
                                    crossinline valueMapper: (T) -> Int,
                                    crossinline groupOp: (List<T>) -> G,
                                    rangeStart: Int? = null
): BinModel<G, Int> {

    val groupedByC = asSequence().groupBy(valueMapper)
    val minC = rangeStart?:groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val bins = mutableListOf<ClosedRange<Int>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC
        while  (currentRangeEnd < maxC) {
            currentRangeEnd = currentRangeStart + binSize - 1
            add(currentRangeStart..currentRangeEnd)
            currentRangeStart = currentRangeEnd + 1
        }
    }

    return bins.asSequence()
            .map { it to mutableListOf<T>() }
            .map { binWithList ->
                groupedByC.entries.asSequence()
                        .filter { it.key in binWithList.first }
                        .forEach { binWithList.second.addAll(it.value) }
                binWithList
            }.map { Bin(it.first, groupOp(it.second)) }
            .toList()
            .let(::BinModel)
}