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