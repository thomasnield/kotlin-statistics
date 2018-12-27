package org.nield.kotlinstatistics.range

class OpenClosedRange<T: Comparable<T>>(val startExclusive: T, val endInclusive: T): Range<T> {

    init {
        if (startExclusive > endInclusive) throw InvalidRangeException("($startExclusive..$endInclusive] is an invalid OpenClosedRange!")
    }

    override val lowerBound get() = startExclusive

    override val upperBound get() = endInclusive

    override fun contains(value: T) = value > startExclusive && value <= endInclusive

    override fun isEmpty() = endInclusive == startExclusive

    override fun toString() = "($startExclusive..$endInclusive]"
}


