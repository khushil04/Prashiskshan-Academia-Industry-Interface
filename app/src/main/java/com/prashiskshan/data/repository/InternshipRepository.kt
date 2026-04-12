package com.prashiskshan.data.repository

import com.prashiskshan.core.Result
import com.prashiskshan.data.model.*
import com.prashiskshan.data.remote.FirestoreService

/**
 * Repository for internship-related operations.
 */
class InternshipRepository {

    private val firestoreService = FirestoreService()

    suspend fun postInternship(internship: Internship): Result<Unit> {
        return firestoreService.postInternship(internship)
    }

    suspend fun getAllInternships(): Result<List<Internship>> {
        return firestoreService.getAllInternships()
    }

    suspend fun applyToInternship(application: Application): Result<Unit> {
        return firestoreService.applyToInternship(application)
    }

    suspend fun getApplications(studentId: String): Result<List<Application>> {
        return firestoreService.getApplicationsByStudent(studentId)
    }

    suspend fun getPendingApplications(): Result<List<Application>> {
        return firestoreService.getPendingApplications()
    }

    suspend fun updateApplicationStatus(applicationId: String, status: String): Result<Unit> {
        return firestoreService.updateApplicationStatus(applicationId, status)
    }

    suspend fun submitLogbook(logbook: Logbook): Result<Unit> {
        return firestoreService.submitLogbookEntry(logbook)
    }

    suspend fun getLogbook(studentId: String): Result<List<Logbook>> {
        return firestoreService.getLogbookEntries(studentId)
    }

    suspend fun getAllLogbooks(): Result<List<Logbook>> {
        return firestoreService.getAllLogbookEntries()
    }

    suspend fun submitReport(report: Report): Result<Unit> {
        return firestoreService.submitReport(report)
    }

    suspend fun getAllReports(): Result<List<Report>> {
        return firestoreService.getAllReports()
    }

    suspend fun updateReportStatus(reportId: String, status: String): Result<Unit> {
        return firestoreService.updateReportStatus(reportId, status)
    }

    suspend fun getNotifications(userId: String): Result<List<Notification>> {
        return firestoreService.getNotifications(userId)
    }
}
