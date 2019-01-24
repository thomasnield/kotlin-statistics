package org.nield.kotlinstatistics

import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.nield.kotlinstatistics.range.Range
import org.nield.kotlinstatistics.range.until
import java.math.BigDecimal

val DoubleArray.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it) } }.let(::ApacheDescriptives)


fun DoubleArray.geometricMean() = StatUtils.geometricMean(this)
fun DoubleArray.median() = percentile(50.0)
fun DoubleArray.percentile(percentile: Double) = StatUtils.percentile(this, percentile)
fun DoubleArray.variance() = StatUtils.variance(this)
fun DoubleArray.sumOfSquares() = StatUtils.sumSq(this)
fun DoubleArray.standardDeviation() = descriptiveStatistics.standardDeviation
fun DoubleArray.normalize() = StatUtils.normalize(this)
val DoubleArray.kurtosis get() = descriptiveStatistics.kurtosis
val DoubleArray.skewness get() = descriptiveStatistics.skewness



// AGGREGATION OPERATORS

inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline doubleSelector: (T) -> Double) =
        groupApply(keySelector, doubleSelector) { it.sum() }

fun <K> Sequence<Pair<K,Double>>.sumBy() =
        groupApply({it.first}, {it.second}) { it.sum() }


inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline doubleSelector: (T) -> Double) =
        asSequence().sumBy(keySelector, doubleSelector)

fun <K> Iterable<Pair<K,Double>>.sumBy() = asSequence().sumBy()



inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline doubleSelector: (T) -> Double) =
        groupApply(keySelector, doubleSelector) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline doubleSelector: (T) -> Double) =
        asSequence().groupApply(keySelector, doubleSelector) { it.average() }

fun <K> Sequence<Pair<K,Double>>.averageBy() =
        groupApply({it.first}, {it.second}) { it.average() }

fun <K> Iterable<Pair<K,Double>>.averageBy() = asSequence().averageBy()




fun Sequence<Double>.doubleRange() = toList().doubleRange()
fun Iterable<Double>.doubleRange() = toList().let { (it.min()?:throw Exception("At least one element must be present"))..(it.max()?:throw Exception("At least one element must be present")) }

inline fun <T,K> Sequence<T>.doubleRangeBy(crossinline keySelector: (T) -> K, crossinline doubleSelector: (T) -> Double) =
        groupApply(keySelector, doubleSelector) { it.range() }

inline fun <T,K> Iterable<T>.doubleRangeBy(crossinline keySelector: (T) -> K, crossinline doubleSelector: (T) -> Double) =
        asSequence().rangeBy(keySelector, doubleSelector)




// Bin Operators

inline fun <T> Iterable<T>.binByDouble(binSize: Double,
                                   crossinline valueSelector: (T) -> Double,
                                   rangeStart: Double? = null
): BinModel<List<T>, Double> = toList().binByDouble(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Iterable<T>.binByDouble(binSize: Double,
                                          crossinline valueSelector: (T) -> Double,
                                          crossinline groupOp: (List<T>) -> G,
                                          rangeStart: Double? = null
) = toList().binByDouble(binSize, valueSelector, groupOp, rangeStart)


inline fun <T> Sequence<T>.binByDouble(binSize: Double,
                                       crossinline valueSelector: (T) -> Double,
                                       rangeStart: Double? = null
): BinModel<List<T>, Double> = toList().binByDouble(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Sequence<T>.binByDouble(binSize: Double,
                                          crossinline valueSelector: (T) -> Double,
                                          crossinline groupOp: (List<T>) -> G,
                                          rangeStart: Double? = null
) = toList().binByDouble(binSize, valueSelector, groupOp, rangeStart)


inline fun <T> List<T>.binByDouble(binSize: Double,
                                   crossinline valueSelector: (T) -> Double,
                                   rangeStart: Double? = null
): BinModel<List<T>, Double> = binByDouble(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> List<T>.binByDouble(binSize: Double,
                                      crossinline valueSelector: (T) -> Double,
                                      crossinline groupOp: (List<T>) -> G,
                                      rangeStart: Double? = null
): BinModel<G, Double> {

    val groupedByC = asSequence().groupBy { BigDecimal.valueOf(valueSelector(it)) }
    val minC = rangeStart?.let(BigDecimal::valueOf)?:groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val bins = mutableListOf<Range<Double>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC
        val binSizeBigDecimal = BigDecimal.valueOf(binSize)

        while  (currentRangeEnd < maxC) {
            currentRangeEnd = currentRangeStart + binSizeBigDecimal
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


fun <K> Map<K, List<Double>>.sum(): Map<K, Double> = entries.map { it.key to it.value.sum() }.toMap()
fun <K> Map<K, List<Double>>.average(): Map<K, Double> = entries.map { it.key to it.value.average() }.toMap()
fun <K> Map<K, List<Double>>.intRange(): Map<K, ClosedFloatingPointRange<Double>> = entries.map { it.key to it.value.doubleRange() }.toMap()
fun <K> Map<K, List<Double>>.geometricMean(): Map<K, Double> = entries.map { it.key to it.value.geometricMean() }.toMap()
fun <K> Map<K, List<Double>>.median(): Map<K, Double> = entries.map { it.key to it.value.median() }.toMap()
fun <K> Map<K, List<Double>>.percentile(percentile: Double): Map<K, Double> = entries.map { it.key to it.value.percentile(percentile) }.toMap()
fun <K> Map<K, List<Double>>.variance(): Map<K, Double> = entries.map { it.key to it.value.variance() }.toMap()
fun <K> Map<K, List<Double>>.sumOfSquares(): Map<K, Double> = entries.map { it.key to it.value.sumOfSquares() }.toMap()
fun <K> Map<K, List<Double>>.normalize(): Map<K, DoubleArray> = entries.map { it.key to it.value.normalize() }.toMap()
fun <K> Map<K, List<Double>>.descriptiveStatistics(): Map<K, Descriptives> = entries.map { it.key to it.value.descriptiveStatistics }.toMap()

fun <K, V> Map<K, List<V>>.sumByDouble(selector: (V) -> Double): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).sum() }.toMap()

fun <K, V> Map<K, List<V>>.averageByDouble(selector: (V) -> Double): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).average() }.toMap()

fun <K, V> Map<K, List<V>>.doubleRangeBy(selector: (V) -> Double): Map<K, ClosedFloatingPointRange<Double>> =
        entries.map { it.key to it.value.map(selector).doubleRange() }.toMap()

fun <K, V> Map<K, List<V>>.geometricMeanByDouble(selector: (V) -> Double): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).geometricMean() }.toMap()

fun <K, V> Map<K, List<V>>.medianByDouble(selector: (V) -> Double): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).median() }.toMap()

fun <K, V> Map<K, List<V>>.percentileByDouble(selector: (V) -> Double, percentile: Double): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).percentile(percentile) }.toMap()

fun <K, V> Map<K, List<V>>.varianceByDouble(selector: (V) -> Double): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).variance() }.toMap()

fun <K, V> Map<K, List<V>>.sumOfSquaresByDouble(selector: (V) -> Double): Map<K, Double> =
        entries.map { it.key to it.value.map(selector).sumOfSquares() }.toMap()

fun <K, V> Map<K, List<V>>.normalizeByDouble(selector: (V) -> Double): Map<K, DoubleArray> =
        entries.map { it.key to it.value.map(selector).normalize() }.toMap()

fun <K, V> Map<K, List<V>>.descriptiveStatisticsByDouble(selector: (V) -> Double): Map<K, Descriptives> =
        entries.map { it.key to it.value.map(selector).descriptiveStatistics }.toMap()