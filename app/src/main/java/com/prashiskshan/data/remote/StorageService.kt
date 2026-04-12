package com.prashiskshan.data.remote

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.prashiskshan.core.Result
import kotlinx.coroutines.tasks.await

/**
 * Remote storage service for Firebase Storage.
 */
class StorageService {

    private val storage = FirebaseStorage.getInstance()

    suspend fun uploadFile(
        path: String,
        uri: Uri
    ): Result<String> {
        return try {
            val ref = storage.reference.child(path)
            ref.putFile(uri).await()
            val downloadUrl = ref.downloadUrl.await().toString()
            Result.success(downloadUrl)
        } catch (e: Exception) {
            Result.error(e, e.message ?: "Failed to upload file")
        }
    }

    suspend fun uploadBytes(
        path: String,
        bytes: ByteArray
    ): Result<String> {
        return try {
            val ref = storage.reference.child(path)
            ref.putBytes(bytes).await()
            val downloadUrl = ref.downloadUrl.await().toString()
            Result.success(downloadUrl)
        } catch (e: Exception) {
            Result.error(e, e.message ?: "Failed to upload bytes")
        }
    }
}
