package com.prashiskshan.presentation.courses

data class Course(
    val id: String = "",
    val title: String = "",
    val provider: String = "", // Coursera / NPTEL / Udemy
    val domain: String = "",
    val isMandatory: Boolean = true,
    val url: String = "",
    val progressPercent: Int? = null
)


