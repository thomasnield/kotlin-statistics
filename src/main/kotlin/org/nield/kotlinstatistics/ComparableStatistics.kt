package org.nield.kotlinstatistics

fun <T> Sequence<T>.mode() = countBy()
        .sortedByDescending { it.second }
        .toList().let { list ->
            list.asSequence()
                    .takeWhile { list[0].second == it.second }
                    .map { it.first }
        }

fun <T> Iterable<T>.mode() = asSequence().mode()
fun <T> Array<out T>.mode() = asIterable().mode()
fun ByteArray.mode() = asIterable().mode()
fun ShortArray.mode() = asIterable().mode()
fun IntArray.mode() = asIterable().mode()
fun LongArray.mode() = asIterable().mode()
fun FloatArray.mode() = asIterable().mode()
fun DoubleArray.mode() = asIterable().mode()

/**
 * Emits each distinct value with the number counts it appeared
 */
fun <T> Sequence<T>.countBy() =
        groupBy { it }
        .entries.asSequence()
        .map { it.key to it.value.count() }


fun <T> Iterable<T>.countBy() = asSequence().countBy()

fun <T,R> Sequence<T>.countBy(selector: (T) -> R) =
        groupBy { selector(it) }
                .entries.asSequence()
                .map { it.key to it.value.count() }

fun <T,R> Iterable<T>.countBy(selector: (T) -> R) = asSequence().countBy(selector)