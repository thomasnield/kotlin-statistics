package org.nield.kotlinstatistics

import kotlin.math.exp
import kotlin.math.ln

/**
 * Returns a `NaiveBayesClassifier` that associates each set of `F` features from an item `T` with a category `C`.
 * New sets of features `F` can then be used to predict a category `C`.
 */
fun <T, F, C> Iterable<T>.toNaiveBayesClassifier(
    featuresSelector: ((T) -> Iterable<F>),
    categorySelector: ((T) -> C),
    observationLimit: Int = Int.MAX_VALUE,
    k1: Double = 0.5,
    k2: Double = k1 * 2.0
) = NaiveBayesClassifier<F, C>(observationLimit, k1, k2).also { nbc ->

    forEach { nbc.addObservation(categorySelector(it), featuresSelector(it)) }
}

/**
 * Returns a `NaiveBayesClassifier` that associates each set of `F` features from an item `T` with a category `C`.
 * New sets of features `F` can then be used to predict a category `C`.
 */
fun <T, F, C> Sequence<T>.toNaiveBayesClassifier(
    featuresSelector: ((T) -> Iterable<F>),
    categorySelector: ((T) -> C),
    observationLimit: Int = Int.MAX_VALUE,
    k1: Double = 0.5,
    k2: Double = k1 * 2.0
) = NaiveBayesClassifier<F, C>(observationLimit, k1, k2).also { nbc ->
    forEach { nbc.addObservation(categorySelector(it), featuresSelector(it)) }
}

/**
 * A `NaiveBayesClassifier` that associates each set of `F` features from an item `T` with a category `C`.
 * New sets of features `F` can then be used to predict a category `C`.
 */
class NaiveBayesClassifier<F, C>(
    val observationLimit: Int = Int.MAX_VALUE,
    val k1: Double = 0.5,
    val k2: Double = k1 * 2.0
) {

    private val _population = mutableListOf<BayesInput<F, C>>()
    private val modelStale = AtomicBoolean(false)

    @Volatile
    private var probabilities = mapOf<FeatureProbability.Key<F, C>, FeatureProbability<F, C>>()
    val population: List<BayesInput<F, C>> get() = _population

    /**
     * Adds an observation of features to a category
     */
    fun addObservation(category: C, features: Iterable<F>) {
        if (_population.size == observationLimit) {
            _population.removeAt(0)
        }
        _population += BayesInput(category, features.toSet())
        modelStale.set(true)
    }

    /**
     * Adds an observation of features to a category
     */
    fun addObservation(category: C, vararg features: F) = addObservation(category, features.asList())

    private fun rebuildModel() {

        probabilities = _population.asSequence().flatMap { it.features.asSequence() }
            .distinct()
            .flatMap { f ->
                _population.asSequence()
                    .map { it.category }
                    .distinct()
                    .map { c -> FeatureProbability.Key(f, c) }
            }.map { it to FeatureProbability(it.feature, it.category, this) }
            .toMap()

        modelStale.set(false)
    }

    /**
     * Returns the categories that have been captured by the model so far.
     */
    val categories get() = probabilities.keys.asSequence().map { it.category }.toSet()

    /**
     * Predicts a category `C` for a given set of `F` features
     */
    fun predict(vararg features: F) = predictWithProbability(features.toSet())?.category

    /**
     * Predicts a category `C` for a given set of `F` features
     */
    fun predict(features: Iterable<F>) = predictWithProbability(features)?.category

    /**
     * Predicts a category `C` for a given set of `F` features, but also returns the probability of that category being correct.
     */
    fun predictWithProbability(vararg features: F) = predictWithProbability(features.toSet())

    /**
     * Predicts a category `C` for a given set of `F` features, but also returns the probability of that category being correct.
     */
    fun predictWithProbability(features: Iterable<F>): CategoryProbability<C>? {
        if (modelStale.get()) rebuildModel()

        val f = features.toSet()

        return categories.asSequence()
            .filter { c -> population.any { it.category == c } && probabilities.values.any { it.feature in f } }
            .map { c ->
                val probIfCategory = probabilities.values.asSequence().filter { it.category == c }.map {
                    if (it.feature in f) {
                        ln(it.probability)
                    } else {
                        ln(1.0 - it.probability)
                    }
                }.sum().let(::exp)

                val probIfNotCategory = probabilities.values.asSequence().filter { it.category == c }.map {
                    if (it.feature in f) {
                        ln(it.notProbability)
                    } else {
                        ln(1.0 - it.notProbability)
                    }
                }.sum().let(::exp)

                CategoryProbability(category = c, probability = probIfCategory / (probIfCategory + probIfNotCategory))
            }.filter { it.probability >= .1 }
            .sortedByDescending { it.probability }
            .firstOrNull()

    }

    class FeatureProbability<F, C>(val feature: F, val category: C, nbc: NaiveBayesClassifier<F, C>) {

        val probability = (nbc.k1 + nbc.population.count { it.category == category && feature in it.features }) /
                (nbc.k2 + nbc.population.count { it.category == category })

        val notProbability = (nbc.k1 + nbc.population.count { it.category != category && feature in it.features }) /
                (nbc.k2 + nbc.population.count { it.category != category })

        data class Key<F, C>(val feature: F, val category: C)

        val key get() = Key(feature, category)

    }
}

data class BayesInput<F, C>(val category: C, val features: Set<F>)

data class CategoryProbability<C>(val category: C, val probability: Double)
