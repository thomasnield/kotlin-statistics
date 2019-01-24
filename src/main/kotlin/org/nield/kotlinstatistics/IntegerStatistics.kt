package org.nield.kotlinstatistics

import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.nield.kotlinstatistics.range.Range
import org.nield.kotlinstatistics.range.XClosedRange

val IntArray.descriptiveStatistics get(): Descriptives = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)
fun IntArray.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )
fun IntArray.median() = percentile(50.0)
fun IntArray.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)
fun IntArray.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun IntArray.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun IntArray.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
val IntArray.kurtosis get() = descriptiveStatistics.kurtosis
val IntArray.skewness get() = descriptiveStatistics.skewness


// AGGREGATION OPERATORS


inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
        groupApply(keySelector, intSelector) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
        asSequence().sumBy(keySelector, intSelector)

fun <K> Sequence<Pair<K,Int>>.sumBy() =
        groupApply({it.first}, {it.second}) { it.sum() }

fun <K> Iterable<Pair<K,Int>>.sumBy() = asSequence().sumBy()






inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
        groupApply(keySelector, intSelector) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
        asSequence().averageBy(keySelector, intSelector)


fun <K> Sequence<Pair<K,Int>>.averageBy() =
        groupApply({it.first}, {it.second}) { it.average() }

fun <K> Iterable<Pair<K,Int>>.averageBy() = asSequence().averageBy()


fun Sequence<Int>.intRange() = toList().intRange()
fun Iterable<Int>.intRange() = toList().let { (it.min()?:throw Exception("At least one element must be present"))..(it.max()?:throw Exception("At least one element must be present")) }

inline fun <T,K> Sequence<T>.intRangeBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
        groupApply(keySelector, intSelector) { it.range() }

inline fun <T,K> Iterable<T>.intRangeBy(crossinline keySelector: (T) -> K, crossinline intSelector: (T) -> Int) =
        asSequence().rangeBy(keySelector, intSelector)




// bin operators


inline fun <T> Sequence<T>.binByInt(binSize: Int,
                                    crossinline valueSelector: (T) -> Int,
                                    rangeStart: Int? = null
): BinModel<List<T>, Int> = toList().binByInt(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Sequence<T>.binByInt(binSize: Int,
                                       crossinline valueSelector: (T) -> Int,
                                       crossinline groupOp: (List<T>) -> G,
                                       rangeStart: Int? = null
) = toList().binByInt(binSize, valueSelector, groupOp, rangeStart)

inline fun <T> Iterable<T>.binByInt(binSize: Int,
                                crossinline valueSelector: (T) -> Int,
                                rangeStart: Int? = null
): BinModel<List<T>, Int> = toList().binByInt(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Iterable<T>.binByInt(binSize: Int,
                                   crossinline valueSelector: (T) -> Int,
                                   crossinline groupOp: (List<T>) -> G,
                                   rangeStart: Int? = null
) = toList().binByInt(binSize, valueSelector, groupOp, rangeStart)


inline fun <T> List<T>.binByInt(binSize: Int,
                                 crossinline valueSelector: (T) -> Int,
                                 rangeStart: Int? = null
): BinModel<List<T>, Int> = binByInt(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> List<T>.binByInt(binSize: Int,
                                    crossinline valueSelector: (T) -> Int,
                                    crossinline groupOp: (List<T>) -> G,
                                    rangeStart: Int? = null
): BinModel<G, Int> {

    val groupedByC = asSequence().groupBy(valueSelector)
    val minC = rangeStart?:groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val bins = mutableListOf<Range<Int>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC
        while  (currentRangeEnd < maxC) {
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


fun <K> Map<K, List<Int>>.sum(): Map<K, Int> = entries.map { it.key to it.value.sum() }.toMap()
fun <K> Map<K, List<Int>>.average(): Map<K, Double> = entries.map { it.key to it.value.average() }.toMap()
fun <K> Map<K, List<Int>>.intRange(): Map<K, IntRange> = entries.map { it.key to it.value.intRange() }.toMap()
fun <K> Map<K, List<Int>>.geometricMean(): Map<K, Double> = entries.map { it.key to it.value.geometricMean() }.toMap()
fun <K> Map<K, List<Int>>.median(): Map<K, Double> = entries.map { it.key to it.value.median() }.toMap()
fun <K> Map<K, List<Int>>.percentile(percentile: Double): Map<K, Double> = entries.map { it.key to it.value.percentile(percentile) }.toMap()
fun <K> Map<K, List<Int>>.variance(): Map<K, Double> = entries.map { it.key to it.value.variance() }.toMap()
fun <K> Map<K, List<Int>>.sumOfSquares(): Map<K, Double> = entries.map { it.key to it.value.sumOfSquares() }.toMap()
fun <K> Map<K, List<Int>>.normalize(): Map<K, DoubleArray> = entries.map { it.key to it.value.normalize() }.toMap()
fun <K> Map<K, List<Int>>.descriptiveStatistics(): Map<K, Descriptives> = entries.map { it.key to it.value.descriptiveStatistics }.toMap()

fun <K, V> Map<K, List<V>>.sumByInt(selector: (V) -> Int): Map<K, Int> =
        entries.map { it.key to it.value.map(selector).sum() }.toMap()

fun <K, V> Map<K, List<V>>.averageByInt(selector: (V) -> Int): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).average() }.toMap()

fun <K, V> Map<K, List<V>>.intRangeBy(selector: (V) -> Int): Map<K, IntRange> =
        entries.map { it.key to it.value.map(selector).intRange() }.toMap()

fun <K, V> Map<K, List<V>>.geometricMeanByInt(selector: (V) -> Int): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).geometricMean() }.toMap()

fun <K, V> Map<K, List<V>>.medianByInt(selector: (V) -> Int): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).median() }.toMap()

fun <K, V> Map<K, List<V>>.percentileByInt(selector: (V) -> Int, percentile: Double): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).percentile(percentile) }.toMap()

fun <K, V> Map<K, List<V>>.varianceByInt(selector: (V) -> Int): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).variance() }.toMap()

fun <K, V> Map<K, List<V>>.sumOfSquaresByInt(selector: (V) -> Int): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).sumOfSquares() }.toMap()

fun <K, V> Map<K, List<V>>.normalizeByInt(selector: (V) -> Int): Map<K, DoubleArray> =
        entries.map { it.key to it.value.map(selector).normalize() }.toMap()

fun <K, V> Map<K, List<V>>.descriptiveStatisticsByInt(selector: (V) -> Int): Map<K, Descriptives> =
        entries.map { it.key to it.value.map(selector).descriptiveStatistics }.toMap()