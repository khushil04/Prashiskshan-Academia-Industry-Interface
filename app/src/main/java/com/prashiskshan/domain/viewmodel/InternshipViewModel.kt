package com.prashiskshan.domain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashiskshan.core.Resource
import com.prashiskshan.core.Result
import com.prashiskshan.data.model.*
import com.prashiskshan.data.repository.InternshipRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for internship-related operations.
 */
class InternshipViewModel : ViewModel() {

    private val repository = InternshipRepository()

    val internships: MutableLiveData<Resource<List<Internship>>> = MutableLiveData()
    val applications: MutableLiveData<Resource<List<Application>>> = MutableLiveData()
    val logbookEntries: MutableLiveData<Resource<List<Logbook>>> = MutableLiveData()
    val notifications: MutableLiveData<Resource<List<Notification>>> = MutableLiveData()
    
    val postInternshipState: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val applyInternshipState: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val submitLogbookState: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val uploadReportState: MutableLiveData<Resource<Unit>> = MutableLiveData()

    fun fetchInternships() {
        internships.value = Resource.loading()
        viewModelScope.launch {
            val result = repository.getAllInternships()
            if (result is Result.Success) {
                internships.value = Resource.success(result.data)
            } else if (result is Result.Error) {
                internships.value = Resource.error(result.message ?: "Failed to fetch internships")
            }
        }
    }

    fun postInternship(internship: Internship) {
        postInternshipState.value = Resource.loading()
        viewModelScope.launch {
            val result = repository.postInternship(internship)
            if (result is Result.Success) {
                postInternshipState.value = Resource.success(Unit)
            } else if (result is Result.Error) {
                postInternshipState.value = Resource.error(result.message ?: "Failed to post internship")
            }
        }
    }

    fun applyInternship(application: Application) {
        applyInternshipState.value = Resource.loading()
        viewModelScope.launch {
            val result = repository.applyToInternship(application)
            if (result is Result.Success) {
                applyInternshipState.value = Resource.success(Unit)
            } else if (result is Result.Error) {
                applyInternshipState.value = Resource.error(result.message ?: "Failed to apply")
            }
        }
    }

    fun submitLogbook(logbook: Logbook) {
        submitLogbookState.value = Resource.loading()
        viewModelScope.launch {
            val result = repository.submitLogbook(logbook)
            if (result is Result.Success) {
                submitLogbookState.value = Resource.success(Unit)
            } else if (result is Result.Error) {
                submitLogbookState.value = Resource.error(result.message ?: "Failed to submit logbook")
            }
        }
    }

    fun fetchLogbook(studentId: String) {
        logbookEntries.value = Resource.loading()
        viewModelScope.launch {
            val result = repository.getLogbook(studentId)
            if (result is Result.Success) {
                logbookEntries.value = Resource.success(result.data)
            } else if (result is Result.Error) {
                logbookEntries.value = Resource.error(result.message ?: "Failed to fetch logbook")
            }
        }
    }

    fun uploadReport(report: Report) {
        uploadReportState.value = Resource.loading()
        viewModelScope.launch {
            val result = repository.submitReport(report)
            if (result is Result.Success) {
                uploadReportState.value = Resource.success(Unit)
            } else if (result is Result.Error) {
                uploadReportState.value = Resource.error(result.message ?: "Failed to upload report")
            }
        }
    }

    fun fetchAppliedInternships(studentId: String) {
        applications.value = Resource.loading()
        viewModelScope.launch {
            val result = repository.getApplications(studentId)
            if (result is Result.Success) {
                applications.value = Resource.success(result.data)
            } else if (result is Result.Error) {
                applications.value = Resource.error(result.message ?: "Failed to fetch applications")
            }
        }
    }

    fun fetchNotifications(userId: String) {
        notifications.value = Resource.loading()
        viewModelScope.launch {
            val result = repository.getNotifications(userId)
            if (result is Result.Success) {
                notifications.value = Resource.success(result.data)
            } else if (result is Result.Error) {
                notifications.value = Resource.error(result.message ?: "Failed to fetch notifications")
            }
        }
    }
}
