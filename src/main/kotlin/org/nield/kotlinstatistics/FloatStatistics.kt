package org.nield.kotlinstatistics


fun Iterable<Float>.median() = toList().let { list ->
    val listSize = list.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        list[middle]
    else
        (list[middle - 1] + list[middle]) / 2.0F
}
fun Sequence<Float>.median() = asIterable().median()

fun Array<out Float>.median() = let { array ->
    val listSize = array.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        array[middle]
    else
        (array[middle - 1] + array[middle]) / 2.0F
}

fun FloatArray.median() = let { array ->
    val listSize = array.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        array[middle]
    else
        (array[middle - 1] + array[middle]) / 2.0F
}

fun Iterable<Float>.variance() = toList().toFloatArray().let {
    val avg = it.average()
    asSequence().map { (it - avg).let { it * it } }.average()
}
fun Sequence<Float>.variance() = asIterable().variance()
fun Array<out Float>.variance() = asIterable().variance()
fun FloatArray.variance() = asIterable().variance()


fun Iterable<Float>.standardDeviation() = variance().let { Math.sqrt(it) }
fun Sequence<Float>.standardDeviation() = asIterable().standardDeviation()
fun Array<out Float>.standardDeviation() = asIterable().standardDeviation()
fun FloatArray.standardDeviation() = asIterable().standardDeviation()



// AGGREGATION OPERATORS

inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        groupApply(keySelector, floatMapper) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        asSequence().sumBy(keySelector, floatMapper)

