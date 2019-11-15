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