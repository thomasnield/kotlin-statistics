package org.nield.kotlinstatistics


fun Iterable<Double>.median() = toList().let { list ->
    val listSize = list.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        list[middle]
    else
        (list[middle - 1] + list[middle]) / 2.0
}

fun Sequence<Double>.median() = asIterable().median()

fun Array<out Double>.median() = asIterable().median()

fun DoubleArray.median() = asIterable().median()