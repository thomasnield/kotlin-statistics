package org.nield.kotlinstatistics


fun Iterable<Int>.median(): Double = toList().let { list ->
    val listSize = list.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        list[middle].toDouble()
    else
        (list[middle - 1] + list[middle]) / 2.0
}
fun Sequence<Int>.median() = asIterable().median()

fun Array<out Int>.median() = let { array ->
    val listSize = array.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        array[middle].toDouble()
    else
        (array[middle - 1] + array[middle]) / 2.0
}

fun IntArray.median() = let { array ->
    val listSize = array.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        array[middle].toDouble()
    else
        (array[middle - 1] + array[middle]) / 2.0
}

fun Iterable<Int>.variance() = toList().toIntArray().let {
    val avg = it.average()
    asSequence().map { (it - avg).let { it * it } }.average()
}
fun Sequence<Int>.variance() = asIterable().variance()
fun Array<out Int>.variance() = asIterable().variance()
fun IntArray.variance() = asIterable().variance()


fun Iterable<Int>.standardDeviation() = variance().let { Math.sqrt(it) }
fun Sequence<Int>.standardDeviation() = asIterable().standardDeviation()
fun Array<out Int>.standardDeviation() = asIterable().standardDeviation()
fun IntArray.standardDeviation() = asIterable().standardDeviation()


// AGGREGATION OPERATORS
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
