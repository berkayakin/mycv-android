/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 8:49 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 05/03/20 2:01 AM
 *  
 */

package com.testchambr.mycv.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL = "https://gist.githubusercontent.com/"

    val apiInterface: WebService
    private val retrofit: Retrofit
    private const val DEFAULT_TIMEOUT = 5L
    private val okHttpClient: OkHttpClient

    init {
        val logging = Interceptor { chain ->
            val request = chain.request()
            Log.d("okhttp", "okhttp--->" + request.url().toString())
            chain.proceed(request)
        }
        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        apiInterface = retrofit.create(WebService::class.java)
    }
}