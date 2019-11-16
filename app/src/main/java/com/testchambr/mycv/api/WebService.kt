/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 8:47 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/16/19 6:33 PM
 *  
 */

package com.testchambr.mycv.api

import com.testchambr.mycv.models.CV
import retrofit2.http.GET

interface WebService {
    @GET("berkayakin/1c65fa739133ba9e6b01df7afc8dbe84/raw/cv.json")
    suspend fun getCV(): CV
}