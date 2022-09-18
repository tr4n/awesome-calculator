package com.tr4n.awesomecalculator

import androidx.annotation.DrawableRes

enum class ButtonType(
    val value: String,
    val text: String,
    @DrawableRes val drawableRes: Int
) {
    CLEAR("CLEAR", "C", 0),
    LEFT_PARENTHESES("(", "(", 0),
    RIGHT_PARENTHESES(")", ")", 0),
    DIV("/", "÷", 0),
    SEVEN("7", "7", 0),
    EIGHT("8", "8", 0),
    NIGHT("9", "9", 0),
    MULTI("*", "x", R.drawable.ic_multi),
    FOUR("4", "4", 0),
    FIVE("5", "5", 0),
    SIX("6", "6", 0),
    SUB("-", "-", R.drawable.ic_sub),
    ONE("1", "1", 0),
    TWO("2", "2", 0),
    THREE("3", "3", 0),
    ADD("+", "+", R.drawable.ic_add),
    ZERO("0", "0", 0),
    DOT(".", ".", 0),
    BACK("", "DELETE", R.drawable.ic_back),
    EQUALS("=", "=", R.drawable.ic_equals);

    fun isOperatorOrOperand() = this in setOf(
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NIGHT, ZERO,
        ADD, SUB, MULTI, DIV, DOT, LEFT_PARENTHESES, RIGHT_PARENTHESES
    )

    fun isOperand() = this in setOf(
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NIGHT, ZERO,
        ADD, SUB, MULTI, DIV, DOT, LEFT_PARENTHESES
    )
}
