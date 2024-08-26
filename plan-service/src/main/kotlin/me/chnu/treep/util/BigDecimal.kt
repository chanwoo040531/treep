package me.chnu.treep.util

import java.math.BigDecimal
import java.math.MathContext

/**
 * Converts this Double to a BigDecimal.
 *
 * Note: Using a Double as a source for BigDecimal can introduce precision issues.
 *
 * @return a BigDecimal representation of this Double.
 */
fun Double.d(): BigDecimal {
    return BigDecimal(this, MathContext.DECIMAL64)
}

/**
 * Converts this Int to a BigDecimal.
 *
 * @return a BigDecimal representation of this Int.
 */
fun Int.d(): BigDecimal {
    return BigDecimal(this)
}

/**
 * Converts this Long to a BigDecimal.
 *
 * @return a BigDecimal representation of this Long.
 */
fun Long.d(): BigDecimal {
    return BigDecimal(this)
}