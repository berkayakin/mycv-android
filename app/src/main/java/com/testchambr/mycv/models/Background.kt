package com.testchambr.mycv.models

import java.io.Serializable

class Background : Serializable {

    var languages: MutableList<Language> = mutableListOf()
    var hobbies: MutableList<String> = mutableListOf()

}