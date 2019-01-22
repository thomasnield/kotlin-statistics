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