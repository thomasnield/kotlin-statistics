package org.nield.kotlinstatistics

import org.nield.kotlinstatistics.range.Range
import org.nield.kotlinstatistics.range.XClosedRange

val IntArray.descriptiveStatistics
    get(): Descriptives = asSequence().map { it.toDouble() }.descriptiveStatistics()

fun IntArray.geometricMean() = ArrayStat.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun IntArray.median() = percentile(50.0)
fun IntArray.percentile(percentile: Double) =
    ArrayStat.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)

fun IntArray.variance() = ArrayStat.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun IntArray.sumOfSquares() = ArrayStat.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun IntArray.normalize() = ArrayStat.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
val IntArray.kurtosis get() = descriptiveStatistics.kurtosis
val IntArray.skewness get() = descriptiveStatistics.skewness


// AGGREGATION OPERATORS


inline fun <T, K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
    groupApply(keySelector, intSelector) { it.sum() }

inline fun <T, K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
    asSequence().sumBy(keySelector, intSelector)

fun <K> Sequence<Pair<K, Int>>.sumBy() =
    groupApply({ it.first }, { it.second }) { it.sum() }

fun <K> Iterable<Pair<K, Int>>.sumBy() = asSequence().sumBy()


inline fun <T, K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
    groupApply(keySelector, intSelector) { it.average() }

inline fun <T, K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
    asSequence().averageBy(keySelector, intSelector)


fun <K> Sequence<Pair<K, Int>>.averageBy() =
    groupApply({ it.first }, { it.second }) { it.average() }

fun <K> Iterable<Pair<K, Int>>.averageBy() = asSequence().averageBy()


fun Sequence<Int>.intRange() = toList().intRange()
fun Iterable<Int>.intRange() = toList().let {
    (it.min() ?: throw Exception("At least one element must be present"))..(it.max()
        ?: throw Exception("At least one element must be present"))
}

inline fun <T, K> Sequence<T>.intRangeBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
    groupApply(keySelector, intSelector) { it.range() }

inline fun <T, K> Iterable<T>.intRangeBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
    asSequence().rangeBy(keySelector, intSelector)


// bin operators


inline fun <T> Sequence<T>.binByInt(
    binSize: Int,
    crossinline valueSelector: (T) -> Int,
    rangeStart: Int? = null
): BinModel<List<T>, Int> = toList().binByInt(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Sequence<T>.binByInt(
    binSize: Int,
    crossinline valueSelector: (T) -> Int,
    crossinline groupOp: (List<T>) -> G,
    rangeStart: Int? = null
) = toList().binByInt(binSize, valueSelector, groupOp, rangeStart)

inline fun <T> Iterable<T>.binByInt(
    binSize: Int,
    crossinline valueSelector: (T) -> Int,
    rangeStart: Int? = null
): BinModel<List<T>, Int> = toList().binByInt(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Iterable<T>.binByInt(
    binSize: Int,
    crossinline valueSelector: (T) -> Int,
    crossinline groupOp: (List<T>) -> G,
    rangeStart: Int? = null
) = toList().binByInt(binSize, valueSelector, groupOp, rangeStart)


inline fun <T> List<T>.binByInt(
    binSize: Int,
    crossinline valueSelector: (T) -> Int,
    rangeStart: Int? = null
): BinModel<List<T>, Int> = binByInt(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> List<T>.binByInt(
    binSize: Int,
    crossinline valueSelector: (T) -> Int,
    crossinline groupOp: (List<T>) -> G,
    rangeStart: Int? = null
): BinModel<G, Int> {

    val groupedByC = asSequence().groupBy(valueSelector)
    val minC = rangeStart ?: groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val bins = mutableListOf<Range<Int>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC
        while (currentRangeEnd < maxC) {
            currentRangeEnd = currentRangeStart + binSize - 1
            add(XClosedRange(currentRangeStart, currentRangeEnd))
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