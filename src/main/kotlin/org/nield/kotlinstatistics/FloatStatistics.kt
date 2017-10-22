import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.nield.kotlinstatistics.*
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicBoolean

val FloatArray.descriptiveStatistics get(): Descriptives = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)

fun Iterable<Float>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Float>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Float>.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun FloatArray.geometricMean() = StatUtils.geometricMean(asSequence().map { it.toDouble() }.toList().toDoubleArray())

fun Iterable<Float>.median() = percentile(50.0)
fun Sequence<Float>.median() = percentile(50.0)
fun Array<out Float>.median() = percentile(50.0)
fun FloatArray.median() = percentile(50.0)

fun Iterable<Float>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)
fun Sequence<Float>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)
fun Array<out Float>.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)
fun FloatArray.percentile(percentile: Double) = StatUtils.percentile(asSequence().map { it.toDouble() }.toList().toDoubleArray(), percentile)

fun Iterable<Float>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Float>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Float>.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun FloatArray.variance() = StatUtils.variance(asSequence().map { it.toDouble() }.toList().toDoubleArray())

fun Iterable<Float>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Float>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Float>.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun FloatArray.sumOfSquares() = StatUtils.sumSq(asSequence().map { it.toDouble() }.toList().toDoubleArray())

fun Iterable<Float>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Sequence<Float>.standardDeviation() = descriptiveStatistics.standardDeviation
fun Array<out Float>.standardDeviation() = descriptiveStatistics.standardDeviation
fun FloatArray.standardDeviation() = descriptiveStatistics.standardDeviation

fun Iterable<Float>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Sequence<Float>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun Array<out Float>.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
fun FloatArray.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())

val Iterable<Float>.kurtosis get() = descriptiveStatistics.kurtosis
val Sequence<Float>.kurtosis get() = descriptiveStatistics.kurtosis
val Array<out Float>.kurtosis get() = descriptiveStatistics.kurtosis
val FloatArray.kurtosis get() = descriptiveStatistics.kurtosis

val Iterable<Float>.skewness get() = descriptiveStatistics.skewness
val Sequence<Float>.skewness get() = descriptiveStatistics.skewness
val Array<out Float>.skewness get() = descriptiveStatistics.skewness
val FloatArray.skewness get() = descriptiveStatistics.skewness


// AGGREGATION OPERATORS



inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        groupApply(keySelector, floatMapper) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        asSequence().sumBy(keySelector, floatMapper)

fun <K> Sequence<Pair<K,Float>>.sumBy() =
        groupApply({it.first}, {it.second}) { it.sum() }

fun <K> Iterable<Pair<K,Float>>.sumBy() = asSequence().sumBy()








inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        groupApply(keySelector, floatMapper) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        asSequence().averageBy(keySelector, floatMapper)

fun <K> Sequence<Pair<K,Float>>.averageBy() =
        groupApply({it.first}, {it.second}) { it.average() }

fun <K> Iterable<Pair<K,Float>>.averageBy() = asSequence().averageBy()








inline fun <T,K> Sequence<T>.minBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        groupApply(keySelector, floatMapper) { it.min() }

inline fun <T,K> Iterable<T>.minBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        asSequence().minBy(keySelector, floatMapper)

fun <K> Sequence<Pair<K,Float>>.minBy() =
        groupApply({it.first}, {it.second}) { it.min() }

fun <K> Iterable<Pair<K,Float>>.minBy() = asSequence().minBy()






inline fun <T,K> Sequence<T>.maxBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        groupApply(keySelector, floatMapper) { it.max() }

inline fun <T,K> Iterable<T>.maxBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        asSequence().maxBy(keySelector, floatMapper)

fun <K> Sequence<Pair<K,Float>>.maxBy() =
        groupApply({it.first}, {it.second}) { it.max() }

fun <K> Iterable<Pair<K,Float>>.maxBy() = asSequence().maxBy()








inline fun <T,K> Sequence<T>.medianBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        groupApply(keySelector, floatMapper) { it.median() }

inline fun <T,K> Iterable<T>.medianBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        asSequence().medianBy(keySelector, floatMapper)


fun <K> Sequence<Pair<K,Float>>.medianBy() =
        groupApply({it.first}, {it.second}) { it.median() }

fun <K> Iterable<Pair<K,Float>>.medianBy() = asSequence().medianBy()







inline fun <T,K> Sequence<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        groupApply(keySelector, floatMapper) { it.variance() }

inline fun <T,K> Iterable<T>.varianceBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        asSequence().varianceBy(keySelector, floatMapper)

fun <K> Sequence<Pair<K,Float>>.varianceBy() =
        groupApply({it.first}, {it.second}) { it.variance() }

fun <K> Iterable<Pair<K,Float>>.varianceBy() = asSequence().varianceBy()








inline fun <T,K> Sequence<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        groupApply(keySelector, floatMapper) { it.standardDeviation() }

inline fun <T,K> Iterable<T>.standardDeviationBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        asSequence().standardDeviationBy(keySelector, floatMapper)

fun <K> Sequence<Pair<K,Float>>.standardDeviationBy() =
        groupApply({it.first}, {it.second}) { it.standardDeviation() }

fun <K> Iterable<Pair<K,Float>>.standardDeviationBy() = asSequence().standardDeviationBy()








inline fun <T,K> Sequence<T>.geometricMeanBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        groupApply(keySelector, floatMapper) { it.geometricMean() }

inline fun <T,K> Iterable<T>.geometricMeanBy(crossinline keySelector: (T) -> K, crossinline floatMapper: (T) -> Float) =
        asSequence().geometricMeanBy(keySelector, floatMapper)

fun <K> Sequence<Pair<K,Float>>.geometricMeanBy() =
        groupApply({it.first}, {it.second}) { it.geometricMean() }

fun <K> Iterable<Pair<K,Float>>.geometricMeanBy() = asSequence().geometricMeanBy()






inline fun <T> Sequence<T>.simpleRegression(crossinline xSelector: (T) -> Float, crossinline ySelector: (T) -> Float) =
        map { xSelector(it).toDouble() to ySelector(it).toDouble() }
                .simpleRegression()

fun Sequence<Pair<Float, Float>>.simpleRegression() = simpleRegression({it.first},{it.second})



// Bin Operators

inline fun <T> Sequence<T>.binByFloat(binSize: Float,
                                      gapSize: Float,
                                      crossinline valueMapper: (T) -> Float,
                                      rangeStart: Float? = null
): BinModel<List<T>, Float> = toList().binByFloat(binSize, gapSize, valueMapper, { it }, rangeStart)

inline fun <T, G> Sequence<T>.binByFloat(binSize: Float,
                                         gapSize: Float,
                                         crossinline valueMapper: (T) -> Float,
                                         crossinline groupOp: (List<T>) -> G,
                                         rangeStart: Float? = null
): BinModel<G, Float> = toList().binByFloat(binSize, gapSize, valueMapper, groupOp, rangeStart)


inline fun <T> Iterable<T>.binByFloat(binSize: Float,
                                  gapSize: Float,
                                  crossinline valueMapper: (T) -> Float,
                                  rangeStart: Float? = null
): BinModel<List<T>, Float> = toList().binByFloat(binSize, gapSize, valueMapper, { it }, rangeStart)

inline fun <T, G> Iterable<T>.binByFloat(binSize: Float,
                                     gapSize: Float,
                                     crossinline valueMapper: (T) -> Float,
                                     crossinline groupOp: (List<T>) -> G,
                                     rangeStart: Float? = null
): BinModel<G, Float> = toList().binByFloat(binSize, gapSize, valueMapper, groupOp, rangeStart)


inline fun <T> List<T>.binByFloat(binSize: Float,
                                   gapSize: Float,
                                   crossinline valueMapper: (T) -> Float,
                                   rangeStart: Float? = null
): BinModel<List<T>, Float> = binByFloat(binSize, gapSize, valueMapper, { it }, rangeStart)

inline fun <T, G> List<T>.binByFloat(binSize: Float,
                                     gapSize: Float,
                                     crossinline valueMapper: (T) -> Float,
                                     crossinline groupOp: (List<T>) -> G,
                                     rangeStart: Float? = null
): BinModel<G, Float> {

    val groupedByC = asSequence().groupBy { BigDecimal.valueOf(valueMapper(it).toDouble()) }
    val minC = rangeStart?.toDouble()?.let(BigDecimal::valueOf) ?:groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val bins = mutableListOf<ClosedFloatingPointRange<Float>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC
        val isFirst = AtomicBoolean(true)
        val binSizeBigDecimal = BigDecimal.valueOf(binSize.toDouble())
        val gapSizeBigDecimal = BigDecimal.valueOf(gapSize.toDouble())
        while  (currentRangeEnd < maxC) {
            currentRangeEnd = currentRangeStart + binSizeBigDecimal - if (isFirst.getAndSet(false)) BigDecimal.ZERO else gapSizeBigDecimal
            add(currentRangeStart.toFloat()..currentRangeEnd.toFloat())
            currentRangeStart = currentRangeEnd + gapSizeBigDecimal
        }
    }

    return bins.asSequence()
            .map { it to mutableListOf<T>() }
            .map { binWithList ->
                groupedByC.entries.asSequence()
                        .filter { it.key.toFloat() in binWithList.first }
                        .forEach { binWithList.second.addAll(it.value) }
                binWithList
            }.map { Bin(it.first, groupOp(it.second)) }
            .toList()
            .let(::BinModel)
}