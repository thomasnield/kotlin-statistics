package org.nield.kotlinstatistics


fun Iterable<Short>.median(): Double = toList().let { list ->
    val listSize = list.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        list[middle].toDouble()
    else
        (list[middle - 1] + list[middle]) / 2.0
}
fun Sequence<Short>.median() = asIterable().median()

fun Array<out Short>.median() = let { array ->
    val listSize = array.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        array[middle].toDouble()
    else
        (array[middle - 1] + array[middle]) / 2.0
}

fun ShortArray.median() = let { array ->
    val listSize = array.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        array[middle].toDouble()
    else
        (array[middle - 1] + array[middle]) / 2.0
}

fun Iterable<Short>.variance() = toList().toShortArray().let {
    val avg = it.average()
    asSequence().map { (it - avg).let { it * it } }.average()
}
fun Sequence<Short>.variance() = asIterable().variance()
fun Array<out Short>.variance() = asIterable().variance()
fun ShortArray.variance() = asIterable().variance()


fun Iterable<Short>.standardDeviation() = variance().let { Math.sqrt(it) }
fun Sequence<Short>.standardDeviation() = asIterable().standardDeviation()
fun Array<out Short>.standardDeviation() = asIterable().standardDeviation()
fun ShortArray.standardDeviation() = asIterable().standardDeviation()


// AGGREGATION OPERATORS
inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().sumBy(keySelector, shortMapper)


