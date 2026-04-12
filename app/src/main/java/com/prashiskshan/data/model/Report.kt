package com.prashiskshan.data.model

import com.google.firebase.firestore.PropertyName

/**
 * Domain model representing a submitted report.
 */
data class Report(
    @get:PropertyName("reportId") @set:PropertyName("reportId") var reportId: String = "",
    @get:PropertyName("studentId") @set:PropertyName("studentId") var studentId: String = "",
    @get:PropertyName("internshipId") @set:PropertyName("internshipId") var internshipId: String = "",
    @get:PropertyName("fileUrl") @set:PropertyName("fileUrl") var fileUrl: String = "",
    @get:PropertyName("status") @set:PropertyName("status") var status: String = "Pending",
    @get:PropertyName("submittedBy") @set:PropertyName("submittedBy") var submittedBy: String? = null,
    @get:PropertyName("createdAt") @set:PropertyName("createdAt") var createdAt: Long = System.currentTimeMillis()
)
