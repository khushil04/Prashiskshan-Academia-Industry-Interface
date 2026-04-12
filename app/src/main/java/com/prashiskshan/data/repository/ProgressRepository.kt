package com.prashiskshan.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.prashiskshan.core.Constants
import com.prashiskshan.data.model.Application
import kotlinx.coroutines.tasks.await

class ProgressRepository {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getApplications(userId: String): List<Application> {
        return try {
            firestore.collection(Constants.COLLECTION_APPLICATIONS)
                .whereEqualTo("studentId", userId)
                .get()
                .await()
                .toObjects(Application::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getLogbookEntries(userId: String): Int {
        return try {
            firestore.collection(Constants.COLLECTION_LOGBOOKS)
                .whereEqualTo("studentId", userId)
                .get()
                .await()
                .size()
        } catch (e: Exception) {
            0
        }
    }

    suspend fun getReportsCount(userId: String): Int {
        return try {
            firestore.collection(Constants.COLLECTION_REPORTS)
                .whereEqualTo("studentId", userId)
                .get()
                .await()
                .size()
        } catch (e: Exception) {
            0
        }
    }

    suspend fun getCompletedCoursesCount(userId: String): Int {
        // Assuming a collection for course enrollments or completions
        return try {
            firestore.collection("enrollments")
                .whereEqualTo("studentId", userId)
                .whereEqualTo("status", "completed")
                .get()
                .await()
                .size()
        } catch (e: Exception) {
            0
        }
    }
}
