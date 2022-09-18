package com.tr4n.awesomecalculator.listener

import com.tr4n.awesomecalculator.base.recyclerview.DataBindingListener

interface OnSimpleItemClick<T> : DataBindingListener {

    fun onClick(item: T) {}

    fun onClick(position: Int) {}

    fun onClick(item: T, position: Int) {}
}
