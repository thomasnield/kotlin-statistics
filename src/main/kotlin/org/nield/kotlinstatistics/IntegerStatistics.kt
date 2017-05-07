package org.nield.kotlinstatistics

import org.apache.commons.math.stat.StatUtils
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics

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

inline fun <T,R> Iterable<T>.intBinBy(bucketSize: Int,
                                   crossinline intMapper: (T) -> Int,
                                   crossinline groupOp: (List<Int>) -> R,
                                   rangeStart: Int? = null): BinModel<R, Int> {
    val ints = map(intMapper)
    val min = rangeStart?:ints.min()

    return ints.binBy(bucketSize = bucketSize,
            rangeStart = min,
            incrementer = { it + 1 },
            mapper = { it },
            groupOp = groupOp
    )
}


inline fun <T> Sequence<T>.descriptiveStatisticsBinBy(bucketSize: Int,
                                                      crossinline intMapper: (T) -> Int,
                                                      rangeStart: Int? = null) = toList().descriptiveStatisticsBinBy(bucketSize,intMapper, rangeStart)

inline fun <T> Iterable<T>.descriptiveStatisticsBinBy(bucketSize: Int,
                                                      crossinline intMapper: (T) -> Int,
                                                      rangeStart: Int? = null) = intBinBy(
        bucketSize = bucketSize,
        intMapper = intMapper,
        rangeStart = rangeStart,
        groupOp = { it.descriptiveStatistics }
)


inline fun <T> Sequence<T>.sumBinBy(bucketSize: Int,
                                    
                                    crossinline intMapper: (T) -> Int,
                                    rangeStart: Int? = null) = toList().sumBinBy(bucketSize,intMapper, rangeStart)

inline fun <T> Iterable<T>.sumBinBy(bucketSize: Int,
                                    crossinline intMapper: (T) -> Int,
                                    rangeStart: Int? = null) = intBinBy(
        bucketSize = bucketSize,
        intMapper = intMapper,
        rangeStart = rangeStart,
        groupOp = { it.sum() }
)

inline fun <T> Sequence<T>.averageBinBy(bucketSize: Int,
                                        crossinline intMapper: (T) -> Int,
                                        rangeStart: Int? = null) = toList().averageBinBy(bucketSize,intMapper, rangeStart)

inline fun <T> Iterable<T>.averageBinBy(bucketSize: Int,
                                        
                                        crossinline intMapper: (T) -> Int,
                                        rangeStart: Int? = null) = intBinBy(
        bucketSize = bucketSize,
        intMapper = intMapper,
        rangeStart = rangeStart,
        groupOp = { it.average() }
)



inline fun <T> Sequence<T>.minBinBy(bucketSize: Int,
                                    crossinline intMapper: (T) -> Int,
                                    rangeStart: Int? = null) = toList().minBinBy(bucketSize,intMapper, rangeStart)

inline fun <T> Iterable<T>.minBinBy(bucketSize: Int,
                                    crossinline intMapper: (T) -> Int,
                                    rangeStart: Int? = null) = intBinBy(
        bucketSize = bucketSize,
        intMapper = intMapper,
        rangeStart = rangeStart,
        groupOp = { it.min() }
)


inline fun <T> Sequence<T>.maxBinBy(bucketSize: Int,
                                    crossinline intMapper: (T) -> Int,
                                    rangeStart: Int? = null) = toList().maxBinBy(bucketSize,intMapper, rangeStart)

inline fun <T> Iterable<T>.maxBinBy(bucketSize: Int,
                                    crossinline intMapper: (T) -> Int,
                                    rangeStart: Int? = null) = intBinBy(
        bucketSize = bucketSize,
        intMapper = intMapper,
        rangeStart = rangeStart,
        groupOp = { it.max() }
)


inline fun <T> Sequence<T>.medianBinBy(bucketSize: Int,
                                       crossinline intMapper: (T) -> Int,
                                       rangeStart: Int? = null) = toList().medianBinBy(bucketSize,intMapper, rangeStart)

inline fun <T> Iterable<T>.medianBinBy(bucketSize: Int,
                                       crossinline intMapper: (T) -> Int,
                                       rangeStart: Int? = null) = intBinBy(
        bucketSize = bucketSize,
        intMapper = intMapper,
        rangeStart = rangeStart,
        groupOp = { it.median() }
)

inline fun <T> Sequence<T>.percentileBinBy(percentile: Double,
                                           bucketSize: Int,
                                           crossinline intMapper: (T) -> Int,
                                           rangeStart: Int? = null) = toList().percentileBinBy(percentile,bucketSize,intMapper, rangeStart)

inline fun <T> Iterable<T>.percentileBinBy(percentile: Double,
                                           bucketSize: Int,
                                           crossinline intMapper: (T) -> Int,
                                           rangeStart: Int? = null) = intBinBy(
        bucketSize = bucketSize,
        intMapper = intMapper,
        rangeStart = rangeStart,
        groupOp = { it.percentile(percentile) }
)



inline fun <T> Sequence<T>.varianceBinBy(bucketSize: Int,
                                         
                                         crossinline intMapper: (T) -> Int,
                                         rangeStart: Int? = null) = toList().varianceBinBy(bucketSize,intMapper, rangeStart)

inline fun <T> Iterable<T>.varianceBinBy(bucketSize: Int,
                                         
                                         crossinline intMapper: (T) -> Int,
                                         rangeStart: Int? = null) = intBinBy(
        bucketSize = bucketSize,
        intMapper = intMapper,
        rangeStart = rangeStart,
        groupOp = { it.variance() }
)


inline fun <T> Sequence<T>.standardDeviationBinBy(bucketSize: Int,
                                                  crossinline intMapper: (T) -> Int,
                                                  rangeStart: Int? = null) = toList().standardDeviationBinBy(bucketSize,intMapper, rangeStart)

inline fun <T> Iterable<T>.standardDeviationBinBy(bucketSize: Int,
                                                  crossinline intMapper: (T) -> Int,
                                                  rangeStart: Int? = null) = intBinBy(
        bucketSize = bucketSize,
        intMapper = intMapper,
        rangeStart = rangeStart,
        groupOp = { it.standardDeviation() }
)

inline fun <T> Sequence<T>.geometricMeanBinBy(bucketSize: Int,
                                              crossinline intMapper: (T) -> Int,
                                              rangeStart: Int? = null) = toList().geometricMeanBinBy(bucketSize,intMapper, rangeStart)

inline fun <T> Iterable<T>.geometricMeanBinBy(bucketSize: Int,
                                              crossinline intMapper: (T) -> Int,
                                              rangeStart: Int? = null) = intBinBy(
        bucketSize = bucketSize,
        intMapper = intMapper,
        rangeStart = rangeStart,
        groupOp = { it.geometricMean() }
)
