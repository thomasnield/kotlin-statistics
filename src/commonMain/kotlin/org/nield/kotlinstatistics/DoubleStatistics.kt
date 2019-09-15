package org.nield.kotlinstatistics

import org.nield.kotlinstatistics.range.Range
import org.nield.kotlinstatistics.range.until
import kotlin.math.exp
import kotlin.math.ln

val DoubleArray.descriptiveStatistics: DescriptiveStatistics
    get() = asSequence().stats()


fun DoubleArray.geometricMean() = exp(sumByDouble { ln(it) } / size)
fun DoubleArray.median() = percentile(50.0)
fun DoubleArray.percentile(percentile: Double) = stats().percentile(percentile)
fun DoubleArray.variance() = stats().variance
fun DoubleArray.sumOfSquares() = sumByDouble { it * it }
fun DoubleArray.standardDeviation() = descriptiveStatistics.standardDeviation
fun DoubleArray.normalize(): DoubleArray {
    val stats = stats()
    return DoubleArray(size) { i -> (get(i) - stats.mean) / stats.standardDeviation }
}

val DoubleArray.kurtosis get() = descriptiveStatistics.kurtosis
val DoubleArray.skewness get() = descriptiveStatistics.skewness


// AGGREGATION OPERATORS

inline fun <T, K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline doubleSelector: (T) -> Double) =
    groupApply(keySelector, doubleSelector) { it.sum() }

fun <K> Sequence<Pair<K, Double>>.sumBy() =
    groupApply({ it.first }, { it.second }) { it.sum() }


inline fun <T, K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline doubleSelector: (T) -> Double) =
    asSequence().sumBy(keySelector, doubleSelector)

fun <K> Iterable<Pair<K, Double>>.sumBy() = asSequence().sumBy()


inline fun <T, K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline doubleSelector: (T) -> Double) =
    groupApply(keySelector, doubleSelector) { it.average() }

inline fun <T, K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline doubleSelector: (T) -> Double) =
    asSequence().groupApply(keySelector, doubleSelector) { it.average() }

fun <K> Sequence<Pair<K, Double>>.averageBy() =
    groupApply({ it.first }, { it.second }) { it.average() }

fun <K> Iterable<Pair<K, Double>>.averageBy() = asSequence().averageBy()


fun Sequence<Double>.doubleRange() = toList().doubleRange()
fun Iterable<Double>.doubleRange() = toList().let {
    (it.min() ?: throw Exception("At least one element must be present"))..(it.max()
        ?: throw Exception("At least one element must be present"))
}

inline fun <T, K> Sequence<T>.doubleRangeBy(
    crossinline keySelector: (T) -> K,
    crossinline doubleSelector: (T) -> Double
) =
    groupApply(keySelector, doubleSelector) { it.range() }

inline fun <T, K> Iterable<T>.doubleRangeBy(
    crossinline keySelector: (T) -> K,
    crossinline doubleSelector: (T) -> Double
) =
    asSequence().rangeBy(keySelector, doubleSelector)


// Bin Operators

inline fun <T> Iterable<T>.binByDouble(
    binSize: Double,
    crossinline valueSelector: (T) -> Double,
    rangeStart: Double? = null
): BinModel<List<T>, Double> = toList().binByDouble(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Iterable<T>.binByDouble(
    binSize: Double,
    crossinline valueSelector: (T) -> Double,
    crossinline groupOp: (List<T>) -> G,
    rangeStart: Double? = null
) = toList().binByDouble(binSize, valueSelector, groupOp, rangeStart)


inline fun <T> Sequence<T>.binByDouble(
    binSize: Double,
    crossinline valueSelector: (T) -> Double,
    rangeStart: Double? = null
): BinModel<List<T>, Double> = toList().binByDouble(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Sequence<T>.binByDouble(
    binSize: Double,
    crossinline valueSelector: (T) -> Double,
    crossinline groupOp: (List<T>) -> G,
    rangeStart: Double? = null
) = toList().binByDouble(binSize, valueSelector, groupOp, rangeStart)


inline fun <T> List<T>.binByDouble(
    binSize: Double,
    crossinline valueSelector: (T) -> Double,
    rangeStart: Double? = null
): BinModel<List<T>, Double> = binByDouble(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> List<T>.binByDouble(
    binSize: Double,
    crossinline valueSelector: (T) -> Double,
    crossinline groupOp: (List<T>) -> G,
    rangeStart: Double? = null
): BinModel<G, Double> {

    //FIXME replaced BigDecimal by Double

    val groupedByC = asSequence().groupBy { valueSelector(it) }
    val minC = rangeStart ?: groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val bins = mutableListOf<Range<Double>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC
        //val binSizeBigDecimal = BigDecimal.valueOf(binSize)

        while (currentRangeEnd < maxC) {
            currentRangeEnd = currentRangeStart + binSize
            add(currentRangeStart.toDouble() until currentRangeEnd.toDouble())
            currentRangeStart = currentRangeEnd
        }
    }

    return bins.asSequence()
        .map { it to mutableListOf<T>() }
        .map { binWithList ->
            groupedByC.entries.asSequence()
                .filter { it.key.toDouble() in binWithList.first }
                .forEach { binWithList.second.addAll(it.value) }
            binWithList
        }.map { Bin(it.first, groupOp(it.second)) }
        .toList()
        .let(::BinModel)
}