/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 6:33 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/16/19 6:32 PM
 *
 */

package com.testchambr.mycv.models

import java.io.Serializable

class Background : Serializable {

    var languages: MutableList<Language> = mutableListOf()
    var hobbies: MutableList<String> = mutableListOf()

}