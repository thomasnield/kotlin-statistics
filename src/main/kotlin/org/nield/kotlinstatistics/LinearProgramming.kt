package org.nield.kotlinstatistics

import org.apache.commons.math3.linear.ArrayRealVector
import org.apache.commons.math3.linear.RealVector
import org.apache.commons.math3.optim.MaxIter
import org.apache.commons.math3.optim.linear.*
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType


class Optimizer(vector: RealVector, constant: Double = 0.0) {

    private val f = LinearObjectiveFunction(vector, constant)
    private val constraints = mutableListOf<LinearConstraint>()

    fun constraint(coefficients: DoubleArray, relationship: Relationship, value: Double) {
        constraints += LinearConstraint(coefficients, relationship, value)
    }
    fun constraint(vector: RealVector, relationship: Relationship, value: Double) {
        constraints += LinearConstraint(vector, relationship, value)
    }
    fun constraint(linearConstraint: LinearConstraint) {
        constraints += linearConstraint
    }
    fun maximize(negativeAllowed: Boolean = false, maxIterations: Int = Integer.MAX_VALUE) =
            SimplexSolver().optimize(MaxIter(maxIterations), f, LinearConstraintSet(constraints), GoalType.MAXIMIZE, NonNegativeConstraint(!negativeAllowed))

    fun minimize(negativeAllowed: Boolean = false, maxIterations: Int = Integer.MAX_VALUE) =
            SimplexSolver().optimize(MaxIter(maxIterations), f, LinearConstraintSet(constraints), GoalType.MINIMIZE, NonNegativeConstraint(!negativeAllowed))
}


fun optimizer(coefficients: DoubleArray, constant: Double = 0.0, op: Optimizer.() -> Unit) =
        Optimizer(ArrayRealVector(coefficients), constant).apply(op)

fun optimizer(coefficients: RealVector, constant: Double = 0.0, op: Optimizer.() -> Unit) =
        Optimizer(coefficients, constant).apply(op)

fun v(vararg vectorValues: Double) = ArrayRealVector(vectorValues)

infix fun RealVector.GEQ(value: Double) = LinearConstraint(this,Relationship.GEQ,value)
infix fun RealVector.LEQ(value: Double) = LinearConstraint(this,Relationship.LEQ,value)
infix fun RealVector.EQ(value: Double) = LinearConstraint(this,Relationship.EQ,value)
