package com.testchambr.mycv.presenters

import com.testchambr.mycv.api.ApiClient
import com.testchambr.mycv.contracts.MainContract

class MainActivityPresenter(private val view: MainContract.View) : MainContract.Presenter {

    override suspend fun getCV() = ApiClient.apiInterface.getCV()

}