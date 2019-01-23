package org.nield.kotlinstatistics

import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics


actual object ArrayStat {
    actual fun geometricMean(array: DoubleArray): Double = StatUtils.geometricMean(array)

    actual fun percentile(array: DoubleArray, percentile: Double): Double = StatUtils.percentile(array, percentile)

    actual fun variance(array: DoubleArray): Double = StatUtils.variance(array)

    actual fun sumSq(array: DoubleArray): Double = StatUtils.sumSq(array)

    actual fun normalize(array: DoubleArray): DoubleArray = StatUtils.normalize(array)

}

internal actual fun Sequence<Double>.descriptiveStatistics(): Descriptives = ApacheDescriptives(
    DescriptiveStatistics().also { stat -> this.forEach { stat.addValue(it) } }
)