package org.nield.kotlinstatistics


fun Iterable<Long>.median(): Double = toList().let { list ->
    val listSize = list.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        list[middle].toDouble()
    else
        (list[middle - 1] + list[middle]) / 2.0
}
fun Sequence<Long>.median() = asIterable().median()

fun Array<out Long>.median() = let { array ->
    val listSize = array.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        array[middle].toDouble()
    else
        (array[middle - 1] + array[middle]) / 2.0
}

fun LongArray.median() = let { array ->
    val listSize = array.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        array[middle].toDouble()
    else
        (array[middle - 1] + array[middle]) / 2.0
}

fun Iterable<Long>.variance() = toList().toLongArray().let {
    val avg = it.average()
    asSequence().map { (it - avg).let { it * it } }.average()
}
fun Sequence<Long>.variance() = asIterable().variance()
fun Array<out Long>.variance() = asIterable().variance()
fun LongArray.variance() = asIterable().variance()


fun Iterable<Long>.standardDeviation() = variance().let { Math.sqrt(it) }
fun Sequence<Long>.standardDeviation() = asIterable().standardDeviation()
fun Array<out Long>.standardDeviation() = asIterable().standardDeviation()
fun LongArray.standardDeviation() = asIterable().standardDeviation()


// AGGREGATION OPERATORS
inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Long) =
        groupApply(keySelector, intMapper) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline intMapper: (T) -> Long) =
        asSequence().sumBy(keySelector, intMapper)


