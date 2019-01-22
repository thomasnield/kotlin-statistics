package org.nield.kotlinstatistics

import java.util.concurrent.ThreadLocalRandom


/**
 * Samples a single random element `T` from a `List<T>`, and throws an error if no elements exist
 */
fun <T> List<T>.randomFirst() = randomFirstOrNull() ?: throw Exception("No elements found!")

/**
 * Samples a single random element `T` from a `List<T>`, and returns `null` if no elements exist
 */
fun <T> List<T>.randomFirstOrNull(): T? {
    if (size == 0) return null

    val random = ThreadLocalRandom.current().nextInt(0, size)

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
 * Samples `n` distinct random elements `T` from a `Sequence<T>`
 */
fun <T> Sequence<T>.randomDistinct(sampleSize: Int) = toList().randomDistinct(sampleSize)

/**
 * Samples `n` distinct random elements `T` from an `Iterable<T>`
 */
fun <T> List<T>.randomDistinct(sampleSize: Int): List<T> {

    val cappedSampleSize = if (sampleSize > size) size else sampleSize

    return (0..Int.MAX_VALUE).asSequence().map {
        ThreadLocalRandom.current().nextInt(0, size)
    }.distinct()
        .take(cappedSampleSize)
        .map { this[it] }
        .toList()
}


/**
 * Samples `n` random elements `T` from a `Sequence<T>`
 */
fun <T> Sequence<T>.random(sampleSize: Int) = toList().random(sampleSize)

/**
 * Samples `n` random elements `T` from an `Iterable<T>`
 */
fun <T> List<T>.random(sampleSize: Int): List<T> {

    val cappedSampleSize = if (sampleSize > size) size else sampleSize

    return (0..Int.MAX_VALUE).asSequence().map {
        ThreadLocalRandom.current().nextInt(0, size)
    }.take(cappedSampleSize)
        .map { this[it] }
        .toList()
}


/**
 * Simulates a weighted TRUE/FALSE coin flip, with a percentage of probability towards TRUE
 *
 * In other words, this is a Probability Density Function (PDF) for discrete TRUE/FALSE values
 */
class WeightedCoin(val trueProbability: Double) {
    fun flip() = ThreadLocalRandom.current().nextDouble(0.0, 1.0) <= trueProbability
}

/**
 * Simulates a weighted TRUE/FALSE coin flip, with a percentage of probability towards TRUE
 *
 * In other words, this is a Probability Density Function (PDF) for discrete TRUE/FALSE values
 */
fun weightedCoinFlip(trueProbability: Double) =
    ThreadLocalRandom.current().nextDouble(0.0, 1.0) <= trueProbability


/**
 *  Assigns a probabilty to each distinct `T` item, and randomly selects `T` values given those probabilities.
 *
 *  In other words, this is a Probability Density Function (PDF) for discrete `T` values
 */
class WeightedDice<T>(val probabilities: Map<T, Double>) {

    constructor(vararg values: Pair<T, Double>) : this(
        values.toMap()
    )

    private val sum = probabilities.values.sum()

    val rangedDistribution = probabilities.let {

        var binStart = 0.0

        it.asSequence().sortedBy { it.value }
            .map { it.key to OpenDoubleRange(binStart, it.value + binStart) }
            .onEach { binStart = it.second.endExclusive }
            .toMap()
    }

    /**
     * Randomly selects a `T` value with probability
     */
    fun roll() = ThreadLocalRandom.current().nextDouble(0.0, sum).let {
        rangedDistribution.asIterable().first { rng -> it in rng.value }.key
    }
}
