package org.nield.kotlinstatistics


//regression
inline fun <T> Iterable<T>.simpleRegression(
    crossinline xSelector: (T) -> Number,
    crossinline ySelector: (T) -> Number
) = asSequence().simpleRegression(xSelector, ySelector)

typealias ASR = org.apache.commons.math3.stat.regression.SimpleRegression

inline fun <T> Sequence<T>.simpleRegression(
    crossinline xSelector: (T) -> Number,
    crossinline ySelector: (T) -> Number
): SimpleRegression {
    val r = ASR()
    forEach { r.addData(xSelector(it).toDouble(), ySelector(it).toDouble()) }
    return ApacheSimpleRegression(r)
}

fun Sequence<Pair<Number, Number>>.simpleRegression() = simpleRegression({ it.first }, { it.second })
fun Iterable<Pair<Number, Number>>.simpleRegression() = simpleRegression({ it.first }, { it.second })


// Simple number vector ops
val <N : Number> Iterable<N>.descriptiveStatistics: Descriptives
    get() = DescriptiveStatistics().apply {
        forEach {
            addValue(
                it.toDouble()
            )
        }
    }.let(::ApacheDescriptives)
val <N : Number> Sequence<N>.descriptiveStatistics: Descriptives
    get() = DescriptiveStatistics().apply {
        forEach {
            addValue(
                it.toDouble()
            )
        }
    }.let(::ApacheDescriptives)
val <N : Number> Array<out N>.descriptiveStatistics: Descriptives
    get() = DescriptiveStatistics().apply {
        forEach {
            addValue(
                it.toDouble()
            )
        }
    }.let(::ApacheDescriptives)


fun <N : Number> Iterable<N>.geometricMean() = asSequence().geometricMean()
fun <N : Number> Sequence<N>.geometricMean() = StatUtils.geometricMean(map { it.toDouble() }.toList().toDoubleArray())
fun <N : Number> Array<out N>.geometricMean() = asSequence().geometricMean()


fun <N : Number> Iterable<N>.median() = asSequence().median()
fun <N : Number> Sequence<N>.median() = map { it.toDouble() }.percentile(50.0)
fun <N : Number> Array<out N>.median() = asSequence().median()


fun <N : Number> Iterable<N>.percentile(percentile: Double) = asSequence().percentile(percentile)
fun <N : Number> Sequence<N>.percentile(percentile: Double) =
    StatUtils.percentile(map { it.toDouble() }.toList().toDoubleArray(), percentile)

fun <N : Number> Array<out N>.percentile(percentile: Double) = asSequence().percentile(percentile)


fun <N : Number> Iterable<N>.variance() = asSequence().variance()
fun <N : Number> Sequence<N>.variance() = StatUtils.variance(map { it.toDouble() }.toList().toDoubleArray())
fun <N : Number> Array<out N>.variance() = asSequence().variance()


fun <N : Number> Iterable<N>.sumOfSquares() = asSequence().sumOfSquares()
fun <N : Number> Sequence<N>.sumOfSquares() = StatUtils.sumSq(map { it.toDouble() }.toList().toDoubleArray())
fun <N : Number> Array<out N>.sumOfSquares() = asSequence().sumOfSquares()


fun <N : Number> Iterable<N>.standardDeviation() = descriptiveStatistics.standardDeviation
fun <N : Number> Sequence<N>.standardDeviation() = descriptiveStatistics.standardDeviation
fun <N : Number> Array<out N>.standardDeviation() = descriptiveStatistics.standardDeviation


fun <N : Number> Iterable<N>.normalize() = asSequence().normalize()
fun <N : Number> Sequence<N>.normalize() = StatUtils.normalize(map { it.toDouble() }.toList().toDoubleArray())
fun <N : Number> Array<out N>.normalize() = asSequence().normalize()


val <N : Number> Iterable<N>.kurtosis get() = descriptiveStatistics.kurtosis
val <N : Number> Sequence<N>.kurtosis get() = descriptiveStatistics.kurtosis
val <N : Number> Array<out N>.kurtosis get() = descriptiveStatistics.kurtosis


val <N : Number> Iterable<N>.skewness get() = descriptiveStatistics.skewness
val <N : Number> Sequence<N>.skewness get() = descriptiveStatistics.skewness
val <N : Number> Array<out N>.skewness get() = descriptiveStatistics.skewness


// slicing operations


inline fun <T, K> Sequence<T>.descriptiveStatisticsBy(
    crossinline keySelector: (T) -> K,
    crossinline valueSelector: (T) -> Number
) =
    groupApply(keySelector, valueSelector) { it.descriptiveStatistics }

inline fun <T, K> Iterable<T>.descriptiveStatisticsBy(
    crossinline keySelector: (T) -> K,
    crossinline valueSelector: (T) -> Number
) =
    asSequence().descriptiveStatisticsBy(keySelector, valueSelector)

fun <K, N : Number> Sequence<Pair<K, N>>.descriptiveStatisticsBy() =
    groupApply({ it.first }, { it.second }) { it.descriptiveStatistics }

fun <K, N : Number> Iterable<Pair<K, N>>.descriptiveStatisticsBy() = asSequence().descriptiveStatisticsBy()


inline fun <T, K> Sequence<T>.medianBy(crossinline keySelector: (T) -> K, crossinline valueSelector: (T) -> Number) =
    groupApply(keySelector, valueSelector) { it.median() }

fun <K> Sequence<Pair<K, Number>>.medianBy() =
    groupApply({ it.first }, { it.second }) { it.median() }

inline fun <T, K> Iterable<T>.medianBy(crossinline keySelector: (T) -> K, crossinline valueSelector: (T) -> Number) =
    asSequence().medianBy(keySelector, valueSelector)

fun <K> Iterable<Pair<K, Number>>.medianBy() = asSequence().medianBy()


inline fun <T, K> Sequence<T>.percentileBy(
    percentile: Double,
    crossinline keySelector: (T) -> K,
    crossinline valueSelector: (T) -> Number
) =
    groupApply(keySelector, valueSelector) { it.percentile(percentile) }

fun <K, N : Number> Sequence<Pair<K, N>>.percentileBy(percentile: Double) =
    groupApply({ it.first }, { it.second }) { it.percentile(percentile) }

inline fun <T, K> Iterable<T>.percentileBy(
    percentile: Double,
    crossinline keySelector: (T) -> K,
    crossinline valueSelector: (T) -> Number
) =
    asSequence().percentileBy(percentile, keySelector, valueSelector)

fun <K, N : Number> Iterable<Pair<K, N>>.percentileBy(percentile: Double) = asSequence().percentileBy(percentile)


inline fun <T, K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline valueSelector: (T) -> Number) =
    groupApply(keySelector, valueSelector) { it.variance() }

fun <K, N : Number> Sequence<Pair<K, N>>.varianceBy() =
    groupApply({ it.first }, { it.second }) { it.variance() }

inline fun <T, K> Iterable<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline valueSelector: (T) -> Number) =
    asSequence().varianceBy(keySelector, valueSelector)

fun <K, N : Number> Iterable<Pair<K, N>>.varianceBy() = asSequence().varianceBy()


inline fun <T, K> Sequence<T>.standardDeviationBy(
    crossinline keySelector: (T) -> K,
    crossinline valueSelector: (T) -> Number
) =
    groupApply(keySelector, valueSelector) { it.standardDeviation() }

fun <K, N : Number> Sequence<Pair<K, N>>.standardDeviationBy() =
    groupApply({ it.first }, { it.second }) { it.standardDeviation() }


inline fun <T, K> Iterable<T>.standardDeviationBy(
    crossinline keySelector: (T) -> K,
    crossinline valueSelector: (T) -> Number
) =
    asSequence().standardDeviationBy(keySelector, valueSelector)

fun <K, N : Number> Iterable<Pair<K, N>>.standardDeviationBy() = asSequence().standardDeviationBy()


inline fun <T, K> Sequence<T>.geometricMeanBy(
    crossinline keySelector: (T) -> K,
    crossinline valueSelector: (T) -> Number
) =
    groupApply(keySelector, valueSelector) { it.geometricMean() }

fun <K, N : Number> Sequence<Pair<K, N>>.geometricMeanBy() =
    groupApply({ it.first }, { it.second }) { it.geometricMean() }


inline fun <T, K> Iterable<T>.geometricMeanBy(
    crossinline keySelector: (T) -> K,
    crossinline valueSelector: (T) -> Number
) =
    asSequence().standardDeviationBy(keySelector, valueSelector)

fun <K, N : Number> Iterable<Pair<K, N>>.geometricMeanBy() = asSequence().standardDeviationBy()
