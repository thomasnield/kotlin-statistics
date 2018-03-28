package org.nield.kotlinstatistics

fun <T,F,C> toNaiveBayesClassifier(
        featuresSelector: ((T) -> Iterable<F>),
        categorySelector: ((T) -> C)
) = Unit


class NaiveBayesClassifier<F,C>(val observationLimit: Int? = null) {

    private val _population = mutableListOf<BayesInput<F,C>>()

    val population get() = _population.toList()

    fun addInput(category: C, vararg features: F) {
        if (_population.size == observationLimit) {
            _population.removeAt(0)
        }
        _population += BayesInput(category, features.toSet())
    }

    fun predictCategory(vararg features: F) {

    }
}

data class BayesInput<F,C>(val category: C, val features: Set<F>)