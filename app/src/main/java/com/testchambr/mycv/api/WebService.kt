package com.testchambr.mycv.api

import com.testchambr.mycv.models.CV
import retrofit2.http.GET

interface WebService {
    @GET("berkayakin/1c65fa739133ba9e6b01df7afc8dbe84/raw/cv.json")
    suspend fun getCV(): CV
}