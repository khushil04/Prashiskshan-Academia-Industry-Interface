package com.prashiskshan.core

object Constants {
    // Firebase Collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_INTERNSHIPS = "internships"
    const val COLLECTION_APPLICATIONS = "applications"
    const val COLLECTION_LOGBOOKS = "logbooks"
    const val COLLECTION_REPORTS = "reports"
    const val COLLECTION_NOTIFICATIONS = "notifications"
    
    // User Types
    const val USER_TYPE_STUDENT = "student"
    const val USER_TYPE_FACULTY = "faculty"
    const val USER_TYPE_INDUSTRY = "industry"
    const val USER_TYPE_ADMIN = "admin"
    
    // Application Status
    const val STATUS_PENDING = "pending"
    const val STATUS_APPROVED = "approved"
    const val STATUS_REJECTED = "rejected"
    const val STATUS_COMPLETED = "completed"
    const val STATUS_ONGOING = "ongoing"
    
    // Internship Status
    const val INTERNSHIP_ACTIVE = "active"
    const val INTERNSHIP_CLOSED = "closed"
    const val INTERNSHIP_DRAFT = "draft"
    
    // Firebase Storage Paths
    const val STORAGE_RESUMES = "resumes"
    const val STORAGE_CERTIFICATES = "certificates"
    const val STORAGE_REPORTS = "reports"
    const val STORAGE_PROFILE_IMAGES = "profile_images"
    const val STORAGE_COMPANY_LOGOS = "company_logos"
    
    // Shared Preferences
    const val PREFS_NAME = "prashiskshan_prefs"
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_TYPE = "user_type"
    const val PREF_USER_EMAIL = "user_email"
    const val PREF_IS_LOGGED_IN = "is_logged_in"
    
    // Intent Extras
    const val EXTRA_USER_TYPE = "extra_user_type"
    const val EXTRA_INTERNSHIP_ID = "extra_internship_id"
    const val EXTRA_APPLICATION_ID = "extra_application_id"
    const val EXTRA_USER_ID = "extra_user_id"
    const val EXTRA_STUDENT_ID = "extra_student_id"
    
    // Request Codes
    const val REQUEST_CODE_PICK_FILE = 1001
    const val REQUEST_CODE_PICK_IMAGE = 1002
    const val REQUEST_CODE_CAMERA = 1003
    const val REQUEST_CODE_STORAGE_PERMISSION = 2001
    const val REQUEST_CODE_CAMERA_PERMISSION = 2002
    const val REQUEST_CODE_NOTIFICATION_PERMISSION = 2003
    
    // Notification Channels
    const val CHANNEL_ID_GENERAL = "general_notifications"
    const val CHANNEL_ID_APPLICATIONS = "application_notifications"
    const val CHANNEL_ID_APPROVALS = "approval_notifications"
    const val CHANNEL_ID_UPDATES = "update_notifications"
    
    // Date Formats
    const val DATE_FORMAT_DISPLAY = "dd MMM yyyy"
    const val DATE_FORMAT_API = "yyyy-MM-dd"
    const val DATETIME_FORMAT_DISPLAY = "dd MMM yyyy, hh:mm a"
    const val DATETIME_FORMAT_API = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    
    // Validation
    const val MIN_PASSWORD_LENGTH = 6
    const val MAX_RESUME_SIZE_MB = 5
    const val MAX_REPORT_SIZE_MB = 10
    const val ALLOWED_FILE_TYPES = "application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    
    // Pagination
    const val PAGE_SIZE = 20
    const val INITIAL_LOAD_SIZE = 20
    
    // Timeouts
    const val NETWORK_TIMEOUT = 30L // seconds
    const val CACHE_TIMEOUT = 5L // minutes
    
    // Error Messages
    const val ERROR_NETWORK = "Network error. Please check your connection."
    const val ERROR_UNKNOWN = "Something went wrong. Please try again."
    const val ERROR_AUTH = "Authentication failed. Please login again."
    const val ERROR_PERMISSION = "Permission denied. Please grant required permissions."
    const val ERROR_FILE_SIZE = "File size exceeds the limit."
    const val ERROR_FILE_TYPE = "Invalid file type."
    
    // Success Messages
    const val SUCCESS_APPLICATION_SUBMITTED = "Application submitted successfully"
    const val SUCCESS_PROFILE_UPDATED = "Profile updated successfully"
    const val SUCCESS_INTERNSHIP_POSTED = "Internship posted successfully"
    const val SUCCESS_APPROVAL_DONE = "Approval completed successfully"
    const val SUCCESS_REPORT_SUBMITTED = "Report submitted successfully"
    
    // NEP 2020 Related
    const val NEP_CREDIT_REQUIREMENT = 20 // Credits required for internship
    const val MIN_INTERNSHIP_DURATION_WEEKS = 4
    const val MAX_INTERNSHIP_DURATION_WEEKS = 24
}
