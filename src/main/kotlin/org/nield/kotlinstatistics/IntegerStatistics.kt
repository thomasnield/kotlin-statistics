package org.nield.kotlinstatistics

import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

val IntArray.descriptiveStatistics get(): Descriptives = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)
fun IntArray.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )
fun IntArray.median() = percentile(50.0)
fun IntArray.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)
fun IntArray.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun IntArray.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())

fun Iterable<Int>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Sequence<Int>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Array<out Int>.standardDeviation() = descriptiveStatistics.standardDeviation
fun IntArray.standardDeviation() = descriptiveStatistics.standardDeviation
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