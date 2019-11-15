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