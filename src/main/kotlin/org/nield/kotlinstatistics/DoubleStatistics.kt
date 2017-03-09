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

fun Array<out Double>.median() = let { array ->
    val listSize = array.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        array[middle]
    else
        (array[middle - 1] + array[middle]) / 2.0
}

fun DoubleArray.median() = let { array ->
    val listSize = array.size
    val middle = listSize / 2

    if (listSize % 2 == 1)
        array[middle]
    else
        (array[middle - 1] + array[middle]) / 2.0
}

/**
 * Returns the difference between the `average()` and the `median()`.
 * A positive difference indicates a positive skewDifference, and negative indicates a negative skewDifference
 */
fun DoubleArray.skewDifference() = let { it.average() - it.median() }
fun Iterable<Double>.skewDifference() = let { it.average() - it.median() }
fun Sequence<Double>.skewDifference() = toList().let { it.average() - it.median() }
fun Array<out Double>.skewDifference() = toDoubleArray().skewDifference()

fun Iterable<Double>.variance() = toList().toDoubleArray().let {
    val avg = it.average()
    asSequence().map { (it - avg).let { it * it } }.average()
}
fun Sequence<Double>.variance() = asIterable().variance()
fun Array<out Double>.variance() = asIterable().variance()
fun DoubleArray.variance() = asIterable().variance()




fun Iterable<Double>.standardDeviation() = variance().let { Math.sqrt(it) }
fun Sequence<Double>.standardDeviation() = asIterable().standardDeviation()
fun Array<out Double>.standardDeviation() = asIterable().standardDeviation()
fun DoubleArray.standardDeviation() = asIterable().standardDeviation()