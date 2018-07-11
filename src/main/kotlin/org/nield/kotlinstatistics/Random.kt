package org.nield.kotlinstatistics

import java.util.concurrent.ThreadLocalRandom


/**
 * Samples a single random element `T` from a `List<T>`, and throws an error if no elements exist
 */
fun <T> List<T>.randomFirst() = randomFirstOrNull()?: throw Exception("No elements found!")

/**
 * Samples a single random element `T` from a `List<T>`, and returns `null` if no elements exist
 */
fun <T> List<T>.randomFirstOrNull(): T? {
    if (size == 0) return null

    val random = ThreadLocalRandom.current().nextInt(0,size)

    return this[random]
}


/**
 * Samples a single random element `T` from a `Sequence<T>`, and throws an error if no elements exist
 */
fun <T> Sequence<T>.randomFirst() = toList().randomFirst()

/**
 * Samples a single random element `T` from a `Sequence<T>`, and returns `null` if no elements exist
 */
fun <T> Sequence<T>.randomFirstOrNull() = toList().randomFirstOrNull()


/**
 * Samples a single random element `T` from a `Sequence<T>`, and throws an error if no elements exist
 */
fun <T> Iterable<T>.randomFirst() = toList().randomFirst()

/**
 * Samples a single random element `T` from an `Iterable<T>`, and returns `null` if no elements exist
 */
fun <T> Iterable<T>.randomFirstOrNull() = toList().randomFirstOrNull()


/**
 * Samples a single random element `T` from an `Iterable<T>`, and returns `null` if no elements exist
 */
fun <T> List<T>.randomDistinct(sampleSize: Int): List<T> {

    val cappedSampleSize = if (sampleSize > size) size else sampleSize

    return (0..Int.MAX_VALUE).asSequence().map {
        ThreadLocalRandom.current().nextInt(0,size)
    }.distinct()
     .take(cappedSampleSize)
     .map { this[it] }
     .toList()
}

/**
 * Samples a single random element `T` from an `Iterable<T>`, and returns `null` if no elements exist
 */
fun <T> Sequence<T>.randomDistinct(sampleSize: Int) = toList().randomDistinct(sampleSize)


/**
 * Simulates a weighted TRUE/FALSE coin flip, with a percentage of probability towards TRUE
 */
fun weightedCoinFlip(trueProbability: Double) =
        ThreadLocalRandom.current().nextDouble(0.0,1.0) <= trueProbability


//TODO Weighted Dice

class WeightedDice<T>(distribution: Map<T,Double>) {

}