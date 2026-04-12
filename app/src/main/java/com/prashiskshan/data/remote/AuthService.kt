package com.prashiskshan.data.remote

import com.prashiskshan.core.Result
import com.prashiskshan.data.model.User

/**
 * Remote auth service (Firebase or other provider).
 *
 * This is a structural placeholder; actual implementation can be wired later.
 */
class AuthService {

    suspend fun login(email: String, password: String): Result<User> {
        return Result.error(Exception("Not implemented"), "AuthService.login is not implemented")
    }

    suspend fun register(name: String, email: String, password: String, role: String): Result<User> {
        return Result.error(Exception("Not implemented"), "AuthService.register is not implemented")
    }
}

