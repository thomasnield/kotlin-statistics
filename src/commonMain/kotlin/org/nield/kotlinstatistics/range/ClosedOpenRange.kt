package org.nield.kotlinstatistics.range

class ClosedOpenRange<T : Comparable<T>>(val startInclusive: T, val endExclusive: T) : Range<T> {

    init {
        if (startInclusive > endExclusive) throw InvalidRangeException("($startInclusive..$endExclusive] is an invalid ClosedOpenRange!")
    }

    override val lowerBound get() = startInclusive

    override val upperBound get() = endExclusive

    override fun contains(value: T) = value >= startInclusive && value < endExclusive

    override fun isEmpty() = endExclusive == startInclusive

    override fun toString() = "[$startInclusive..$endExclusive)"
}


infix fun <T : Comparable<T>> T.until(endExclusive: T) = ClosedOpenRange(this, endExclusive)

fun <T : Comparable<T>> XClosedRange<T>.toClosedOpenRange() = ClosedOpenRange(start, endInclusive)
