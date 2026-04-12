package com.prashiskshan.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.prashiskshan.core.Constants
import com.prashiskshan.core.Result
import com.prashiskshan.data.model.User
import kotlinx.coroutines.tasks.await

/**
 * Consolidated Repository for handling authentication and user data operations.
 */
class AuthRepository(private val context: Context) {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val prefs: SharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
    
    fun isLoggedIn(): Boolean {
         return auth.currentUser != null && prefs.getBoolean(Constants.PREF_IS_LOGGED_IN, false)
    }
    
    suspend fun login(email: String, password: String, expectedRole: String): Result<Unit> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user
            if (user != null) {
                val document = firestore.collection(Constants.COLLECTION_USERS)
                    .document(user.uid)
                    .get()
                    .await()
                
                val actualRole = document.getString("userType") ?: Constants.USER_TYPE_STUDENT
                val name = document.getString("name") ?: ""
                
                val isRoleMatch = actualRole.equals(expectedRole, ignoreCase = true)

                if (isRoleMatch) {
                    saveUserSession(user.uid, user.email ?: "", name, actualRole)
                    Result.success(Unit)
                } else {
                    auth.signOut()
                    Result.error(Exception("Unauthorized"), "This account is registered as $actualRole, not $expectedRole.")
                }
            } else {
                Result.error(Exception("User is null"), "Login failed")
            }
        } catch (e: Exception) {
            Result.error(e, e.message)
        }
    }
    
    suspend fun register(
        name: String,
        email: String,
        password: String,
        userType: String = Constants.USER_TYPE_STUDENT
    ): Result<Unit> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            
            if (user != null) {
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

                // Initialize leaderboard entry for students
                if (userType == Constants.USER_TYPE_STUDENT) {
                    val leaderboardData = hashMapOf(
                        "id" to user.uid,
                        "name" to name,
                        "points" to 0,
                        "completedInternships" to 0,
                        "category" to userType
                    )
                    firestore.collection("leaderboard")
                        .document(user.uid)
                        .set(leaderboardData)
                        .await()
                }
                
                saveUserSession(user.uid, email, name, userType)
                Result.success(Unit)
            } else {
                Result.error(Exception("User is null"), "Registration failed")
            }
        } catch (e: Exception) {
            Result.error(e, e.message)
        }
    }
    
    fun logout() {
        auth.signOut()
        clearUserSession()
    }
    
    private fun saveUserSession(userId: String, email: String, name: String, userType: String? = null) {
        prefs.edit().apply {
            putString(Constants.PREF_USER_ID, userId)
            putString(Constants.PREF_USER_EMAIL, email)
            putString(Constants.PREF_USER_NAME, name)
            putBoolean(Constants.PREF_IS_LOGGED_IN, true)
            if (userType != null) {
                putString(Constants.PREF_USER_TYPE, userType)
            }
            apply()
        }
    }
    
    private fun clearUserSession() {
        prefs.edit().apply {
            remove(Constants.PREF_USER_ID)
            remove(Constants.PREF_USER_EMAIL)
            remove(Constants.PREF_USER_NAME)
            remove(Constants.PREF_USER_TYPE)
            putBoolean(Constants.PREF_IS_LOGGED_IN, false)
            apply()
        }
    }

    fun getCurrentUser(): Result<User> {
        val userId = prefs.getString(Constants.PREF_USER_ID, null) ?: return Result.error(Exception("Not logged in"))
        val role = prefs.getString(Constants.PREF_USER_TYPE, Constants.USER_TYPE_STUDENT) ?: Constants.USER_TYPE_STUDENT
        val name = prefs.getString(Constants.PREF_USER_NAME, "") ?: ""
        return Result.success(User(userId = userId, name = name, role = role))
    }
}
