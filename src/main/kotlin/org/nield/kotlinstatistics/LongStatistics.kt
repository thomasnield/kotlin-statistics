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


inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().averageBy(keySelector, longMapper)

inline fun <T,K> Sequence<T>.minBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.min() }

inline fun <T,K> Iterable<T>.minBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().minBy(keySelector, longMapper)

inline fun <T,K> Sequence<T>.maxBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.max() }

inline fun <T,K> Iterable<T>.maxBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().maxBy(keySelector, longMapper)

inline fun <T,K> Sequence<T>.medianBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.median() }

inline fun <T,K> Iterable<T>.medianBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().medianBy(keySelector, longMapper)

inline fun <T,K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.variance() }

inline fun <T,K> Iterable<T>.varianceby(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().varianceBy(keySelector, longMapper)

inline fun <T,K> Sequence<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        groupApply(keySelector, longMapper) { it.standardDeviation() }

inline fun <T,K> Iterable<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline longMapper: (T) -> Long) =
        asSequence().standardDeviationBy(keySelector, longMapper)

