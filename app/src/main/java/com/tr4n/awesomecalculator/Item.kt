package com.tr4n.awesomecalculator

import androidx.annotation.ColorRes

data class Item(val type: ButtonType) {

    val hasDrawable get() = type.drawableRes != 0

    val text get() = type.text

    val drawable get() = type.drawableRes

    val textColor: Int
        @ColorRes get() {
            Long.MAX_VALUE
            return if (type in setOf(
                    ButtonType.CLEAR, ButtonType.LEFT_PARENTHESES, ButtonType.RIGHT_PARENTHESES, ButtonType.DIV,
                    ButtonType.MULTI, ButtonType.SUB, ButtonType.ADD, ButtonType.EQUALS,
                    ButtonType.BACK,
                )
            ) R.color.text_primary_color else R.color.text_secondary_color
        }
}
