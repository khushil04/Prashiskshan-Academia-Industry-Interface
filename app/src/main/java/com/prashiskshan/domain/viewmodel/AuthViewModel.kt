package com.prashiskshan.domain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prashiskshan.core.Resource

/**
 * ViewModel placeholder for auth flows.
 *
 * Wiring it to Activities can be done later.
 */
class AuthViewModel : ViewModel() {

    val loginState: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val registerState: MutableLiveData<Resource<Unit>> = MutableLiveData()

    fun login(email: String, password: String) {
        // Structural placeholder: real execution should call a usecase.
        loginState.value = Resource.loading<Unit>()
    }

    fun register(name: String, email: String, password: String, role: String) {
        // Structural placeholder: real execution should call a usecase.
        registerState.value = Resource.loading<Unit>()
    }
}

