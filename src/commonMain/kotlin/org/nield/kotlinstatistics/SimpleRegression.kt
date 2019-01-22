package org.nield.kotlinstatistics


interface SimpleRegression {
    val n: Long
    val intercept: Double
    val slope: Double
    val sumSquaredErrors: Double
    val totalSumSqaures: Double
    val xSumSquares: Double
    val sumOfCrossProducts: Double
    val regressionSumSquares: Double
    val meanSquareError: Double
    val r: Double
    val rSquare: Double
    val intereptStdErr: Double
    val slopeStdErr: Double
    val slopeConfidenceInterval: Double
    val significance: Double

    fun predict(x: Double): Double
}


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