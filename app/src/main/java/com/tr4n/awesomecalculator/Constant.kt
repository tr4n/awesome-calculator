package com.tr4n.awesomecalculator

import android.content.res.Resources

object Constant {

    val screenWidth by lazy {
        Resources.getSystem().displayMetrics.widthPixels
    }
    val screenHeight by lazy {
        Resources.getSystem().displayMetrics.heightPixels
    }
}
