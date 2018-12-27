import org.apache.commons.math3.stat.StatUtils
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics
import org.nield.kotlinstatistics.*
import org.nield.kotlinstatistics.range.Range
import org.nield.kotlinstatistics.range.until
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicBoolean

val FloatArray.descriptiveStatistics get(): Descriptives = DescriptiveStatistics().apply { forEach { addValue(it.toDouble()) } }.let(::ApacheDescriptives)

fun FloatArray.geometricMean() = asSequence().geometricMean()
fun FloatArray.median() = percentile(50.0)
fun FloatArray.percentile(percentile: Double) = asSequence().percentile(50.0)
fun FloatArray.variance() = asSequence().variance()
fun FloatArray.sumOfSquares() = asSequence().sumOfSquares()
fun FloatArray.standardDeviation() = asSequence().standardDeviation()
fun FloatArray.normalize() = StatUtils.normalize(asSequence().map { it.toDouble() }.toList().toDoubleArray())
val FloatArray.kurtosis get() = descriptiveStatistics.kurtosis
val FloatArray.skewness get() = descriptiveStatistics.skewness


// AGGREGATION OPERATORS



inline fun <T,K> Sequence<T>.sumBy(crossinline keySelector: (T) -> K, crossinline floatSelector: (T) -> Float) =
        groupApply(keySelector, floatSelector) { it.sum() }

inline fun <T,K> Iterable<T>.sumBy(crossinline keySelector: (T) -> K, crossinline floatSelector: (T) -> Float) =
        asSequence().sumBy(keySelector, floatSelector)

fun <K> Sequence<Pair<K,Float>>.sumBy() =
        groupApply({it.first}, {it.second}) { it.sum() }

fun <K> Iterable<Pair<K,Float>>.sumBy() = asSequence().sumBy()






inline fun <T,K> Sequence<T>.averageBy(crossinline keySelector: (T) -> K, crossinline floatSelector: (T) -> Float) =
        groupApply(keySelector, floatSelector) { it.average() }

inline fun <T,K> Iterable<T>.averageBy(crossinline keySelector: (T) -> K, crossinline floatSelector: (T) -> Float) =
        asSequence().averageBy(keySelector, floatSelector)

fun <K> Sequence<Pair<K,Float>>.averageBy() =
        groupApply({it.first}, {it.second}) { it.average() }

fun <K> Iterable<Pair<K,Float>>.averageBy() = asSequence().averageBy()






fun Sequence<Float>.floatRange() = toList().floatRange()
fun Iterable<Float>.floatRange() = toList().let { (it.min()?:throw Exception("At least one element must be present"))..(it.max()?:throw Exception("At least one element must be present")) }

inline fun <T,K> Sequence<T>.floatRangeBy(crossinline keySelector: (T) -> K, crossinline floatSelector: (T) -> Float) =
        groupApply(keySelector, floatSelector) { it.range() }

inline fun <T,K> Iterable<T>.floatRangeBy(crossinline keySelector: (T) -> K, crossinline floatSelector: (T) -> Float) =
        asSequence().rangeBy(keySelector, floatSelector)



// Bin Operators

inline fun <T> Sequence<T>.binByFloat(binSize: Float,
                                      crossinline valueSelector: (T) -> Float,
                                      rangeStart: Float? = null
): BinModel<List<T>, Float> = toList().binByFloat(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Sequence<T>.binByFloat(binSize: Float,
                                         crossinline valueSelector: (T) -> Float,
                                         crossinline groupOp: (List<T>) -> G,
                                         rangeStart: Float? = null
): BinModel<G, Float> = toList().binByFloat(binSize, valueSelector, groupOp, rangeStart)


inline fun <T> Iterable<T>.binByFloat(binSize: Float,
                                  crossinline valueSelector: (T) -> Float,
                                  rangeStart: Float? = null
): BinModel<List<T>, Float> = toList().binByFloat(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> Iterable<T>.binByFloat(binSize: Float,
                                     crossinline valueSelector: (T) -> Float,
                                     crossinline groupOp: (List<T>) -> G,
                                     rangeStart: Float? = null
): BinModel<G, Float> = toList().binByFloat(binSize, valueSelector, groupOp, rangeStart)


inline fun <T> List<T>.binByFloat(binSize: Float,
                                   crossinline valueSelector: (T) -> Float,
                                   rangeStart: Float? = null
): BinModel<List<T>, Float> = binByFloat(binSize, valueSelector, { it }, rangeStart)

inline fun <T, G> List<T>.binByFloat(binSize: Float,
                                     crossinline valueSelector: (T) -> Float,
                                     crossinline groupOp: (List<T>) -> G,
                                     rangeStart: Float? = null
): BinModel<G, Float> {

    val groupedByC = asSequence().groupBy { BigDecimal.valueOf(valueSelector(it).toDouble()) }
    val minC = rangeStart?.toDouble()?.let(BigDecimal::valueOf) ?:groupedByC.keys.min()!!
    val maxC = groupedByC.keys.max()!!

    val bins = mutableListOf<Range<Float>>().apply {
        var currentRangeStart = minC
        var currentRangeEnd = minC
        val isFirst = AtomicBoolean(true)
        val binSizeBigDecimal = BigDecimal.valueOf(binSize.toDouble())
        while  (currentRangeEnd < maxC) {
            currentRangeEnd = currentRangeStart + binSizeBigDecimal
            add(currentRangeStart.toFloat() until currentRangeEnd.toFloat())
            currentRangeStart = currentRangeEnd
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