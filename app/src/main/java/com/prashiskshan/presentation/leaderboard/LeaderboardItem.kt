package com.prashiskshan.presentation.leaderboard

data class LeaderboardItem(
    val id: String = "",
    val name: String = "",
    val points: Int = 0,
    val completedInternships: Int = 0,
    val category: String = "Student",
    val avatar: String = ""
)
