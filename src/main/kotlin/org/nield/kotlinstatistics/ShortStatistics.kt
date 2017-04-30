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


inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().averageBy(keySelector, shortMapper)

inline fun <T,K> Sequence<T>.minBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.min() }

inline fun <T,K> Iterable<T>.minBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().minBy(keySelector, shortMapper)

inline fun <T,K> Sequence<T>.maxBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.max() }

inline fun <T,K> Iterable<T>.maxBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().maxBy(keySelector, shortMapper)

inline fun <T,K> Sequence<T>.medianBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.median() }

inline fun <T,K> Iterable<T>.medianBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().medianBy(keySelector, shortMapper)

inline fun <T,K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.variance() }

inline fun <T,K> Iterable<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().varianceBy(keySelector, shortMapper)

inline fun <T,K> Sequence<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        groupApply(keySelector, shortMapper) { it.standardDeviation() }

inline fun <T,K> Iterable<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline shortMapper: (T) -> Short) =
        asSequence().standardDeviationBy(keySelector, shortMapper)

