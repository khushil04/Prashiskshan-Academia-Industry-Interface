package com.prashiskshan.presentation.industry

import com.prashiskshan.data.model.Application

data class ApplicantItem(
    val application: Application,
    val studentName: String,
    val studentEmail: String,
    val skills: String = "",
    val resumeUrl: String = ""
)
