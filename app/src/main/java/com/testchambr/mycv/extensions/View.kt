package com.testchambr.mycv.extensions

import android.view.View

fun View.toggleVisibility() {
    this.visibility = if (this.visibility == View.GONE) {
        View.VISIBLE
    } else {
        View.GONE
    }
}