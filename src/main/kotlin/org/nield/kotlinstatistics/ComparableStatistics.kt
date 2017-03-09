package org.nield.kotlinstatistics

fun <T> Sequence<T>.mode() = groupBy { it }
        .entries.asSequence()
        .map { it.key to it.value.count() }
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
