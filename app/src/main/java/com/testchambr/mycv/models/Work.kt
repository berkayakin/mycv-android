/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 8:47 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/16/19 6:33 PM
 *  
 */

package com.testchambr.mycv.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Work : Serializable {

    var company: String = ""

    @SerializedName("logo_url")
    var logoURL: String? = null

    var position: String = ""
    var location: String = ""
    var from: String = ""
    var to: String = ""
    var description: String? = null

}