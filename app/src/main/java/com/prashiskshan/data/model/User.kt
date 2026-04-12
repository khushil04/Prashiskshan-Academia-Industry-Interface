package com.prashiskshan.data.model

/**
 * Domain model representing an application user.
 */
data class User(
    val userId: String,
    val name: String,
    val role: String // student/faculty/industry/admin
)

