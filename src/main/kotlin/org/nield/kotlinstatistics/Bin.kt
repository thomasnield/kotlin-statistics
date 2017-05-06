package org.nield.kotlinstatistics

class Bin<out T,in C: Comparable<C>>(val range: ClosedRange<in C>, val item: T)

class BinModel<out T, in C: Comparable<C>>(val bins: List<Bin<T, C>>)

inline fun <T, R, C: Comparable<C>> List<T>.binBy(rangeStart: C,
                                                  bucketSize: Int,
                                                  crossinline mapper: (T) -> C,
                                                  crossinline incrementer: (C) -> C,
                                                  crossinline result: (List<T>) -> R) {

    val groupedByC = asSequence().groupBy(mapper)
    val maxC = groupedByC.keys.max()!!

    val allIncrements = generateSequence(rangeStart) { incrementer(rangeStart) }
            .takeWhile { it <= maxC }
            .map { it to (groupedByC[it]?:listOf()) }


}