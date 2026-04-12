package com.prashiskshan.presentation.leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prashiskshan.data.repository.LeaderboardRepository
import kotlinx.coroutines.launch

class LeaderboardViewModel : ViewModel() {
    private val repository = LeaderboardRepository()

    private val _leaderboardItems = MutableLiveData<List<LeaderboardItem>>()
    val leaderboardItems: LiveData<List<LeaderboardItem>> = _leaderboardItems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchLeaderboard()
    }

    fun fetchLeaderboard() {
        viewModelScope.launch {
            _isLoading.value = true
            val items = repository.getTopPerformers()
            _leaderboardItems.value = items
            _isLoading.value = false
        }
    }
}
