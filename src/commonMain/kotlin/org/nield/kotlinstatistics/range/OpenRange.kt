package org.nield.kotlinstatistics.range

class OpenRange<T : Comparable<T>>(val startExclusive: T, val endExclusive: T) : Range<T> {

    init {
        if (startExclusive == endExclusive ||
            startExclusive > endExclusive
        ) throw InvalidRangeException("($startExclusive..$endExclusive) is an invalid OpenRange!")
    }

    override val lowerBound get() = startExclusive

    override val upperBound get() = endExclusive

    override fun contains(value: T) = value > startExclusive && value < endExclusive

    override fun isEmpty() = endExclusive <= startExclusive

    override fun toString() = "($startExclusive..$endExclusive)"
}

