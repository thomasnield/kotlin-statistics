package org.nield.kotlinstatistics

class Bin<out T,in C: Comparable<C>>(val range: ClosedRange<in C>, val item: T)

class BinModel<out T, in C: Comparable<C>>(val bins: List<Bin<T, C>>)

inline fun <T, C: Comparable<C>> List<T>.binBy(bucketSize: Int,
                                                  crossinline mapper: (T) -> C,
                                                  crossinline incrementer: (C) -> C,
                                                  rangeStart: C? = null
                                                  ): Map<ClosedRange<C>, List<T>> {

    val groupedByC = asSequence().groupBy(mapper)
    val maxC = groupedByC.keys.max()!!
    val minC = rangeStart?:groupedByC.keys.min()!!

    val fullSpan = generateSequence(minC) { incrementer(it) }
            .takeWhile { it <= maxC }

    var index = 0
    var currentRangeStart = minC
    var currentRangeEnd = maxC
    val buckets = mutableListOf<ClosedRange<C>>()

    fullSpan.forEach { c ->
        if ((index > 0 && (index % bucketSize) == 0) || c == maxC) {
            buckets.add(currentRangeStart..currentRangeEnd)
            currentRangeStart = c
        }
        currentRangeEnd = c
        index++
    }
    println(buckets)

    val getBucket = { c: C ->  buckets.find { c in it }!! }

    return fullSpan
            .map { it to (groupedByC[it]?:listOf()) }
            .groupBy({getBucket(it.first)},{it.second})
            .entries.asSequence()
            .map { it.key to it.value.flatten() }
            .toMap()
}