/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 8:47 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/16/19 6:33 PM
 *  
 */

package com.testchambr.mycv.presenters

import com.testchambr.mycv.api.ApiClient
import com.testchambr.mycv.contracts.MainContract

class MainActivityPresenter(private val view: MainContract.View) : MainContract.Presenter {

    override suspend fun getCV() = ApiClient.apiInterface.getCV()

}