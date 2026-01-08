package com.prashiskshan.presentation.student

data class Internship(
    val id: String = "",
    val title: String = "",
    val company: String = "",
    val location: String = "",
    val domain: String = "",
    val status: String = "Applied", // Applied, Approved, Completed
    val completionPercent: Int = 0
)


