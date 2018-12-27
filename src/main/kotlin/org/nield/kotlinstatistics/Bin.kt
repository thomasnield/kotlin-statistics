package org.nield.kotlinstatistics

import org.nield.kotlinstatistics.range.ClosedOpenRange
import org.nield.kotlinstatistics.range.Range
import org.nield.kotlinstatistics.range.XClosedRange
import org.nield.kotlinstatistics.range.until
import java.util.concurrent.atomic.AtomicBoolean

class Bin<T,C: Comparable<C>>(val range: Range<C>, val value: T) {
    operator fun contains(key: C) = key in range
    override fun toString(): String {
        return "Bin(range=$range, value=$value)"
    }
}

class BinModel<T, C: Comparable<C>>(val bins: List<Bin<T, C>>): Iterable<Bin<T,C>> by bins {
    operator fun get(key: C) = bins.find { key in it.range }
    operator fun contains(key: C) = bins.any { key in it.range }
    override fun toString(): String {
        return "BinModel(bins=$bins)"
    }
}

inline fun <T, C: Comparable<C>> List<T>.binByComparable(binIncrements: Int,
                                                         crossinline incrementer: (C) -> C,
                                                         crossinline valueSelector: (T) -> C,
                                                         rangeStart: C? = null,
                                                         endExclusive: Boolean = false) = binByComparable(binIncrements, incrementer, valueSelector, { it }, rangeStart, endExclusive)

inline fun <T, C: Comparable<C>, G> List<T>.binByComparable(binIncrements: Int,
                                                            crossinline incrementer: (C) -> C,
                                                            crossinline valueSelector: (T) -> C,
                                                            crossinline groupOp: (List<T>) -> G,
                                                            rangeStart: C? = null,
                                                            endExclusive: Boolean = false
                                                            ): BinModel<G, C> {

    val groupedByC = asSequence().groupBy(valueSelector)
    val minC = rangeStart?:groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val bins = mutableListOf<Range<C>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC

        val initial = AtomicBoolean(true)
        val rangeFactory = { lowerBound: C, upperBound: C  -> if (endExclusive) ClosedOpenRange(lowerBound, upperBound) else XClosedRange(lowerBound, upperBound) }

        while (currentRangeEnd < maxC) {
            repeat(if (initial.getAndSet(false)) binIncrements - 1 else binIncrements) { currentRangeEnd = incrementer(currentRangeEnd) }
            add(rangeFactory(currentRangeStart, currentRangeEnd))
            currentRangeStart = incrementer(currentRangeEnd)
        }
    }

    return bins.asSequence()
            .map { it to mutableListOf<T>() }
            .map { binWithList ->
                groupedByC.entries.asSequence()
                        .filter { it.key in binWithList.first }
                        .forEach { binWithList.second.addAll(it.value) }
                binWithList
            }.map { Bin(it.first, groupOp(it.second)) }
            .toList()
            .let(::BinModel)
}


inline fun <T, C: Comparable<C>> Sequence<T>.binByComparable(binIncrements: Int,
                                                             crossinline incrementer: (C) -> C,
                                                             crossinline valueSelector: (T) -> C,
                                                             rangeStart: C? = null,
                                                             endExclusive: Boolean = false) = binByComparable(binIncrements, incrementer, valueSelector, { it }, rangeStart, endExclusive)

inline fun <T, C: Comparable<C>, G> Sequence<T>.binByComparable(binIncrements: Int,
                                                                crossinline incrementer: (C) -> C,
                                                                crossinline valueSelector: (T) -> C,
                                                                crossinline groupOp: (List<T>) -> G,
                                                                rangeStart: C? = null,
                                                                endExclusive: Boolean = false)  = toList().binByComparable(binIncrements, incrementer, valueSelector, groupOp, rangeStart, endExclusive)