package org.nield.kotlinstatistics

import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicBoolean

val DoubleArray.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it) } }.let(::ApacheDescriptives)


fun Iterable<Double>.geometricMean() = StatUtils.geometricMean(toList().toDoubleArray())
fun Sequence<Double>.geometricMean() = StatUtils.geometricMean(toList().toDoubleArray())
fun Array<out Double>.geometricMean() = StatUtils.geometricMean(toDoubleArray())
fun DoubleArray.geometricMean() = StatUtils.geometricMean(this)

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

fun Iterable<Double>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Sequence<Double>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Array<out Double>.standardDeviation() = descriptiveStatistics.standardDeviation
fun DoubleArray.standardDeviation() = descriptiveStatistics.standardDeviation

fun Iterable<Double>.normalize() = StatUtils.normalize(toList().toDoubleArray())
fun Sequence<Double>.normalize() = StatUtils.normalize(toList().toDoubleArray())
fun Array<out Double>.normalize() = StatUtils.normalize(toDoubleArray())
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







inline fun <T,K> Sequence<T>.medianBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.median() }

fun <K> Sequence<Pair<K,Double>>.medianBy() =
        groupApply({it.first}, {it.second}) { it.median() }

inline fun <T,K> Iterable<T>.medianBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().medianBy(keySelector, doubleMapper)

fun <K> Iterable<Pair<K,Double>>.medianBy() = asSequence().medianBy()







inline fun <T,K> Sequence<T>.percentileBy(percentile: Double, crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.percentile(percentile) }

fun <K> Sequence<Pair<K,Double>>.percentileBy(percentile: Double) =
        groupApply({it.first}, {it.second}) { it.percentile(percentile) }

inline fun <T,K> Iterable<T>.percentileBy(percentile: Double, crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().percentileBy(percentile, keySelector, doubleMapper)

fun <K> Iterable<Pair<K,Double>>.percentileBy(percentile: Double) = asSequence().percentileBy(percentile)








inline fun <T,K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.variance() }

fun <K> Sequence<Pair<K,Double>>.varianceBy() =
        groupApply({it.first}, {it.second}) { it.variance() }

inline fun <T,K> Iterable<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().varianceBy(keySelector, doubleMapper)

fun <K> Iterable<Pair<K,Double>>.varianceBy() = asSequence().varianceBy()







inline fun <T,K> Sequence<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.standardDeviation() }

fun <K> Sequence<Pair<K,Double>>.standardDeviationBy() =
        groupApply({it.first}, {it.second}) { it.standardDeviation() }


inline fun <T,K> Iterable<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().standardDeviationBy(keySelector, doubleMapper)

fun <K> Iterable<Pair<K,Double>>.standardDeviationBy() =  asSequence().standardDeviationBy()







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