/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 6:33 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/16/19 6:32 PM
 *
 */

package com.testchambr.mycv.contracts

import com.testchambr.mycv.models.CV

class MainContract {

    interface View {
        fun initView()
    }

    interface Presenter {
        suspend fun getCV(): CV
    }

}