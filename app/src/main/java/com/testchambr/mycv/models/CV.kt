/*
 * *
 *  * Created by Berkay AKIN on 11/16/19 6:33 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/16/19 6:32 PM
 *
 */

package com.testchambr.mycv.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CV : Serializable {

    var name: String = ""
    var title: String = ""
    var location: String = ""

    @SerializedName("photo_url")
    var photoURL: String = ""

    var skillset: MutableList<Skill> = mutableListOf()

    @SerializedName("personal_projects")
    var personalProjects: MutableList<Project> = mutableListOf()

    @SerializedName("incomplete_personal_projects")
    var incompletePersonalProjects: MutableList<Project> = mutableListOf()

    @SerializedName("old_projects")
    var oldProjects: MutableList<Project> = mutableListOf()

    var work: MutableList<Work> = mutableListOf()
    var education: MutableList<Education> = mutableListOf()
    var background: Background? = null

}