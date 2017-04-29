package org.nield.kotlinstatistics

import java.math.BigDecimal

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

fun Iterable<BigDecimal>.standardDeviation() = variance().let { Math.sqrt(it.toDouble()) }
fun Sequence<BigDecimal>.standardDeviation() = asIterable().standardDeviation()
fun Array<out BigDecimal>.standardDeviation() = asIterable().standardDeviation()


// AGGREGATION OPERATORS

inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalMapper) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline bigDecimalMapper: (T) -> BigDecimal) =
        asSequence().sumBy(keySelector, bigDecimalMapper)

