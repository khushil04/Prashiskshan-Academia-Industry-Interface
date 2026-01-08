package com.prashiskshan.core

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

/**
 * Application class for Prashiskshan
 * Initializes Firebase and sets up global configurations
 */
class App : Application() {
    
    companion object {
        private lateinit var instance: App
        
        fun getInstance(): App = instance
        
        fun getAppContext(): Context = instance.applicationContext
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        
        // Initialize Firebase
        initializeFirebase()
        
        // Create notification channels
        createNotificationChannels()
    }
    
    private fun initializeFirebase() {
        try {
            // Initialize Firebase (if not already initialized)
            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this)
            }
            
            // Configure Firestore settings for offline support
            val firestore = FirebaseFirestore.getInstance()
            val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true) // Enable offline persistence
                .build()
            firestore.firestoreSettings = settings
        } catch (e: Exception) {
            // Log error but don't crash the app
            e.printStackTrace()
        }
    }
    
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // General Notifications Channel
            val generalChannel = NotificationChannel(
                Constants.CHANNEL_ID_GENERAL,
                "General Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "General app notifications"
            }
            
            // Application Notifications Channel
            val applicationChannel = NotificationChannel(
                Constants.CHANNEL_ID_APPLICATIONS,
                "Application Updates",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Internship application status updates"
            }
            
            // Approval Notifications Channel
            val approvalChannel = NotificationChannel(
                Constants.CHANNEL_ID_APPROVALS,
                "Approval Requests",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Approval requests and responses"
            }
            
            // Updates Channel
            val updatesChannel = NotificationChannel(
                Constants.CHANNEL_ID_UPDATES,
                "Important Updates",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Important updates and announcements"
            }
            
            // Register all channels
            notificationManager.createNotificationChannels(
                listOf(
                    generalChannel,
                    applicationChannel,
                    approvalChannel,
                    updatesChannel
                )
            )
        }
    }
}
