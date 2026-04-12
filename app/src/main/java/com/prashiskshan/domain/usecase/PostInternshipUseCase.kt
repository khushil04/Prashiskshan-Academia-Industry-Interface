package com.prashiskshan.domain.usecase

import com.prashiskshan.core.Result
import com.prashiskshan.data.model.Internship
import com.prashiskshan.data.repository.InternshipRepository

/**
 * Use case for posting an internship.
 */
class PostInternshipUseCase(
    private val internshipRepository: InternshipRepository
) {
    suspend operator fun invoke(internship: Internship): Result<Unit> {
        return internshipRepository.postInternship(internship)
    }
}

