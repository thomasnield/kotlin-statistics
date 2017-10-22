package org.nield.kotlinstatistics

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics


fun Sequence<Pair<Number, Number>>.simpleRegression() = simpleRegression({it.first},{it.second})
fun Iterable<Pair<Number, Number>>.simpleRegression() = simpleRegression({it.first},{it.second})

inline fun <T> Iterable<T>.simpleRegression(crossinline xSelector: (T) -> Number, crossinline ySelector: (T) -> Number) = asSequence().simpleRegression(xSelector, ySelector)


inline fun <T> Sequence<T>.simpleRegression(crossinline xSelector: (T) -> Number, crossinline ySelector: (T) -> Number): SimpleRegression {
    val r = ASR()
    forEach { r.addData(xSelector(it).toDouble(), ySelector(it).toDouble()) }
    return ApacheSimpleRegression(r)
}

// Simple number vector ops
val Iterable<Number>.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)
val Sequence<Number>.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)
val Array<out Number>.descriptiveStatistics: Descriptives get() = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)




inline fun <T,K> Sequence<T>.descriptiveStatisticsBy(crossinline keySelector: (T) -> K, crossinline valueMapper: (T) -> Number) =
        groupApply(keySelector, valueMapper) { it.descriptiveStatistics }

inline fun <T,K> Iterable<T>.descriptiveStatisticsBy(crossinline keySelector: (T) -> K, crossinline valueMapper: (T) -> Number) =
        asSequence().descriptiveStatisticsBy(keySelector, valueMapper)

fun <K, N: Number> Sequence<Pair<K,N>>.descriptiveStatisticsBy() =
        groupApply({it.first}, {it.second}) { it.descriptiveStatistics }

fun <K, N: Number> Iterable<Pair<K,N>>.descriptiveStatisticsBy() = asSequence().descriptiveStatisticsBy()
