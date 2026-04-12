package com.prashiskshan.data.repository

import com.prashiskshan.core.Result
import com.prashiskshan.data.model.Application

/**
 * Repository for faculty operations.
 *
 * Structural placeholder.
 */
class FacultyRepository {

    suspend fun approveApplication(application: Application): Result<Unit> {
        return Result.error(Exception("Not implemented"), "FacultyRepository.approveApplication is not implemented")
    }

    suspend fun rejectApplication(application: Application): Result<Unit> {
        return Result.error(Exception("Not implemented"), "FacultyRepository.rejectApplication is not implemented")
    }
}

