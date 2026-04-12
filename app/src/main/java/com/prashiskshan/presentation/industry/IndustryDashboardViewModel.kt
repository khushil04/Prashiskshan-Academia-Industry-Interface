package com.prashiskshan.presentation.industry

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.data.repository.IndustryRepository
import kotlinx.coroutines.launch

data class IndustryStats(
    val companyName: String = "",
    val postingsCount: Int = 0,
    val applicantsCount: Int = 0
)

class IndustryDashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val industryRepository = IndustryRepository()
    private val authRepository = AuthRepository(application)

    private val _stats = MutableLiveData<IndustryStats>()
    val stats: LiveData<IndustryStats> = _stats

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchStats() {
        val userResult = authRepository.getCurrentUser()
        if (userResult.isSuccess) {
            val user = userResult.getOrNull()
            if (user != null) {
                viewModelScope.launch {
                    _isLoading.value = true
                    
                    val internships = industryRepository.getIndustryInternships(user.userId)
                    val applicants = industryRepository.getApplicantsForIndustry(user.userId)
                    
                    _stats.value = IndustryStats(
                        companyName = user.name.ifEmpty { "Company Name" },
                        postingsCount = internships.size,
                        applicantsCount = applicants.size
                    )
                    
                    _isLoading.value = false
                }
            }
        }
    }
}
