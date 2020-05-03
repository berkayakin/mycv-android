/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 8:49 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 05/03/20 2:01 AM
 *  
 */

package com.testchambr.mycv.models

import java.io.Serializable

class Education : Serializable {

    var title: String = ""
    var location: String = ""
    var from: String = ""
    var to: String = ""
    var description: String? = null

}