package org.nield.kotlinstatistics

import org.apache.commons.math3.ml.clustering.Clusterable
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer

fun Collection<Pair<Double,Double>>.cluster(k: Int, maxIterations: Int) = cluster(k, maxIterations, {it.first}, {it.second})
fun Sequence<Pair<Double,Double>>.cluster(k: Int, maxIterations: Int) = toList().cluster(k, maxIterations, {it.first}, {it.second})

inline fun <T> Collection<T>.cluster(k: Int, maxIterations: Int, crossinline xSelector: (T) -> Double, crossinline ySelector: (T) -> Double) =
        asSequence().map { ClusterInput(it, doubleArrayOf(xSelector(it), ySelector(it))) }
                .toList()
                .let {
                    KMeansPlusPlusClusterer<ClusterInput<T>>(k,maxIterations)
                        .cluster(it)
                        .map {
                            Centroid((it.center).point.let { DoublePoint(it[0],it[1])}, it.points.map { it.item })
                        }
                }
inline fun <T> Sequence<T>.cluster(k: Int, maxIterations: Int, crossinline xSelector: (T) -> Double, crossinline ySelector: (T) -> Double) =
        toList().cluster(k,maxIterations,xSelector,ySelector)

class ClusterInput<out T>(val item: T, val location: DoubleArray): Clusterable {
    override fun getPoint() = location
}

data class DoublePoint(val x: Double, val y: Double)
data class Centroid<out T>(val center: DoublePoint, val points: List<T>)