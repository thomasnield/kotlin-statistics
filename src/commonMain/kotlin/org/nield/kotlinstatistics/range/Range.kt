package org.nield.kotlinstatistics.range

/**
 * A `Range` is an abstract interface common to all ranges, regardless of their inclusive or exclusive nature
 */
interface Range<T : Comparable<T>> {

    /**
     * The minimum value in the range, regardless if it is inclusive or exclusive
     */
    val lowerBound: T
    /**
     * The maximum value in the range, regardless if it is inclusive or exclusive
     */
    val upperBound: T

    /**
     * Checks whether the specified [value] belongs to the range.
     */
    operator fun contains(value: T): Boolean

    /**
     * Checks whether the range is empty.
     */
    fun isEmpty(): Boolean
}