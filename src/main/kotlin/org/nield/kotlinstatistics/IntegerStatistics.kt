package org.nield.kotlinstatistics


/**
 * Median
 */
fun Iterable<Int>.median(): Double = map(Int::toDouble).median()

fun Sequence<Int>.median(): Double = asIterable().median()


/**
 * Returns the difference between the highest and lowest values in the set.
 */
fun Iterable<Int>.range(): Int = sorted().let { it.last() - it.first() }