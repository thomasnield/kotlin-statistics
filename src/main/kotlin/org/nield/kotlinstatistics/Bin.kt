package org.nield.kotlinstatistics

import java.util.concurrent.atomic.AtomicBoolean

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

inline fun <T, C: Comparable<C>> List<T>.binByComparable(bucketIncrements: Int,
                                                         crossinline incrementer: (C) -> C,
                                                         crossinline binMapper: (T) -> C,
                                                         rangeStart: C? = null) = binByComparable(bucketIncrements, incrementer, binMapper, { it }, rangeStart)

inline fun <T, C: Comparable<C>, G> List<T>.binByComparable(bucketIncrements: Int,
                                                            crossinline incrementer: (C) -> C,
                                                            crossinline binMapper: (T) -> C,
                                                            crossinline groupOp: (List<T>) -> G,
                                                            rangeStart: C? = null
): BinModel<G, C> {

    val groupedByC = asSequence().groupBy(binMapper)
    val minC = rangeStart?:groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val buckets = mutableListOf<ClosedRange<C>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC

        val initial = AtomicBoolean(true)
        while (currentRangeEnd < maxC) {
            repeat(if (initial.getAndSet(false)) bucketIncrements - 1 else bucketIncrements) { currentRangeEnd = incrementer(currentRangeEnd) }
            add(currentRangeStart..currentRangeEnd)
            currentRangeStart = incrementer(currentRangeEnd)
        }
    }

    return buckets.asSequence()
            .map { it to mutableListOf<T>() }
            .map { bucketWithList ->
                groupedByC.entries.asSequence()
                        .filter { it.key in bucketWithList.first }
                        .forEach { bucketWithList.second.addAll(it.value) }
                bucketWithList
            }.map { Bin(it.first, groupOp(it.second)) }
            .toList()
            .let(::BinModel)
}
