package com.tr4n.awesomecalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat
import kotlin.math.abs

class MainViewModel : ViewModel() {

    val buttons = MutableLiveData<List<Item>>().apply {
        value = ButtonType.values().map { type -> Item(type) }
    }

    val result = MutableLiveData<String>()
    val express = MutableLiveData<String>()

    private val itemList = mutableListOf<Item>()

    fun clear() {
        result.value = ""
        express.value = ""
        itemList.clear()
    }

    fun append(item: Item) {
        itemList.add(item)
        val currentExpression = itemList.getExpressionText()
        val expressionText = currentExpression + item.type.value
        val newExpression = expressionText.getExpressOrNull()
        currentExpression.getExpressOrNull()?.evaluate()?.shorten()?.let {
            result.value = it
            express.value = expressionText
        }
    }

    fun delete() {
        if (itemList.isEmpty()) return
        val expressionText = itemList.subList(0, itemList.size - 1).getExpressionText()
        val newExpression = expressionText.getExpressOrNull()
        if (newExpression != null) {
            result.value = newExpression.evaluate().shorten()
            express.value = expressionText
        }
    }

    fun calculate() {
        if (itemList.isEmpty()) return
        val expressionText = itemList.getExpressionText()
        val calculatedResult = expressionText.getResultExpressOrNull()
        result.value = calculatedResult?.shorten() ?: "Error expression"
        express.value = expressionText
    }


    private fun String.getExpressOrNull() = kotlin.runCatching {
        if (isNotBlank()) ExpressionBuilder(this).build().takeIf {
            it.validate().isValid
        } else null
    }.getOrNull()


    private fun String.getResultExpressOrNull() = kotlin.runCatching {
        ExpressionBuilder(this).build().evaluate()
    }.getOrNull()

    private fun Number.shorten(): String {
        val delta = abs(toDouble()) - abs(toLong())
        return if (delta == 0.0 || delta >= 0.00001) {
            val format = DecimalFormat("#,###,###.###########")
            format.format(this)
        } else toString()

    }

    private fun List<Item>.getExpressionText(): String {
        return joinToString(prefix = "", separator = "", postfix = "") { it.type.value }
    }
}
