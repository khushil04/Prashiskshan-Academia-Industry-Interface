package com.prashiskshan.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashiskshan.core.Resource
import com.prashiskshan.core.Result
import com.prashiskshan.data.model.Application
import com.prashiskshan.data.model.Logbook
import com.prashiskshan.data.model.Report
import com.prashiskshan.data.repository.InternshipRepository
import kotlinx.coroutines.launch

class FacultyViewModel : ViewModel() {

    private val repository = InternshipRepository()

    private val _pendingApplications = MutableLiveData<Resource<List<Application>>>()
    val pendingApplications: LiveData<Resource<List<Application>>> = _pendingApplications

    private val _allLogbooks = MutableLiveData<Resource<List<Logbook>>>()
    val allLogbooks: LiveData<Resource<List<Logbook>>> = _allLogbooks

    private val _allReports = MutableLiveData<Resource<List<Report>>>()
    val allReports: LiveData<Resource<List<Report>>> = _allReports

    private val _updateStatus = MutableLiveData<Resource<Unit>>()
    val updateStatus: LiveData<Resource<Unit>> = _updateStatus

    fun fetchPendingApplications() {
        _pendingApplications.value = Resource.loading()
        viewModelScope.launch {
            when (val result = repository.getPendingApplications()) {
                is Result.Success -> _pendingApplications.value = Resource.success(result.data)
                is Result.Error -> _pendingApplications.value = Resource.error(result.message ?: "Failed to fetch")
                else -> {}
            }
        }
    }

    fun fetchAllLogbooks() {
        _allLogbooks.value = Resource.loading()
        viewModelScope.launch {
            when (val result = repository.getAllLogbooks()) {
                is Result.Success -> _allLogbooks.value = Resource.success(result.data)
                is Result.Error -> _allLogbooks.value = Resource.error(result.message ?: "Failed to fetch logbooks")
                else -> {}
            }
        }
    }

    fun fetchAllReports() {
        _allReports.value = Resource.loading()
        viewModelScope.launch {
            when (val result = repository.getAllReports()) {
                is Result.Success -> _allReports.value = Resource.success(result.data)
                is Result.Error -> _allReports.value = Resource.error(result.message ?: "Failed to fetch reports")
                else -> {}
            }
        }
    }

    fun updateApplicationStatus(applicationId: String, status: String) {
        _updateStatus.value = Resource.loading()
        viewModelScope.launch {
            when (val result = repository.updateApplicationStatus(applicationId, status)) {
                is Result.Success -> {
                    _updateStatus.value = Resource.success(Unit)
                    fetchPendingApplications()
                }
                is Result.Error -> _updateStatus.value = Resource.error(result.message ?: "Failed to update status")
                else -> {}
            }
        }
    }

    fun updateReportStatus(reportId: String, status: String) {
        viewModelScope.launch {
            repository.updateReportStatus(reportId, status)
            fetchAllReports()
        }
    }

    fun verifyLogbook(logId: String, isVerified: Boolean) {
        viewModelScope.launch {
            // Logic to update verification in Firestore
        }
    }
}
