package com.prashiskshan.domain.usecase

import com.prashiskshan.core.Result
import com.prashiskshan.data.model.Application
import com.prashiskshan.data.repository.InternshipRepository

/**
 * Use case for applying to an internship.
 */
class ApplyInternshipUseCase(
    private val internshipRepository: InternshipRepository
) {
    suspend operator fun invoke(application: Application): Result<Unit> {
        return internshipRepository.applyToInternship(application)
    }
}

