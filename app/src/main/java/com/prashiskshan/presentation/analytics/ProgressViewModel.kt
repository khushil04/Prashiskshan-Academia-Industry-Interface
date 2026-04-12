package com.prashiskshan.presentation.analytics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.prashiskshan.core.Constants
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.data.repository.ProgressRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class StudentProgress(
    val internshipProgress: Int = 0,
    val logbookProgress: Int = 0,
    val reportSubmitted: Boolean = false,
    val coursesCompleted: Int = 0,
    val userName: String = "Student"
)

class ProgressViewModel(application: Application) : AndroidViewModel(application) {
    private val progressRepository = ProgressRepository()
    private val authRepository = AuthRepository(application)
    private val firestore = FirebaseFirestore.getInstance()

    private val _progressData = MutableLiveData<StudentProgress>()
    val progressData: LiveData<StudentProgress> = _progressData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchProgress() {
        val userResult = authRepository.getCurrentUser()
        if (userResult.isSuccess) {
            val user = userResult.getOrNull()
            if (user != null) {
                viewModelScope.launch {
                    _isLoading.value = true
                    
                    // Fetch latest user name from Firestore if session name is empty
                    var currentUserName = user.name
                    if (currentUserName.isEmpty()) {
                        try {
                            val doc = firestore.collection(Constants.COLLECTION_USERS)
                                .document(user.userId)
                                .get()
                                .await()
                            currentUserName = doc.getString("name") ?: "Student"
                        } catch (e: Exception) {
                            currentUserName = "Student"
                        }
                    }

                    val applications = progressRepository.getApplications(user.userId)
                    val logbookCount = progressRepository.getLogbookEntries(user.userId)
                    val reportCount = progressRepository.getReportsCount(user.userId)
                    val coursesCount = progressRepository.getCompletedCoursesCount(user.userId)
                    
                    // Calculate Internship Progress
                    val completedApps = applications.count { it.status == Constants.STATUS_COMPLETED }
                    val totalApps = applications.size
                    val internProgress = calculatePercentage(completedApps, totalApps)
                    
                    // Calculate Logbook Progress (target 30 entries)
                    val logProgress = calculatePercentage(logbookCount, 30)
                    
                    _progressData.value = StudentProgress(
                        internshipProgress = internProgress,
                        logbookProgress = logProgress,
                        reportSubmitted = reportCount > 0,
                        coursesCompleted = coursesCount,
                        userName = currentUserName
                    )
                    
                    _isLoading.value = false
                }
            }
        }
    }

    private fun calculatePercentage(done: Int, total: Int): Int {
        if (total <= 0) return 0
        return ((done.toFloat() / total.toFloat()) * 100).toInt().coerceAtMost(100)
    }
}
