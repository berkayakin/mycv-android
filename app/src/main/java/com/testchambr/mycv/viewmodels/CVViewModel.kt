package com.testchambr.mycv.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testchambr.mycv.models.CV
import com.testchambr.mycv.repositories.CVRepository

class CVViewModel : ViewModel() {

    private val repository = CVRepository()

    fun getCV() : MutableLiveData<CV>? {
        return repository.getCV()
    }
}