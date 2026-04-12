package com.prashiskshan.domain.usecase

import com.prashiskshan.core.Result
import com.prashiskshan.data.model.User
import com.prashiskshan.data.repository.AuthRepository

/**
 * Use case for fetching the current user's profile.
 */
class GetUserProfileUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<User> {
        return authRepository.getCurrentUser()
    }
}

