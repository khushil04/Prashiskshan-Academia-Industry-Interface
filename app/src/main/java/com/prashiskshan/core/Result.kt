package com.prashiskshan.core

/**
 * A generic sealed class to handle operation results
 * Used for wrapping API/Database operation responses
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception, val message: String? = null) : Result<Nothing>()
    object Loading : Result<Nothing>()
    
    val isSuccess: Boolean
        get() = this is Success
    
    val isError: Boolean
        get() = this is Error
    
    val isLoading: Boolean
        get() = this is Loading
    
    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }
    
    fun exceptionOrNull(): Exception? = when (this) {
        is Error -> exception
        else -> null
    }
    
    companion object {
        fun <T> success(data: T): Result<T> = Success(data)
        fun error(exception: Exception, message: String? = null): Result<Nothing> = Error(exception, message)
        fun loading(): Result<Nothing> = Loading
    }
}

/**
 * Extension function to handle result with callbacks
 */
inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T> Result<T>.onError(action: (Exception, String?) -> Unit): Result<T> {
    if (this is Result.Error) action(exception, message)
    return this
}

inline fun <T> Result<T>.onLoading(action: () -> Unit): Result<T> {
    if (this is Result.Loading) action()
    return this
}
