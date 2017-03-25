package org.nield.kotlinstatistics


/**
 * Median
 * The middle value in the list of numbers
 */
fun Iterable<Int>.median(): Double = map(Int::toDouble).median()
fun Sequence<Int>.median(): Double = asIterable().median()
fun Array<Int>.median(): Double = asIterable().median()


/**
 * Variance
 * The average of the squared differences from the Mean
 */
fun Iterable<Int>.variance(): Double = map(Int::toDouble).variance()
fun Sequence<Int>.variance() = asIterable().variance()
fun Array<Int>.variance() = asIterable().variance()

/**
 * Standard Deviation
 * The square root of the variance
 */
fun Iterable<Int>.standardDeviation(): Double = map(Int::toDouble).standardDeviation()
fun Sequence<Int>.standardDeviation() = asIterable().standardDeviation()
fun Array<Int>.standardDeviation() = asIterable().standardDeviation()