package org.nield.kotlinstatistics

class Bin<out T,in C: Comparable<C>>(val range: ClosedRange<in C>, val value: T) {
    operator fun contains(key: C) = key in range
    override fun toString(): String {
        return "Bin(range=$range, value=$value)"
    }
}

class BinModel<out T, in C: Comparable<C>>(val bins: List<Bin<T, C>>): Iterable<Bin<T,C>> by bins {
    operator fun get(key: C) = bins.find { key in it.range }
    operator fun contains(key: C) = bins.any { key in it.range }
    override fun toString(): String {
        return "BinModel(bins=$bins)"
    }
}

inline fun <T, C: Comparable<C>> List<T>.binBy(bucketSize: Int,
                                                  crossinline incrementer: (C) -> C,
                                                   crossinline mapper: (T) -> C,
                                                   rangeStart: C? = null
                                                  ): BinModel<List<T>, C> {

    val groupedByC = asSequence().groupBy(mapper)
    val maxC = groupedByC.keys.max()!!
    val minC = rangeStart?:groupedByC.keys.min()!!

    val fullSpan = generateSequence(minC) { incrementer(it) }
            .takeWhile { it <= maxC }
            .toList()

    var currentRangeStart = minC
    var currentRangeEnd = maxC
    val buckets = mutableListOf<ClosedRange<C>>()

    fullSpan.forEachIndexed { i,c ->

        if (i > 0 && (i % bucketSize) == 0) {
            buckets.add(currentRangeStart..currentRangeEnd)
            currentRangeStart = c
        }
        currentRangeEnd = c
        if (c == maxC) {
            buckets.add(currentRangeStart..currentRangeEnd)
        }
    }

    val getBucket = { c: C ->
        buckets.find { c in it }!!
    }

    return fullSpan
            .map { it to (groupedByC[it]?:listOf()) }
            .groupBy({getBucket(it.first)},{it.second})
            .entries.asSequence()
            .map { Bin(it.key, it.value.flatten()) }
            .toList()
            .let(::BinModel)
}