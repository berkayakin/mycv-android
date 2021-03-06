/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 8:49 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 05/03/20 2:01 AM
 *  
 */

package com.testchambr.mycv.helpers

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.testchambr.mycv.R

object DataBindingAdapter {

    @BindingAdapter("app:customSrc")
    @JvmStatic
    fun setSrc(view: ImageView, resId: Int) {
        Glide.with(view.context)
            .load(resId)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .into(view)
    }

    @BindingAdapter("app:customSrc")
    @JvmStatic
    fun setSrc(view: ImageView, URL: String?) {
        Glide.with(view.context)
            .load(URL)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .into(view)
    }
}