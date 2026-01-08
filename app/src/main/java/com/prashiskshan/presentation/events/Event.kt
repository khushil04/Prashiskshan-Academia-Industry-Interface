package com.prashiskshan.presentation.events

data class Event(
    val id: String = "",
    val title: String = "",
    val type: String = "", // Hackathon, Tech Fest, Competition
    val description: String = "",
    val date: String = "",
    val location: String = "", // Online, Offline, Hybrid
    val status: String = "", // Ongoing, Upcoming, Ended
    val participants: Int = 0,
    val registrationUrl: String = ""
)

