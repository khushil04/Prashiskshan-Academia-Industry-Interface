package com.prashiskshan.presentation.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashiskshan.core.Constants
import com.prashiskshan.data.repository.IndustryRepository
import kotlinx.coroutines.launch

class ApplicantsViewModel : ViewModel() {
    private val repository = IndustryRepository()

    private val _applicants = MutableLiveData<List<ApplicantItem>>()
    val applicants: LiveData<List<ApplicantItem>> = _applicants

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> = _updateStatus

    fun fetchApplicants(industryId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val list = repository.getApplicantsForIndustry(industryId)
            _applicants.value = list
            _isLoading.value = false
        }
    }

    fun updateStatus(applicationId: String, status: String, industryId: String) {
        viewModelScope.launch {
            val success = repository.updateApplicationStatus(applicationId, status)
            if (success) {
                fetchApplicants(industryId) // Refresh list
            }
            _updateStatus.value = success
        }
    }
}
