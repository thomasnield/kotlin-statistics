package org.nield.kotlinstatistics

class OpenDoubleRange(
        start: Double,
        endExclusive: Double
) {
    private val _start = start
    private val _endExclusive = endExclusive
    val start: Double get() = _start
    val endExclusive: Double get() = _endExclusive

    fun lessThanOrEquals(a: Double, b: Double): Boolean = a <= b

    operator fun contains(value: Double): Boolean = value >= _start && value < _endExclusive
    fun isEmpty(): Boolean = !(_start <= _endExclusive)

    override fun equals(other: Any?): Boolean {
        return other is OpenDoubleRange && (isEmpty() && other.isEmpty() ||
                _start == other._start && _endExclusive == other._endExclusive)
    }

    override fun hashCode(): Int {
        return if (isEmpty()) -1 else 31 * _start.hashCode() + _endExclusive.hashCode()
    }

    override fun toString(): String = "$_start..<$_endExclusive"
}

infix fun Double.openRange(double: Double) = OpenDoubleRange(this, double)