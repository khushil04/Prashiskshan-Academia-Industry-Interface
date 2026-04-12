package com.prashiskshan.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.prashiskshan.core.Constants
import com.prashiskshan.data.model.Application
import com.prashiskshan.data.model.Internship
import com.prashiskshan.presentation.industry.ApplicantItem
import kotlinx.coroutines.tasks.await

class IndustryRepository {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getIndustryInternships(industryId: String): List<Internship> {
        return try {
            firestore.collection(Constants.COLLECTION_INTERNSHIPS)
                .whereEqualTo("postedBy", industryId)
                .get()
                .await()
                .toObjects(Internship::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getApplicantsForIndustry(industryId: String): List<ApplicantItem> {
        return try {
            // 1. Get all internships posted by this industry
            val internships = firestore.collection(Constants.COLLECTION_INTERNSHIPS)
                .whereEqualTo("postedBy", industryId)
                .get()
                .await()
                .toObjects(Internship::class.java)
            
            val internshipIds = internships.map { it.internshipId }.filter { it.isNotEmpty() }
            if (internshipIds.isEmpty()) return emptyList()

            // 2. Get all applications for these internships
            val applicationsSnapshot = firestore.collection(Constants.COLLECTION_APPLICATIONS)
                .whereIn("internshipId", internshipIds)
                .get()
                .await()
            
            val applications = applicationsSnapshot.toObjects(Application::class.java)
            
            // 3. Combine with student details
            applications.map { app ->
                val studentDoc = firestore.collection(Constants.COLLECTION_USERS)
                    .document(app.studentId)
                    .get()
                    .await()
                
                ApplicantItem(
                    application = app,
                    studentName = studentDoc.getString("name") ?: "Unknown Student",
                    studentEmail = studentDoc.getString("email") ?: "",
                    skills = studentDoc.getString("skills") ?: "No skills listed",
                    resumeUrl = studentDoc.getString("resumeUrl") ?: ""
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun updateApplicationStatus(applicationId: String, status: String): Boolean {
        return try {
            firestore.collection(Constants.COLLECTION_APPLICATIONS)
                .document(applicationId)
                .update("status", status)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
