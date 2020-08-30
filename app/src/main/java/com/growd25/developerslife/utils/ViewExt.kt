package com.growd25.developerslife.utils

import android.view.View

fun View.visibility(isVisible:Boolean){
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
