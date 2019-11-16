/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 8:47 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/16/19 6:33 PM
 *
 */

package com.testchambr.mycv.extensions

import android.view.View

fun View.toggleVisibility() {
    this.visibility = if (this.visibility == View.GONE) {
        View.VISIBLE
    } else {
        View.GONE
    }
}