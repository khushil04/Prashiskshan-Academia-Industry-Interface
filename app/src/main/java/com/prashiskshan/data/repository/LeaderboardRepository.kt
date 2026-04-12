package com.prashiskshan.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.prashiskshan.core.Constants
import com.prashiskshan.presentation.leaderboard.LeaderboardItem
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class LeaderboardRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val leaderboardCollection = firestore.collection("leaderboard")
    private val usersCollection = firestore.collection(Constants.COLLECTION_USERS)

    /**
     * Fetches top performers. To ensure real users show up even with 0 points,
     * it fetches students from the users collection and merges with their leaderboard data.
     */
    suspend fun getTopPerformers(): List<LeaderboardItem> = coroutineScope {
        try {
            // 1. Fetch all users who are students
            val usersSnapshot = usersCollection
                .whereEqualTo("userType", Constants.USER_TYPE_STUDENT)
                .limit(50) // Limit to avoid performance issues
                .get()
                .await()
            
            // 2. For each student, fetch their leaderboard data (points, etc.)
            val deferredItems = usersSnapshot.documents.map { userDoc ->
                async {
                    val userId = userDoc.id
                    val name = userDoc.getString("name") ?: "Unknown User"
                    val category = userDoc.getString("userType") ?: Constants.USER_TYPE_STUDENT
                    
                    val lbDoc = leaderboardCollection.document(userId).get().await()
                    if (lbDoc.exists()) {
                        lbDoc.toObject(LeaderboardItem::class.java)?.copy(
                            name = name, // Always use latest name from users collection
                            category = category
                        ) ?: LeaderboardItem(id = userId, name = name, category = category)
                    } else {
                        LeaderboardItem(id = userId, name = name, category = category, points = 0, completedInternships = 0)
                    }
                }
            }
            
            deferredItems.awaitAll().sortedByDescending { it.points }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Update points for a user
     * @param userId The ID of the user
     * @param pointsToAdd The number of points to add
     * @param internshipIncrement Whether to increment completed internships count
     */
    suspend fun updatePoints(userId: String, pointsToAdd: Int, internshipIncrement: Boolean = false) {
        val userRef = leaderboardCollection.document(userId)
        
        // Get user info to ensure the leaderboard entry has correct initial metadata
        val userDoc = try {
            usersCollection.document(userId).get().await()
        } catch (e: Exception) {
            null
        }
        
        val userName = userDoc?.getString("name") ?: "Unknown User"
        val userType = userDoc?.getString("userType") ?: Constants.USER_TYPE_STUDENT

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            if (!snapshot.exists()) {
                val data = mutableMapOf<String, Any>(
                    "id" to userId,
                    "name" to userName,
                    "points" to pointsToAdd,
                    "completedInternships" to if (internshipIncrement) 1 else 0,
                    "category" to userType
                )
                transaction.set(userRef, data)
            } else {
                val currentPoints = snapshot.getLong("points") ?: 0
                val currentInternships = snapshot.getLong("completedInternships") ?: 0
                
                transaction.update(userRef, "points", currentPoints + pointsToAdd)
                if (internshipIncrement) {
                    transaction.update(userRef, "completedInternships", currentInternships + 1)
                }
                // Sync metadata
                transaction.update(userRef, "name", userName)
                transaction.update(userRef, "category", userType)
            }
        }.await()
    }
    
    // Point Constants
    companion object {
        const val POINTS_INTERNSHIP_COMPLETED = 100
        const val POINTS_LOGBOOK_ENTRY = 5
        const val POINTS_REPORT_APPROVED = 50
        const val POINTS_COURSE_COMPLETED = 30
    }
}
