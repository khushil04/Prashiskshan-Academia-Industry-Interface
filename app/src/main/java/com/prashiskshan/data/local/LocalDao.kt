package com.prashiskshan.data.local

/**
 * Optional local database DAO (e.g., Room).
 *
 * Structural placeholder.
 */
interface LocalDao<T> {
    suspend fun upsert(entity: T)
    suspend fun delete(entity: T)
}

