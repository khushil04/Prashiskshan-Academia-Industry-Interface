package com.prashiskshan.data

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.prashiskshan.core.Constants
import kotlinx.coroutines.tasks.await

/**
 * Repository for handling authentication and user data operations
 */
class AuthRepository(private val context: Context) {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val prefs: SharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
    
    /**
     * Get current Firebase user
     */
    fun getCurrentUser(): FirebaseUser? = auth.currentUser
    
    /**
     * Check if user is logged in
     */
    fun isLoggedIn(): Boolean {
        return auth.currentUser != null && prefs.getBoolean(Constants.PREF_IS_LOGGED_IN, false)
    }
    
    /**
     * Login with email and password
     */
    suspend fun login(email: String, password: String): kotlin.Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
            if (user != null) {
                // Save session
                saveUserSession(user.uid, user.email ?: "")
                // Fetch user type from Firestore
                fetchAndSaveUserType(user.uid)
                kotlin.Result.success(user)
            } else {
                kotlin.Result.failure(Exception("Login failed: User is null"))
            }
        } catch (e: Exception) {
            kotlin.Result.failure(e)
        }
    }
    
    /**
     * Register new user with email and password
     */
    suspend fun register(
        name: String,
        email: String,
        password: String,
        userType: String = Constants.USER_TYPE_STUDENT
    ): kotlin.Result<FirebaseUser> {
        return try {
            // Create user in Firebase Auth
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            
            if (user != null) {
                // Save user data to Firestore
                val userData = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "userType" to userType,
                    "createdAt" to com.google.firebase.Timestamp.now(),
                    "updatedAt" to com.google.firebase.Timestamp.now()
                )
                
                firestore.collection(Constants.COLLECTION_USERS)
                    .document(user.uid)
                    .set(userData)
                    .await()
                
                // Save session
                saveUserSession(user.uid, email, userType)
                
                kotlin.Result.success(user)
            } else {
                kotlin.Result.failure(Exception("Registration failed: User is null"))
            }
        } catch (e: Exception) {
            kotlin.Result.failure(e)
        }
    }
    
    /**
     * Logout user
     */
    fun logout() {
        auth.signOut()
        clearUserSession()
    }
    
    /**
     * Save user session to SharedPreferences
     */
    private fun saveUserSession(userId: String, email: String, userType: String? = null) {
        prefs.edit().apply {
            putString(Constants.PREF_USER_ID, userId)
            putString(Constants.PREF_USER_EMAIL, email)
            putBoolean(Constants.PREF_IS_LOGGED_IN, true)
            if (userType != null) {
                putString(Constants.PREF_USER_TYPE, userType)
            }
            apply()
        }
    }
    
    /**
     * Fetch user type from Firestore and save to preferences
     */
    private suspend fun fetchAndSaveUserType(userId: String) {
        try {
            val document = firestore.collection(Constants.COLLECTION_USERS)
                .document(userId)
                .get()
                .await()
            
            val userType = document.getString("userType") ?: Constants.USER_TYPE_STUDENT
            prefs.edit().putString(Constants.PREF_USER_TYPE, userType).apply()
        } catch (e: Exception) {
            // If fetch fails, use default
            prefs.edit().putString(Constants.PREF_USER_TYPE, Constants.USER_TYPE_STUDENT).apply()
        }
    }
    
    /**
     * Clear user session
     */
    private fun clearUserSession() {
        prefs.edit().apply {
            remove(Constants.PREF_USER_ID)
            remove(Constants.PREF_USER_EMAIL)
            remove(Constants.PREF_USER_TYPE)
            putBoolean(Constants.PREF_IS_LOGGED_IN, false)
            apply()
        }
    }
    
    /**
     * Get saved user type
     */
    fun getUserType(): String {
        return prefs.getString(Constants.PREF_USER_TYPE, Constants.USER_TYPE_STUDENT) ?: Constants.USER_TYPE_STUDENT
    }
    
    /**
     * Get saved user ID
     */
    fun getUserId(): String? {
        return prefs.getString(Constants.PREF_USER_ID, null)
    }
}

