package org.nield.kotlinstatistics

import org.nield.kotlinstatistics.range.ClosedOpenRange
import org.nield.kotlinstatistics.range.until
import java.math.BigDecimal

fun Sequence<BigDecimal>.sum() = fold(BigDecimal.ZERO) { x,y -> x + y }!!
fun Iterable<BigDecimal>.sum() = fold(BigDecimal.ZERO) { x,y -> x + y }!!

fun Sequence<BigDecimal>.average() = toList().let { list ->
    list.sum() / BigDecimal.valueOf(list.count().toDouble())
}
fun Iterable<BigDecimal>.average() = asSequence().average()




// AGGREGATION OPERATORS


inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline bigDecimalSelector: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalSelector) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline bigDecimalSelector: (T) -> BigDecimal) =
        asSequence().sumBy(keySelector, bigDecimalSelector)

fun <K> Sequence<Pair<K,BigDecimal>>.sumBy() =
        groupApply({it.first}, {it.second}) { it.sum() }

fun <K> Iterable<Pair<K,BigDecimal>>.sumBy() = asSequence().sumBy()




inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline bigDecimalSelector: (T) -> BigDecimal) =
        groupApply(keySelector, bigDecimalSelector) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline bigDecimalSelector: (T) -> BigDecimal) =
        asSequence().averageBy(keySelector, bigDecimalSelector)

fun <K> Sequence<Pair<K,BigDecimal>>.averageBy() =
        groupApply({it.first}, {it.second}) { it.average() }

fun <K> Iterable<Pair<K,BigDecimal>>.averageBy() = asSequence().averageBy()




// bin operators


inline fun <T> Sequence<T>.binByBigDecimal(binSize: BigDecimal,
                                           crossinline valueSelector: (T) -> BigDecimal,
                                           rangeStart: BigDecimal? = null
) = toList().binByBigDecimal(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Sequence<T>.binByBigDecimal(binSize: BigDecimal,
                                              crossinline valueSelector: (T) -> BigDecimal,
                                              crossinline groupOp: (List<T>) -> G,
                                              rangeStart: BigDecimal? = null
) = toList().binByBigDecimal(binSize, valueSelector, groupOp, rangeStart)

inline fun <T> Iterable<T>.binByBigDecimal(binSize: BigDecimal,
                                       crossinline valueSelector: (T) -> BigDecimal,
                                       rangeStart: BigDecimal? = null
) = toList().binByBigDecimal(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Iterable<T>.binByBigDecimal(binSize: BigDecimal,
                                              crossinline valueSelector: (T) -> BigDecimal,
                                          crossinline groupOp: (List<T>) -> G,
                                          rangeStart: BigDecimal? = null
) = toList().binByBigDecimal(binSize, valueSelector, groupOp, rangeStart)

inline fun <T> List<T>.binByBigDecimal(binSize: BigDecimal,
                                       crossinline valueSelector: (T) -> BigDecimal,
                                       rangeStart: BigDecimal? = null
): BinModel<List<T>, BigDecimal> = binByBigDecimal(binSize, valueSelector, { it }, rangeStart)


inline fun <T, G> List<T>.binByBigDecimal(binSize: BigDecimal,
                                          crossinline valueSelector: (T) -> BigDecimal,
                                          crossinline groupOp: (List<T>) -> G,
                                          rangeStart: BigDecimal? = null
): BinModel<G, BigDecimal> {

    val groupedByC = asSequence().groupBy(valueSelector)
    val minC = rangeStart?:groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val bins = mutableListOf<ClosedOpenRange<BigDecimal>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC

        while  (currentRangeEnd < maxC) {
            currentRangeEnd = currentRangeStart + binSize
            add(currentRangeStart until currentRangeEnd)
            currentRangeStart = currentRangeEnd
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

fun <T, C : Comparable<C>> BinModel<List<T>, C>.averageByBigDecimal(selector: (T) -> Double) =
        bins.map { Bin(it.range, it.value.map(selector).average()) }

fun <T, C : Comparable<C>> BinModel<List<T>, C>.sumByBigDecimal(selector: (T) -> Double) =
        bins.map { Bin(it.range, it.value.map(selector).sum()) }

fun <K> Map<K, List<BigDecimal>>.sum(): Map<K, BigDecimal> = entries.map { it.key to it.value.sum() }.toMap()
fun <K> Map<K, List<BigDecimal>>.average(): Map<K, BigDecimal> = entries.map { it.key to it.value.average() }.toMap()

fun <K, V> Map<K, List<V>>.sumByBigDecimal(selector: (V) -> BigDecimal): Map<K, BigDecimal> =
        entries.map { it.key to it.value.map(selector).sum() }.toMap()

fun <K, V> Map<K, List<V>>.averageByBigDecimal(selector: (V) -> BigDecimal): Map<K, BigDecimal> =
        entries.map { it.key to it.value.map(selector).average() }.toMap()