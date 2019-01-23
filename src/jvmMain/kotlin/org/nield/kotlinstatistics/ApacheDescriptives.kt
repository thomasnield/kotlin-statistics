package org.nield.kotlinstatistics

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

class ApacheDescriptives(private val ds: DescriptiveStatistics) : Descriptives {

    override val windowSize by lazy { ds.windowSize }
    override val mean by lazy { ds.mean }
    override val geometricMean by lazy { ds.geometricMean }
    override val variance by lazy { ds.variance }
    override val standardDeviation by lazy { ds.standardDeviation }
    override val skewness by lazy { ds.skewness }
    override val kurtosis by lazy { ds.kurtosis }
    override val max by lazy { ds.max }
    override val min by lazy { ds.min }
    override val size by lazy { ds.n }
    override val sum by lazy { ds.sum }
    override val sumSquared by lazy { ds.sumsq }
    override val values by lazy { ds.values }
    override fun percentile(percentile: Double) = ds.getPercentile(percentile)
    override operator fun get(index: Int) = ds.getElement(index)
}