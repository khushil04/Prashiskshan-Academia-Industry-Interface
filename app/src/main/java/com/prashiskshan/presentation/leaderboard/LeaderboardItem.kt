package com.prashiskshan.presentation.leaderboard

data class LeaderboardItem(
    val id: String = "",
    val rank: Int = 0,
    val name: String = "",
    val points: Int = 0,
    val category: String = "", // Student, Institution, Faculty
    val avatar: String = "" // Optional: avatar URL or resource
)

