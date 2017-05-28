package org.nield.kotlinstatistics

import org.apache.commons.math.stat.StatUtils
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicBoolean

val Iterable<Double>.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it) } }.let(::ApacheDescriptives)
val Sequence<Double>.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it) } }.let(::ApacheDescriptives)
val Array<out Double>.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it) } }.let(::ApacheDescriptives)
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
typealias ASR = org.apache.commons.math.stat.regression.SimpleRegression

fun Sequence<Pair<Double, Double>>.simpleRegression() = simpleRegression({it.first},{it.second})
fun Iterable<Pair<Double, Double>>.simpleRegression() = simpleRegression({it.first},{it.second})
inline fun <T> Iterable<T>.simpleRegression(crossinline xSelector: (T) -> Double, crossinline ySelector: (T) -> Double) = asSequence().simpleRegression(xSelector, ySelector)
inline fun <T> Sequence<T>.simpleRegression(crossinline xSelector: (T) -> Double, crossinline ySelector: (T) -> Double): SimpleRegression {
    val r = ASR()
    forEach { r.addData(xSelector(it), ySelector(it)) }
    return ApacheSimpleRegression(r)
}


// AGGREGATION OPERATORS

inline fun <T,K> Sequence<T>.descriptiveStatisticsBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.descriptiveStatistics }

inline fun <T,K> Iterable<T>.descriptiveStatisticsBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().descriptiveStatisticsBy(keySelector, doubleMapper)

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

inline fun <T,K> Sequence<T>.percentileBy(percentile: Double, crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.percentile(percentile) }

inline fun <T,K> Iterable<T>.percentileBy(percentile: Double, crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().percentileBy(percentile, keySelector, doubleMapper)

inline fun <T,K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.variance() }

inline fun <T,K> Iterable<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().varianceBy(keySelector, doubleMapper)

inline fun <T,K> Sequence<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.standardDeviation() }

inline fun <T,K> Iterable<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().standardDeviationBy(keySelector, doubleMapper)

inline fun <T,K> Sequence<T>.geometricMeanBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        groupApply(keySelector, doubleMapper) { it.geometricMean() }

inline fun <T,K> Iterable<T>.geometricMeanBy(crossinline keySelector: (T) -> K, crossinline doubleMapper: (T) -> Double) =
        asSequence().geometricMeanBy(keySelector, doubleMapper)

inline fun <T,K> Sequence<T>.simpleRegressionBy(crossinline keySelector: (T) -> K, crossinline xSelector: (T) -> Double, crossinline ySelector: (T) -> Double) =
        groupApply(keySelector, { it.simpleRegression(xSelector, ySelector) })

// Bin Operators

inline fun <T> Iterable<T>.binByDouble(binSize: Double,
                                   gapSize: Double,
                                   crossinline binMapper: (T) -> Double,
                                   rangeStart: Double? = null
): BinModel<List<T>, Double> = toList().binByDouble(binSize, gapSize, binMapper, { it }, rangeStart)

inline fun <T, G> Iterable<T>.binByDouble(binSize: Double,
                                          gapSize: Double,
                                          crossinline binMapper: (T) -> Double,
                                          crossinline groupOp: (List<T>) -> G,
                                          rangeStart: Double? = null
) = toList().binByDouble(binSize, gapSize, binMapper, groupOp, rangeStart)


inline fun <T> Sequence<T>.binByDouble(binSize: Double,
                                       gapSize: Double,
                                       crossinline binMapper: (T) -> Double,
                                       rangeStart: Double? = null
): BinModel<List<T>, Double> = toList().binByDouble(binSize, gapSize, binMapper, { it }, rangeStart)

inline fun <T, G> Sequence<T>.binByDouble(binSize: Double,
                                          gapSize: Double,
                                          crossinline binMapper: (T) -> Double,
                                          crossinline groupOp: (List<T>) -> G,
                                          rangeStart: Double? = null
) = toList().binByDouble(binSize, gapSize, binMapper, groupOp, rangeStart)


inline fun <T> List<T>.binByDouble(binSize: Double,
                                   gapSize: Double,
                                   crossinline binMapper: (T) -> Double,
                                   rangeStart: Double? = null
): BinModel<List<T>, Double> = binByDouble(binSize, gapSize, binMapper, { it }, rangeStart)

inline fun <T, G> List<T>.binByDouble(binSize: Double,
                                      gapSize: Double,
                                      crossinline binMapper: (T) -> Double,
                                      crossinline groupOp: (List<T>) -> G,
                                      rangeStart: Double? = null
): BinModel<G, Double> {

    val groupedByC = asSequence().groupBy { BigDecimal.valueOf(binMapper(it)) }
    val minC = rangeStart?.let(BigDecimal::valueOf)?:groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val buckets = mutableListOf<ClosedRange<Double>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC
        val isFirst = AtomicBoolean(true)
        val bucketSizeBigDecimal = BigDecimal.valueOf(binSize)
        val gapSizeBigDecimal = BigDecimal.valueOf(gapSize)
        while  (currentRangeEnd < maxC) {
            currentRangeEnd = currentRangeStart + bucketSizeBigDecimal - if (isFirst.getAndSet(false)) BigDecimal.ZERO else gapSizeBigDecimal
            add(currentRangeStart.toDouble()..currentRangeEnd.toDouble())
            currentRangeStart = currentRangeEnd + gapSizeBigDecimal
        }
    }

    return buckets.asSequence()
            .map { it to mutableListOf<T>() }
            .map { bucketWithList ->
                groupedByC.entries.asSequence()
                        .filter { it.key.toDouble() in bucketWithList.first }
                        .forEach { bucketWithList.second.addAll(it.value) }
                bucketWithList
            }.map { Bin(it.first, groupOp(it.second)) }
            .toList()
            .let(::BinModel)
}