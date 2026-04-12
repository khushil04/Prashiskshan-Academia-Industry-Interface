package com.prashiskshan.domain.usecase

import com.prashiskshan.core.Result
import com.prashiskshan.data.model.Report
import com.prashiskshan.data.repository.InternshipRepository

/**
 * Use case for uploading a report for an internship.
 */
class UploadReportUseCase(
    private val internshipRepository: InternshipRepository
) {
    suspend operator fun invoke(report: Report): Result<Unit> {
        return internshipRepository.submitReport(report)
    }
}
