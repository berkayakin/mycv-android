package com.testchambr.mycv.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Project : Serializable {

    var title: String = ""
    var platforms: MutableList<String> = mutableListOf()
    var category: String = ""
    var details: String = ""

    @SerializedName("url")
    var URL: String? = null

    @SerializedName("additional_information")
    var additionalInformation: String? = null

}