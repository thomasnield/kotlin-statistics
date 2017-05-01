package org.nield.kotlinstatistics

import org.apache.commons.math.fraction.Fraction

operator fun Fraction.plus(other: Fraction) = add(other)!!
operator fun Fraction.plus(other: Int) = add(other)!!

operator fun Fraction.minus(other: Fraction) = subtract(other)!!
operator fun Fraction.minus(other: Int) = subtract(other)!!

operator fun Fraction.times(other: Fraction) = multiply(other)!!
operator fun Fraction.times(other: Int) = multiply(other)!!

operator fun Fraction.div(other: Fraction) = divide(other)!!
operator fun Fraction.div(other: Int) = divide(other)!!