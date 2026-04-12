package com.prashiskshan.data.model

import com.google.firebase.firestore.PropertyName

/**
 * Domain model for an internship/post.
 */
data class Internship(
    @get:PropertyName("internshipId") @set:PropertyName("internshipId") var internshipId: String = "",
    @get:PropertyName("title") @set:PropertyName("title") var title: String = "",
    @get:PropertyName("company") @set:PropertyName("company") var company: String = "",
    @get:PropertyName("description") @set:PropertyName("description") var description: String = "",
    @get:PropertyName("duration") @set:PropertyName("duration") var duration: String = "",
    @get:PropertyName("location") @set:PropertyName("location") var location: String = "",
    @get:PropertyName("domain") @set:PropertyName("domain") var domain: String = "",
    @get:PropertyName("type") @set:PropertyName("type") var type: String = "Full-time",
    @get:PropertyName("completionPercent") @set:PropertyName("completionPercent") var completionPercent: Int = 0,
    @get:PropertyName("postedBy") @set:PropertyName("postedBy") var postedBy: String? = null,
    @get:PropertyName("createdAt") @set:PropertyName("createdAt") var createdAt: Long = System.currentTimeMillis()
)
