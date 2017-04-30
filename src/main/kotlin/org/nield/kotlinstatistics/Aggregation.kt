package org.nield.kotlinstatistics


inline fun <T,K,R> Sequence<T>.groupApply(crossinline keySelector: (T) -> K, crossinline aggregation: (Iterable<T>) -> R): Map<K, R> {
    val map = mutableMapOf<K,MutableList<T>>()

    for (item in this) {
        val key = keySelector(item)
        val list = map.computeIfAbsent(key) { mutableListOf() }
        list += item
    }
    val aggregatedMap = mutableMapOf<K,R>()

    for ((key, value) in map) {
        aggregatedMap.put(key, aggregation(value))
    }
    return aggregatedMap
}

inline fun <T,V,K,R> Sequence<T>.groupApply(crossinline keySelector: (T) -> K, crossinline valueMapper: (T) -> V, crossinline aggregation: (Iterable<V>) -> R): Map<K, R> {
    val map = mutableMapOf<K, MutableList<V>>()

    for (item in this) {
        val key = keySelector(item)
        val list = map.computeIfAbsent(key) { mutableListOf() }
        list += valueMapper(item)
    }
    val aggregatedMap = mutableMapOf<K, R>()

    for (entry in map.entries) {
        aggregatedMap.put(entry.key, aggregation(entry.value))
    }
    return aggregatedMap
}