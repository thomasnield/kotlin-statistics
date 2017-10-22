package org.nield.kotlinstatistics

import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicBoolean

val DoubleArray.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it) } }.let(::ApacheDescriptives)


fun DoubleArray.geometricMean() = StatUtils.geometricMean(this)
fun DoubleArray.median() = percentile(50.0)
fun DoubleArray.percentile(percentile: Double) = StatUtils.percentile(this, percentile)
fun DoubleArray.variance() = StatUtils.variance(this)
fun DoubleArray.sumOfSquares() = StatUtils.sumSq(this)
fun DoubleArray.standardDeviation() = descriptiveStatistics.standardDeviation
fun DoubleArray.normalize() = StatUtils.normalize(this)

val Iterable<Double>.kurtosis get() = descriptiveStatistics.kurtosis
val Sequence<Double>.kurtosis get() = descriptiveStatistics.kurtosis
val Array<out Double>.kurtosis get() = descriptiveStatistics.kurtosis
val DoubleArray.kurtosis get() = descriptiveStatistics.kurtosis

val Iterable<Double>.skewness get() = descriptiveStatistics.skewness
val Sequence<Double>.skewness get() = descriptiveStatistics.skewness
val Array<out Double>.skewness get() = descriptiveStatistics.skewness
val DoubleArray.skewness get() = descriptiveStatistics.skewness


// REGRESSION
typealias ASR = org.apache.commons.math3.stat.regression.SimpleRegression




// AGGREGATION OPERATORS




inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.sum() }

fun <K> Sequence<Pair<K,Double>>.sumBy() =
        groupApply({it.first}, {it.second}) { it.sum() }


inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().sumBy(keySelector, doubleMapper)

fun <K> Iterable<Pair<K,Double>>.sumBy() = asSequence().sumBy()






inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().groupApply(keySelector, doubleMapper) { it.average() }

fun <K> Sequence<Pair<K,Double>>.averageBy() =
        groupApply({it.first}, {it.second}) { it.average() }

fun <K> Iterable<Pair<K,Double>>.averageBy() = asSequence().averageBy()






inline fun <T,K> Sequence<T>.minBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.min() }

fun <K> Sequence<Pair<K,Double>>.minBy() =
        groupApply({it.first}, {it.second}) { it.min() }

inline fun <T,K> Iterable<T>.minBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().minBy(keySelector, doubleMapper)

fun <K> Iterable<Pair<K,Double>>.minBy() = asSequence().minBy()






inline fun <T,K> Sequence<T>.maxBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.max() }

fun <K> Sequence<Pair<K,Double>>.maxBy() =
        groupApply({it.first}, {it.second}) { it.max() }

inline fun <T,K> Iterable<T>.maxBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().maxBy(keySelector, doubleMapper)

fun <K> Iterable<Pair<K,Double>>.maxBy() = asSequence().maxBy()








inline fun <T,K> Sequence<T>.geometricMeanBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.geometricMean() }

fun <K> Sequence<Pair<K,Double>>.geometricMeanBy() =
        groupApply({it.first}, {it.second}) { it.geometricMean() }

inline fun <T,K> Iterable<T>.geometricMeanBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().geometricMeanBy(keySelector, doubleMapper)

fun <K> Iterable<Pair<K,Double>>.geometricMeanBy() = asSequence().geometricMeanBy()







inline fun <T,K> Sequence<T>.simpleRegressionBy(crossinline keySelector: (T) -> K, crossinline xSelector: (T) -> Double, crossinline ySelector: (T) -> Double) =
        groupApply(keySelector, { it.simpleRegression(xSelector, ySelector) })

inline fun <T,K> Iterable<T>.simpleRegressionBy(crossinline keySelector: (T) -> K, crossinline xSelector: (T) -> Double, crossinline ySelector: (T) -> Double) =
        asSequence().groupApply(keySelector, { it.simpleRegression(xSelector, ySelector) })



// Bin Operators

inline fun <T> Iterable<T>.binByDouble(binSize: Double,
                                   gapSize: Double,
                                   crossinline valueMapper: (T) -> Double,
                                   rangeStart: Double? = null
): BinModel<List<T>, Double> = toList().binByDouble(binSize, gapSize, valueMapper, { it }, rangeStart)

inline fun <T, G> Iterable<T>.binByDouble(binSize: Double,
                                          gapSize: Double,
                                          crossinline valueMapper: (T) -> Double,
                                          crossinline groupOp: (List<T>) -> G,
                                          rangeStart: Double? = null
) = toList().binByDouble(binSize, gapSize, valueMapper, groupOp, rangeStart)


inline fun <T> Sequence<T>.binByDouble(binSize: Double,
                                       gapSize: Double,
                                       crossinline valueMapper: (T) -> Double,
                                       rangeStart: Double? = null
): BinModel<List<T>, Double> = toList().binByDouble(binSize, gapSize, valueMapper, { it }, rangeStart)

inline fun <T, G> Sequence<T>.binByDouble(binSize: Double,
                                          gapSize: Double,
                                          crossinline valueMapper: (T) -> Double,
                                          crossinline groupOp: (List<T>) -> G,
                                          rangeStart: Double? = null
) = toList().binByDouble(binSize, gapSize, valueMapper, groupOp, rangeStart)


inline fun <T> List<T>.binByDouble(binSize: Double,
                                   gapSize: Double,
                                   crossinline valueMapper: (T) -> Double,
                                   rangeStart: Double? = null
): BinModel<List<T>, Double> = binByDouble(binSize, gapSize, valueMapper, { it }, rangeStart)

inline fun <T, G> List<T>.binByDouble(binSize: Double,
                                      gapSize: Double,
                                      crossinline valueMapper: (T) -> Double,
                                      crossinline groupOp: (List<T>) -> G,
                                      rangeStart: Double? = null
): BinModel<G, Double> {

    val groupedByC = asSequence().groupBy { BigDecimal.valueOf(valueMapper(it)) }
    val minC = rangeStart?.let(BigDecimal::valueOf)?:groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val bins = mutableListOf<ClosedRange<Double>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC
        val isFirst = AtomicBoolean(true)
        val binSizeBigDecimal = BigDecimal.valueOf(binSize)
        val gapSizeBigDecimal = BigDecimal.valueOf(gapSize)
        while  (currentRangeEnd < maxC) {
            currentRangeEnd = currentRangeStart + binSizeBigDecimal - if (isFirst.getAndSet(false)) BigDecimal.ZERO else gapSizeBigDecimal
            add(currentRangeStart.toDouble()..currentRangeEnd.toDouble())
            currentRangeStart = currentRangeEnd + gapSizeBigDecimal
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