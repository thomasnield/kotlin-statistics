package org.nield.kotlinstatistics

import org.apache.commons.math.stat.StatUtils
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicBoolean

fun Sequence<BigDecimal>.sum() = fold(BigDecimal.ZERO) { x,y -> x + y }!!
fun Iterable<BigDecimal>.sum() = fold(BigDecimal.ZERO) { x,y -> x + y }!!

fun Sequence<BigDecimal>.average() = toList().let { list ->
    list.sum() / BigDecimal.valueOf(list.count().toDouble())
}
fun Iterable<BigDecimal>.average() = asSequence().average()

val Iterable<BigDecimal>.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)
val Sequence<BigDecimal>.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)
val Array<out BigDecimal>.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)

fun Iterable<BigDecimal>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<BigDecimal>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out BigDecimal>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray() )

fun Iterable<BigDecimal>.median() = percentile(50.0)
fun Sequence<BigDecimal>.median() = percentile(50.0)
fun Array<out BigDecimal>.median() = percentile(50.0)

fun Iterable<BigDecimal>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList() .toDoubleArray(), percentile)
fun Sequence<BigDecimal>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList() .toDoubleArray(), percentile)
fun Array<out BigDecimal>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray() , percentile)

fun Iterable<BigDecimal>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList() .toDoubleArray())
fun Sequence<BigDecimal>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList() .toDoubleArray())
fun Array<out BigDecimal>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray() )

fun Iterable<BigDecimal>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList() .toDoubleArray())
fun Sequence<BigDecimal>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList() .toDoubleArray())
fun Array<out BigDecimal>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray() )

fun Iterable<BigDecimal>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Sequence<BigDecimal>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Array<out BigDecimal>.standardDeviation() = descriptiveStatistics.standardDeviation

fun Iterable<BigDecimal>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList() .toDoubleArray())
fun Sequence<BigDecimal>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList() .toDoubleArray())
fun Array<out BigDecimal>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray() )

val Iterable<BigDecimal>.kurtosis get() = descriptiveStatistics.kurtosis
val Sequence<BigDecimal>.kurtosis get() = descriptiveStatistics.kurtosis
val Array<out BigDecimal>.kurtosis get() = descriptiveStatistics.kurtosis

val Iterable<BigDecimal>.skewness get() = descriptiveStatistics.skewness
val Sequence<BigDecimal>.skewness get() = descriptiveStatistics.skewness
val Array<out BigDecimal>.skewness get() = descriptiveStatistics.skewness


// AGGREGATION OPERATORS

inline fun <T,K> Sequence<T>.descriptiveStatisticsBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.descriptiveStatistics }

inline fun <T,K> Iterable<T>.descriptiveStatisticsBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().descriptiveStatisticsBy(keySelector, bigDecimalMapper)

fun <K> Sequence<Pair<K,BigDecimal>>.descriptiveStatisticsBy() =
        groupApply({it.first}, {it.second}) { it.descriptiveStatistics }

fun <K> Iterable<Pair<K,BigDecimal>>.descriptiveStatisticsBy() =
        asSequence().descriptiveStatisticsBy()

inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().sumBy(keySelector, bigDecimalMapper)

fun <K> Sequence<Pair<K,BigDecimal>>.sumBy() =
        groupApply({it.first}, {it.second}) { it.sum() }

fun <K> Iterable<Pair<K,BigDecimal>>.sumBy() =
        asSequence().sumBy()

inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().averageBy(keySelector, bigDecimalMapper)

fun <K> Sequence<Pair<K,BigDecimal>>.averageBy() =
        groupApply({it.first}, {it.second}) { it.average() }

fun <K> Iterable<Pair<K,BigDecimal>>.averageBy() =
        asSequence().averageBy()

inline fun <T,K> Sequence<T>.minBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.min() }

inline fun <T,K> Iterable<T>.minBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().minBy(keySelector, bigDecimalMapper)

fun <K> Sequence<Pair<K,BigDecimal>>.minBy() =
        groupApply({it.first}, {it.second}) { it.min() }

fun <K> Iterable<Pair<K,BigDecimal>>.minBy() =
        asSequence().minBy()

inline fun <T,K> Sequence<T>.maxBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.max() }

inline fun <T,K> Iterable<T>.maxBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().maxBy(keySelector, bigDecimalMapper)

fun <K> Sequence<Pair<K,BigDecimal>>.maxBy() =
        groupApply({it.first}, {it.second}) { it.max() }

fun <K> Iterable<Pair<K,BigDecimal>>.maxBy() =
        asSequence().maxBy()

inline fun <T,K> Sequence<T>.medianBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.median() }

inline fun <T,K> Iterable<T>.medianBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().medianBy(keySelector, bigDecimalMapper)

fun <K> Sequence<Pair<K,BigDecimal>>.medianBy() =
        groupApply({it.first}, {it.second}) { it.median() }

fun <K> Iterable<Pair<K,BigDecimal>>.medianBy() =
        asSequence().medianBy()

inline fun <T,K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.variance() }

inline fun <T,K> Iterable<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().varianceBy(keySelector, bigDecimalMapper)

fun <K> Sequence<Pair<K,BigDecimal>>.varianceBy() =
        groupApply({it.first}, {it.second}) { it.variance() }

fun <K> Iterable<Pair<K,BigDecimal>>.varianceBy() =
        asSequence().varianceBy()

inline fun <T,K> Sequence<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.standardDeviation() }

inline fun <T,K> Iterable<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().standardDeviationBy(keySelector, bigDecimalMapper)

fun <K> Sequence<Pair<K,BigDecimal>>.standardDeviationBy() =
        groupApply({it.first}, {it.second}) { it.standardDeviation() }

fun <K> Iterable<Pair<K,BigDecimal>>.standardDeviationBy() =
        asSequence().standardDeviationBy()

inline fun <T,K> Sequence<T>.geometricMeanBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.geometricMean() }

inline fun <T,K> Iterable<T>.geometricMeanBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().geometricMeanBy(keySelector, bigDecimalMapper)


fun <K> Sequence<Pair<K,BigDecimal>>.geometricMeanBy() =
        groupApply({it.first}, {it.second}) { it.geometricMean() }

fun <K> Iterable<Pair<K,BigDecimal>>.geometricMeanBy() =
        asSequence().geometricMeanBy()

// bin operators


inline fun <T> Sequence<T>.binByBigDecimal(binSize: BigDecimal,
                                           gapSize: BigDecimal,
                                           crossinline valueMapper: (T) -> BigDecimal,
                                           rangeStart: BigDecimal? = null
) = toList().binByBigDecimal(binSize, gapSize, valueMapper, { it }, rangeStart)

inline fun <T, G> Sequence<T>.binByBigDecimal(binSize: BigDecimal,
                                              gapSize: BigDecimal,
                                              crossinline valueMapper: (T) -> BigDecimal,
                                              crossinline groupOp: (List<T>) -> G,
                                              rangeStart: BigDecimal? = null
) = toList().binByBigDecimal(binSize, gapSize, valueMapper, groupOp, rangeStart)

inline fun <T> Iterable<T>.binByBigDecimal(binSize: BigDecimal,
                                           gapSize: BigDecimal,
                                           crossinline valueMapper: (T) -> BigDecimal,
                                           rangeStart: BigDecimal? = null
) = toList().binByBigDecimal(binSize, gapSize, valueMapper, { it }, rangeStart)

inline fun <T, G> Iterable<T>.binByBigDecimal(binSize: BigDecimal,
                                              gapSize: BigDecimal,
                                              crossinline valueMapper: (T) -> BigDecimal,
                                              crossinline groupOp: (List<T>) -> G,
                                              rangeStart: BigDecimal? = null
) = toList().binByBigDecimal(binSize, gapSize, valueMapper, groupOp, rangeStart)

inline fun <T> List<T>.binByBigDecimal(binSize: BigDecimal,
                                       gapSize: BigDecimal,
                                       crossinline valueMapper: (T) -> BigDecimal,
                                       rangeStart: BigDecimal? = null
): BinModel<List<T>, BigDecimal> = binByBigDecimal(binSize, gapSize, valueMapper, { it }, rangeStart)

inline fun <T, G> List<T>.binByBigDecimal(binSize: BigDecimal,
                                          gapSize: BigDecimal,
                                          crossinline valueMapper: (T) -> BigDecimal,
                                          crossinline groupOp: (List<T>) -> G,
                                          rangeStart: BigDecimal? = null
): BinModel<G, BigDecimal> {

    val groupedByC = asSequence().groupBy(valueMapper)
    val minC = rangeStart?:groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val buckets = mutableListOf<ClosedRange<BigDecimal>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC
        val isFirst = AtomicBoolean(true)
        while  (currentRangeEnd < maxC) {
            currentRangeEnd = currentRangeStart + binSize - if (isFirst.getAndSet(false)) BigDecimal.ZERO else gapSize
            add(currentRangeStart..currentRangeEnd)
            currentRangeStart = currentRangeEnd + gapSize
        }
    }

    return buckets.asSequence()
            .map { it to mutableListOf<T>() }
            .map { bucketWithList ->
                groupedByC.entries.asSequence()
                        .filter { it.key in bucketWithList.first }
                        .forEach { bucketWithList.second.addAll(it.value) }
                bucketWithList
            }.map { Bin(it.first, groupOp(it.second)) }
            .toList()
            .let(::BinModel)
}