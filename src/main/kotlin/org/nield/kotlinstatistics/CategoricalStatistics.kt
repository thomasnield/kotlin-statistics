package org.nield.kotlinstatistics

fun <T> Sequence<T>.mode() = countBy()
        .entries
        .asSequence()
        .sortedByDescending { it.value }
        .toList().let { list ->
            list.asSequence()
                    .takeWhile { list[0].value == it.value }
                    .map { it.key }
        }

fun <T> Iterable<T>.mode() = asSequence().mode()
fun <T> Array<out T>.mode() = asIterable().mode()
fun ByteArray.mode() = asIterable().mode()
fun ShortArray.mode() = asIterable().mode()
fun IntArray.mode() = asIterable().mode()
fun LongArray.mode() = asIterable().mode()
fun FloatArray.mode() = asIterable().mode()
fun DoubleArray.mode() = asIterable().mode()

//AGGREGATION OPERATORS

/**
 * Groups each distinct value with the number counts it appeared
 */
fun <T> Sequence<T>.countBy() = groupApply({ it }, {it.count()})
/**
 * Groups each distinct value with the number counts it appeared
 */
fun <T> Iterable<T>.countBy() = asSequence().countBy()
/**
 * Groups each distinct key with the number counts it appeared
 */
inline fun <T,K> Sequence<T>.countBy(crossinline keySelector: (T) -> K) = groupApply(keySelector) { it.count() }
/**
 * Groups each distinct key with the number counts it appeared
 */
inline fun <T,K> Iterable<T>.countBy(crossinline keySelector: (T) -> K) = asSequence().countBy(keySelector)

