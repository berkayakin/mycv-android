/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 8:49 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 05/03/20 2:01 AM
 *  
 */

package com.testchambr.mycv.helpers

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View
import com.testchambr.mycv.R

class Utils {

    companion object {

        fun isNightModeOn(context: Context): Boolean {
            val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return !(currentNightMode == Configuration.UI_MODE_NIGHT_NO || currentNightMode == Configuration.UI_MODE_NIGHT_UNDEFINED)
        }

        fun setLightStatusBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var flags = activity.window.decorView.systemUiVisibility
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                activity.window.decorView.systemUiVisibility = flags
                activity.window.statusBarColor = activity.resources.getColor(R.color.colorPrimaryDark)
            }
        }

    }

}