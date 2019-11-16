/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 6:33 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/16/19 6:32 PM
 *
 */

package com.testchambr.mycv

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}