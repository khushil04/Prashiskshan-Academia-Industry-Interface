package com.prashiskshan.data.model

import com.google.firebase.firestore.PropertyName

/**
 * Domain model for an internship application.
 */
data class Application(
    @get:PropertyName("applicationId") @set:PropertyName("applicationId") var applicationId: String = "",
    @get:PropertyName("internshipId") @set:PropertyName("internshipId") var internshipId: String = "",
    @get:PropertyName("studentId") @set:PropertyName("studentId") var studentId: String = "",
    @get:PropertyName("status") @set:PropertyName("status") var status: String = "pending",
    @get:PropertyName("appliedAt") @set:PropertyName("appliedAt") var appliedAt: Long = System.currentTimeMillis()
)
