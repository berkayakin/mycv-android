/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 8:49 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 05/03/20 2:01 AM
 *  
 */

package com.testchambr.mycv.api

import com.testchambr.mycv.models.CV
import retrofit2.Call
import retrofit2.http.GET

interface WebService {
    @GET("berkayakin/1c65fa739133ba9e6b01df7afc8dbe84/raw/cv.json")
    fun getCV(): Call<CV>
}