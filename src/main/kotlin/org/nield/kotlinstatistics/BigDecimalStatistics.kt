package org.nield.kotlinstatistics

import jdk.nashorn.internal.runtime.JSType.toDouble
import java.math.BigDecimal
import java.math.BigDecimal.ROUND_HALF_UP



fun Sequence<BigDecimal>.sum() = fold(BigDecimal.ZERO) { x,y -> x + y }!!
fun Iterable<BigDecimal>.sum() = fold(BigDecimal.ZERO) { x,y -> x + y }!!

fun Sequence<BigDecimal>.average() = toList().let { list ->
    list.sum() / BigDecimal.valueOf(list.count().toDouble())
}
fun Iterable<BigDecimal>.average() = asSequence().average()

fun Iterable<BigDecimal>.median() = toList().let { list ->
    val listSize = list.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        list[middle]
    else
        (list[middle - 1] + list[middle]) / BigDecimal.valueOf(2.0)
}
fun Sequence<BigDecimal>.median() = asIterable().median()

fun Array<out BigDecimal>.median() = let { array ->
    val listSize = array.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        array[middle]
    else
        (array[middle - 1] + array[middle]) / BigDecimal.valueOf(2.0)
}

fun Iterable<BigDecimal>.variance() = toList().let {
    val avg = it.average()
    asSequence().map { (it - avg).let { it * it } }.average()
}
fun Sequence<BigDecimal>.variance() = asIterable().variance()
fun Array<out BigDecimal>.variance() = asIterable().variance()

fun Iterable<BigDecimal>.standardDeviation() = variance().sqrt()
fun Sequence<BigDecimal>.standardDeviation() = asIterable().standardDeviation()
fun Array<out BigDecimal>.standardDeviation() = asIterable().standardDeviation()


fun BigDecimal.sqrt(): BigDecimal {
    val x = BigDecimal(Math.sqrt(toDouble()))
    return x.add(BigDecimal(subtract(x.multiply(x)).toDouble() / (x.toDouble() * 2.0)))
}

// AGGREGATION OPERATORS

inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().sumBy(keySelector, bigDecimalMapper)

inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().averageBy(keySelector, bigDecimalMapper)

inline fun <T,K> Sequence<T>.minBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.min() }

inline fun <T,K> Iterable<T>.minBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().minBy(keySelector, bigDecimalMapper)

inline fun <T,K> Sequence<T>.maxBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.max() }

inline fun <T,K> Iterable<T>.maxBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().maxBy(keySelector, bigDecimalMapper)

inline fun <T,K> Sequence<T>.medianBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.median() }

inline fun <T,K> Iterable<T>.medianBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().medianBy(keySelector, bigDecimalMapper)

inline fun <T,K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.variance() }

inline fun <T,K> Iterable<T>.varianceby(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().varianceBy(keySelector, bigDecimalMapper)

inline fun <T,K> Sequence<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.standardDeviation() }

inline fun <T,K> Iterable<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().standardDeviationBy(keySelector, bigDecimalMapper)