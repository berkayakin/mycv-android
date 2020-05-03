package com.testchambr.mycv.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.testchambr.mycv.LiveDataResult
import com.testchambr.mycv.api.ApiClient
import com.testchambr.mycv.models.CV
import retrofit2.Call
import retrofit2.Callback

class CVRepository {

    val liveData: MutableLiveData<LiveDataResult<CV>> = MutableLiveData()

    fun getCV(): MutableLiveData<LiveDataResult<CV>> {
        val retrofitCall  = ApiClient.apiInterface.getCV()
        retrofitCall.enqueue(object : Callback<CV> {
            override fun onFailure(call: Call<CV>, t: Throwable?) {
                liveData.value = LiveDataResult(null, t)
                Log.e("on failure :", "retrofit error")
            }
            override fun onResponse(call: Call<CV>, response: retrofit2.Response<CV>) {
                liveData.value = LiveDataResult(response.body(), null)
                Log.e("hasActiveObservers 1", liveData.hasActiveObservers().toString() + " check")
                Log.e("on response :", "$liveData check")
            }
        })
        return liveData
    }
}