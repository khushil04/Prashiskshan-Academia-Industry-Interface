package com.prashiskshan.domain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prashiskshan.core.Resource

/**
 * ViewModel placeholder for industry flows.
 */
class IndustryViewModel : ViewModel() {

    val postInternshipState: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val reviewState: MutableLiveData<Resource<Unit>> = MutableLiveData()

    fun postInternship() {
        postInternshipState.value = Resource.loading<Unit>()
    }

    fun review() {
        reviewState.value = Resource.loading<Unit>()
    }
}

