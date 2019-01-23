package org.nield.kotlinstatistics


class ApacheSimpleRegression(val sr: org.apache.commons.math3.stat.regression.SimpleRegression) : SimpleRegression {
    override val n get() = sr.n
    override val intercept get() = sr.intercept
    override val slope get() = sr.slope
    override val sumSquaredErrors get() = sr.sumSquaredErrors
    override val totalSumSqaures get() = sr.totalSumSquares
    override val xSumSquares get() = sr.xSumSquares
    override val sumOfCrossProducts get() = sr.sumOfCrossProducts
    override val regressionSumSquares get() = sr.regressionSumSquares
    override val meanSquareError get() = sr.meanSquareError
    override val r get() = sr.r
    override val rSquare get() = sr.rSquare
    override val intereptStdErr get() = sr.interceptStdErr
    override val slopeStdErr get() = sr.slopeStdErr
    override val slopeConfidenceInterval get() = sr.slopeConfidenceInterval
    override val significance get() = sr.significance
    override fun predict(x: Double) = sr.predict(x)
}

//regression
inline fun <T> Iterable<T>.simpleRegression(
    crossinline xSelector: (T) -> Number,
    crossinline ySelector: (T) -> Number
) = asSequence().simpleRegression(xSelector, ySelector)

typealias ASR = org.apache.commons.math3.stat.regression.SimpleRegression

inline fun <T> Sequence<T>.simpleRegression(
    crossinline xSelector: (T) -> Number,
    crossinline ySelector: (T) -> Number
): SimpleRegression {
    val r = ASR()
    forEach { r.addData(xSelector(it).toDouble(), ySelector(it).toDouble()) }
    return ApacheSimpleRegression(r)
}

fun Sequence<Pair<Number, Number>>.simpleRegression() = simpleRegression({ it.first }, { it.second })
fun Iterable<Pair<Number, Number>>.simpleRegression() = simpleRegression({ it.first }, { it.second })