package com.prashiskshan.data.model

import com.google.firebase.firestore.PropertyName

/**
 * Domain model for a student's logbook entry.
 */
data class Logbook(
    @get:PropertyName("logId") @set:PropertyName("logId") var logId: String = "",
    @get:PropertyName("studentId") @set:PropertyName("studentId") var studentId: String = "",
    @get:PropertyName("task") @set:PropertyName("task") var task: String = "",
    @get:PropertyName("learning") @set:PropertyName("learning") var learning: String = "",
    @get:PropertyName("date") @set:PropertyName("date") var date: Long = System.currentTimeMillis(),
    @get:PropertyName("feedback") @set:PropertyName("feedback") var feedback: String = "",
    @get:PropertyName("isVerified") @set:PropertyName("isVerified") var isVerified: Boolean = false
)
