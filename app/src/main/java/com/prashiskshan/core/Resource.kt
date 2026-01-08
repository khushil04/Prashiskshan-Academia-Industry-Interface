package com.prashiskshan.core

/**
 * A wrapper class for data with loading, success, and error states
 * Useful for UI state management with LiveData/StateFlow
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    
    companion object {
        fun <T> success(data: T): Resource<T> = Success(data)
        fun <T> error(message: String, data: T? = null): Resource<T> = Error(message, data)
        fun <T> loading(data: T? = null): Resource<T> = Loading(data)
    }
}

/**
 * Extension to check resource state
 */
val <T> Resource<T>.isSuccess: Boolean
    get() = this is Resource.Success

val <T> Resource<T>.isError: Boolean
    get() = this is Resource.Error

val <T> Resource<T>.isLoading: Boolean
    get() = this is Resource.Loading
