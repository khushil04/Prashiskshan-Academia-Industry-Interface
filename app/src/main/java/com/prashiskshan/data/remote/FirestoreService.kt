package com.prashiskshan.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.prashiskshan.core.Result
import com.prashiskshan.data.model.*
import com.prashiskshan.core.Constants
import com.prashiskshan.data.repository.LeaderboardRepository
import kotlinx.coroutines.tasks.await
import java.util.*

/**
 * Service to handle Firestore operations.
 */
class FirestoreService {

    private val db = FirebaseFirestore.getInstance()
    private val leaderboardRepository = LeaderboardRepository()

    // --- User Operations ---
    suspend fun upsertUser(user: User): Result<Unit> {
        return try {
            db.collection(Constants.COLLECTION_USERS)
                .document(user.userId)
                .set(user)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e, "Failed to save user")
        }
    }

    suspend fun updateFcmToken(userId: String, token: String): Result<Unit> {
        return try {
            db.collection(Constants.COLLECTION_USERS)
                .document(userId)
                .set(mapOf("fcmToken" to token), SetOptions.merge())
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e, "Failed to update FCM token")
        }
    }

    // --- Internship Operations ---
    suspend fun postInternship(internship: Internship): Result<Unit> {
        return try {
            db.collection(Constants.COLLECTION_INTERNSHIPS)
                .document(internship.internshipId)
                .set(internship)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e, "Failed to post internship")
        }
    }

    suspend fun getAllInternships(): Result<List<Internship>> {
        return try {
            val snapshot = db.collection(Constants.COLLECTION_INTERNSHIPS)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            val list = snapshot.toObjects(Internship::class.java)
            Result.success(list)
        } catch (e: Exception) {
            Result.error(e, "Failed to fetch internships")
        }
    }

    // --- Application Operations ---
    suspend fun applyToInternship(application: Application): Result<Unit> {
        return try {
            db.collection(Constants.COLLECTION_APPLICATIONS)
                .document(application.applicationId)
                .set(application)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e, "Failed to apply")
        }
    }

    suspend fun getApplicationsByStudent(studentId: String): Result<List<Application>> {
        return try {
            val snapshot = db.collection(Constants.COLLECTION_APPLICATIONS)
                .whereEqualTo("studentId", studentId)
                .get()
                .await()
            val list = snapshot.toObjects(Application::class.java)
            Result.success(list)
        } catch (e: Exception) {
            Result.error(e, "Failed to fetch applications")
        }
    }

    suspend fun getPendingApplications(): Result<List<Application>> {
        return try {
            val snapshot = db.collection(Constants.COLLECTION_APPLICATIONS)
                .whereEqualTo("status", Constants.STATUS_PENDING)
                .get()
                .await()
            val list = snapshot.toObjects(Application::class.java)
            Result.success(list)
        } catch (e: Exception) {
            Result.error(e, "Failed to fetch pending applications")
        }
    }

    suspend fun updateApplicationStatus(applicationId: String, status: String): Result<Unit> {
        return try {
            db.collection(Constants.COLLECTION_APPLICATIONS)
                .document(applicationId)
                .update("status", status)
                .await()
            
            // Auto-generate notification for student
            val appDoc = db.collection(Constants.COLLECTION_APPLICATIONS).document(applicationId).get().await()
            val studentId = appDoc.getString("studentId")
            
            if (studentId != null) {
                // If status is completed, add points
                if (status == Constants.STATUS_COMPLETED) {
                    leaderboardRepository.updatePoints(
                        studentId, 
                        LeaderboardRepository.POINTS_INTERNSHIP_COMPLETED,
                        internshipIncrement = true
                    )
                }

                sendInAppNotification(
                    userId = studentId,
                    title = "Application Update",
                    message = "Your internship application status is now: $status"
                )
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e, "Failed to update application status")
        }
    }

    // --- Logbook Operations ---
    suspend fun submitLogbookEntry(logbook: Logbook): Result<Unit> {
        return try {
            db.collection(Constants.COLLECTION_LOGBOOKS)
                .document(logbook.logId)
                .set(logbook)
                .await()
            
            // Add points for logbook entry
            leaderboardRepository.updatePoints(
                logbook.studentId, 
                LeaderboardRepository.POINTS_LOGBOOK_ENTRY
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e, "Failed to submit logbook")
        }
    }

    suspend fun getLogbookEntries(studentId: String): Result<List<Logbook>> {
        return try {
            val snapshot = db.collection(Constants.COLLECTION_LOGBOOKS)
                .whereEqualTo("studentId", studentId)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
            val list = snapshot.toObjects(Logbook::class.java)
            Result.success(list)
        } catch (e: Exception) {
            Result.error(e, "Failed to fetch logbook")
        }
    }

    suspend fun getAllLogbookEntries(): Result<List<Logbook>> {
        return try {
            val snapshot = db.collection(Constants.COLLECTION_LOGBOOKS)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
            val list = snapshot.toObjects(Logbook::class.java)
            Result.success(list)
        } catch (e: Exception) {
            Result.error(e, "Failed to fetch all logbooks")
        }
    }

    // --- Report Operations ---
    suspend fun submitReport(report: Report): Result<Unit> {
        return try {
            db.collection(Constants.COLLECTION_REPORTS)
                .document(report.reportId)
                .set(report)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e, "Failed to submit report")
        }
    }

    suspend fun getAllReports(): Result<List<Report>> {
        return try {
            val snapshot = db.collection(Constants.COLLECTION_REPORTS)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            val list = snapshot.toObjects(Report::class.java)
            Result.success(list)
        } catch (e: Exception) {
            Result.error(e, "Failed to fetch reports")
        }
    }

    suspend fun updateReportStatus(reportId: String, status: String): Result<Unit> {
        return try {
            db.collection(Constants.COLLECTION_REPORTS)
                .document(reportId)
                .update("status", status)
                .await()

            // Auto-generate notification for student
            val reportDoc = db.collection(Constants.COLLECTION_REPORTS).document(reportId).get().await()
            val studentId = reportDoc.getString("studentId")
            
            if (studentId != null) {
                // If approved, add points
                if (status == Constants.STATUS_APPROVED) {
                    leaderboardRepository.updatePoints(
                        studentId, 
                        LeaderboardRepository.POINTS_REPORT_APPROVED
                    )
                }

                sendInAppNotification(
                    userId = studentId,
                    title = "Report Reviewed",
                    message = "Your internship report has been $status"
                )
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e, "Failed to update report status")
        }
    }

    // --- Notification Operations ---
    suspend fun sendInAppNotification(userId: String, title: String, message: String): Result<Unit> {
        return try {
            val id = UUID.randomUUID().toString()
            val notification = Notification(
                id = id,
                userId = userId,
                title = title,
                message = message,
                timestamp = System.currentTimeMillis()
            )
            db.collection(Constants.COLLECTION_NOTIFICATIONS)
                .document(id)
                .set(notification)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e, "Failed to send notification")
        }
    }

    suspend fun getNotifications(userId: String): Result<List<Notification>> {
        return try {
            val snapshot = db.collection(Constants.COLLECTION_NOTIFICATIONS)
                .whereEqualTo("userId", userId)
                // .orderBy("timestamp", Query.Direction.DESCENDING) // Removed to avoid index error
                .get()
                .await()
            
            // Sort in memory for better UX
            val list = snapshot.toObjects(Notification::class.java)
                .sortedByDescending { it.timestamp }

            Result.success(list)
        } catch (e: Exception) {
            Result.error(e, "Failed to fetch notifications")
        }
    }

    /**
     * Call this when a course is completed to update leaderboard points.
     */
    suspend fun completeCourse(studentId: String, courseName: String): Result<Unit> {
        return try {
            leaderboardRepository.updatePoints(
                studentId,
                LeaderboardRepository.POINTS_COURSE_COMPLETED
            )
            sendInAppNotification(
                userId = studentId,
                title = "Course Completed!",
                message = "Congratulations on completing $courseName. You earned 30 points!"
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.error(e, "Failed to update points for course completion")
        }
    }
}
