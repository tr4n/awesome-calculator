package com.tr4n.awesomecalculator

import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.*
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tr4n.awesomecalculator.base.recyclerview.BaseDiffUtilItemCallback
import com.tr4n.awesomecalculator.base.recyclerview.BindAbleAdapter
import com.tr4n.awesomecalculator.base.recyclerview.DataBindingListener
import com.tr4n.awesomecalculator.base.recyclerview.SimpleBindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@Suppress("UNCHECKED_CAST", "UNCHECKED_CAST")
@BindingAdapter(value = ["adapter", "items"], requireAll = false)
fun <T> RecyclerView.setAdapterData(
    itemAdapter: RecyclerView.Adapter<*>? = null,
    items: List<T>?
) {
    itemAdapter?.let {
        adapter = it
    }
    (adapter as? BindAbleAdapter<T>)?.setItems(items ?: emptyList())
}

@BindingAdapter(
    value = ["simpleData", "layoutId", "listener", "diffUtil"],
    requireAll = false
)
fun <T> RecyclerView.setSimpleAdapter(
    data: List<T>?,
    @LayoutRes layoutId: Int,
    listener: DataBindingListener? = null,
    diffUtilItemCallback: DiffUtil.ItemCallback<T>? = null
) {
    val diffUtil = diffUtilItemCallback ?: BaseDiffUtilItemCallback()
    if (adapter as? SimpleBindingAdapter<*> == null) {
        adapter = SimpleBindingAdapter(layoutId, diffUtil)
    }

    (adapter as? SimpleBindingAdapter<T>)?.apply {
        setItems(data ?: emptyList())
        this.listener = listener
    }
}

@BindingAdapter("gridSpanCount")
fun RecyclerView.setGridLayoutManagerSpanCount(spanCount: Int?) {
    layoutManager = GridLayoutManager(context, spanCount ?: 1)
}

@BindingAdapter(value = ["textResource", "date", "format"], requireAll = false)
fun TextView.setTextResource(
    @StringRes
    resId: Int? = null,
    date: Date? = null,
    format: String? = null
) {

    resId?.let {
        text = context.getString(resId)
    }
    date?.let {
        val pattern = format ?: "MM/dd/yyyy HH:mm"
        text = SimpleDateFormat(pattern, Locale.getDefault()).format(it)
    }
}

@BindingAdapter("textColorRes")
fun TextView.setTextColorRes(@ColorRes colorRes: Int?) {
    colorRes?.runCatching {
        this@setTextColorRes.setTextColor(getColor(this@setTextColorRes.context, colorRes))
    }
}

@BindingAdapter("bgColor")
fun View.bindBackgroundColor(@ColorInt color: Int) {
    background = null
    setBackgroundColor(Color.BLACK)
    setBackgroundColor(color)
}

@BindingAdapter("isVisible")
fun View.setVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("isInvisible")
fun View.setInvisible(isInvisible: Boolean) {
    this.isInvisible = isInvisible
}

@BindingAdapter(value = ["widthPercent", "heightPercent"], requireAll = false)
fun View.setLayoutPercent(widthPercent: Number?, heightPercent: Number?) {
    updateLayoutParams<ViewGroup.LayoutParams> {
        widthPercent?.let {
            width = (Constant.screenWidth * it.toDouble()).toInt()
        }
        heightPercent?.let {
            height = (Constant.screenHeight * it.toDouble()).toInt()
        }
    }
}

@BindingAdapter(value = ["ratioBaseWidth", "ratioBaseHeight"], requireAll = false)
fun View.setRatio(ratioBaseWidth: Number?, ratioBaseHeight: Number?) {
    updateLayoutParams<ViewGroup.LayoutParams> {
        when {
            ratioBaseWidth != null -> height = (width * ratioBaseWidth.toDouble()).toInt()
            ratioBaseHeight != null -> width = (height * ratioBaseHeight.toDouble()).toInt()
        }
    }
}

@BindingAdapter(
    value = ["startDrawable", "topDrawable", "endDrawable", "bottomDrawable"],
    requireAll = false
)
fun TextView.setDrawables(
    @DrawableRes startRes: Int,
    @DrawableRes topRes: Int,
    @DrawableRes endRes: Int,
    @DrawableRes bottomRes: Int
) {
    setCompoundDrawablesWithIntrinsicBounds(
        startRes,
        topRes,
        endRes,
        bottomRes
    )
}

@BindingAdapter("secondCount")
fun TextView.setSecondCount(count: Int) {
    val minutes = count.div(60)
    val seconds = count.rem(60)
    text = String.format("%d:%02d", minutes, seconds)
}

@BindingAdapter("sizeText")
fun TextView.setSizeText(size: Number) {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
}

@BindingAdapter("drawableTintColor")
fun TextView.setDrawableTintColor(color: Int) {
    compoundDrawables.filterNotNull().forEach {
        try {
            it.colorFilter = PorterDuffColorFilter(getColor(context, color), PorterDuff.Mode.SRC_IN)
        } catch (e: Resources.NotFoundException) {
            it.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@BindingAdapter("tintColor")
fun ImageView.setImageTint(@ColorInt color: Int) {
    setColorFilter(color)
}

@BindingAdapter("tintRes")
fun ImageView.setImageTintRes(@ColorRes colorRes: Int) {
    try {
        setColorFilter(getColor(context, colorRes), PorterDuff.Mode.SRC_IN)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@BindingAdapter("drawableRes")
fun ImageView.setDrawableRes(@DrawableRes drawableRes: Int?) {
    kotlin.runCatching {
        drawableRes?.let { setImageResource(drawableRes) }
    }
}

@BindingAdapter("paddingStatusBar")
fun View.isPaddingStatusBar(shouldPadding: Boolean = false) {
    if (shouldPadding) {
        setPadding(0, context.statusBarHeight, 0, 0)
    }
}

@BindingAdapter("paddingNavigationBar")
fun View.isPaddingNavigationBar(shouldPadding: Boolean = false) {
    if (shouldPadding) {
        setPadding(0, 0, 0, context.navigationBarHeight)
    }
}

@BindingAdapter("marginToStatusBar")
fun View.setMarginToStatusBar(dimen: Float) {
    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.topMargin = dimen.toInt() + context.statusBarHeight
    this.layoutParams = layoutParams
}

@BindingAdapter(
    value = ["layoutMarginStart", "layoutMarginTop", "layoutMarginEnd", "layoutMarginBottom"],
    requireAll = false
)
fun View.setLayoutMargin(start: Float?, top: Float?, end: Float?, bottom: Float?) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        start?.let { leftMargin = it.toInt() }
        top?.let { topMargin = it.toInt() }
        end?.let { rightMargin = it.toInt() }
        bottom?.let { bottomMargin = it.toInt() }
    }
}

@BindingAdapter(
    value = ["layoutPaddingStart", "layoutPaddingTop", "layoutPaddingEnd", "layoutPaddingBottom"],
    requireAll = false
)
fun View.setLayoutPadding(start: Float?, top: Float?, end: Float?, bottom: Float?) {
    val pStart = start?.toInt() ?: paddingStart
    val pTop = top?.toInt() ?: paddingTop
    val pEnd = end?.toInt() ?: paddingEnd
    val pBottom = bottom?.toInt() ?: paddingBottom
    setPadding(pStart, pTop, pEnd, pBottom)
}

@BindingAdapter(value = ["layoutHeight", "layoutWidth"], requireAll = false)
fun View.setLayoutWidthHeight(heightDimen: Float?, widthDimen: Float?) {
    layoutParams = layoutParams.apply {
        widthDimen?.let { this.width = it.toInt() }
        heightDimen?.let { this.height = it.toInt() }
    }
}
