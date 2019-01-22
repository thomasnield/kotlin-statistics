package org.nield.kotlinstatistics.range


class XClosedRange<T : Comparable<T>>(val startInclusive: T, override val endInclusive: T) : Range<T>,
    kotlin.ranges.ClosedRange<T> by startInclusive..endInclusive {

    init {
        if (startInclusive > endInclusive) throw InvalidRangeException("[$startInclusive..$endInclusive] is an invalid XClosedRange!")
    }

    override val lowerBound get() = startInclusive

    override val upperBound get() = endInclusive

    override fun contains(value: T) = value in startInclusive..endInclusive

    override fun isEmpty() = endInclusive == startInclusive

    override fun toString() = "[$startInclusive..$endInclusive]"
}

