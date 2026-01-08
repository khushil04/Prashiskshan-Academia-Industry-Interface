package com.prashiskshan.presentation.common

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.databinding.ActivityMainDashboardBinding
import com.prashiskshan.presentation.faculty.FacultyDashboardActivity
import com.prashiskshan.presentation.industry.IndustryDashboardActivity
import com.prashiskshan.presentation.student.ProfileActivity
import com.prashiskshan.presentation.student.StudentDashboardActivity
import com.prashiskshan.presentation.courses.CoursesActivity
import com.prashiskshan.presentation.govt.GovtInternshipsActivity
import com.prashiskshan.presentation.events.Event
import com.prashiskshan.presentation.events.EventAdapter
import com.prashiskshan.presentation.events.EventsActivity
import com.prashiskshan.presentation.leaderboard.LeaderboardItem
import com.prashiskshan.presentation.leaderboard.LeaderboardAdapter
import com.prashiskshan.presentation.leaderboard.LeaderboardActivity
import com.prashiskshan.presentation.analytics.ProgressAnalyticsActivity
import androidx.recyclerview.widget.LinearLayoutManager

class MainDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainDashboardBinding
    private lateinit var eventAdapter: EventAdapter
    private lateinit var leaderboardAdapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide default action bar since we have custom toolbar
        supportActionBar?.hide()

        setupListeners()
        setupEventsSection()
        setupLeaderboardSection()
    }

    private fun setupEventsSection() {
        eventAdapter = EventAdapter { event ->
            // Navigate to full events list
            startActivity(Intent(this, EventsActivity::class.java))
        }
        binding.rvEvents.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvEvents.adapter = eventAdapter

        // Load sample events for dashboard preview
        val sampleEvents = listOf(
            Event(
                id = "1",
                title = "Tech Fest 2024",
                type = "Hackathon",
                description = "Join us for the biggest tech fest with coding competitions, workshops, and networking opportunities.",
                date = "Dec 15-17, 2024",
                location = "Hybrid",
                status = "Ongoing",
                participants = 1234,
                registrationUrl = ""
            ),
            Event(
                id = "2",
                title = "AI Innovation Challenge",
                type = "Competition",
                description = "Build innovative AI solutions and compete for prizes worth ₹5 Lakhs.",
                date = "Jan 10-12, 2025",
                location = "Online",
                status = "Upcoming",
                participants = 856,
                registrationUrl = ""
            ),
            Event(
                id = "3",
                title = "Code Sprint 2025",
                type = "Hackathon",
                description = "24-hour coding marathon with real-world problem statements.",
                date = "Feb 5-6, 2025",
                location = "Offline",
                status = "Upcoming",
                participants = 567,
                registrationUrl = ""
            )
        )
        eventAdapter.submitList(sampleEvents)
    }

    private fun setupLeaderboardSection() {
        leaderboardAdapter = LeaderboardAdapter { item ->
            // Navigate to full leaderboard
            startActivity(Intent(this, LeaderboardActivity::class.java))
        }
        binding.rvLeaderboard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvLeaderboard.adapter = leaderboardAdapter

        // Load top performers for dashboard preview
        val topPerformers = listOf(
            LeaderboardItem("1", 1, "Rajesh Kumar", 2450, "Student"),
            LeaderboardItem("2", 2, "IIT Delhi", 2280, "Institution"),
            LeaderboardItem("3", 3, "Priya Sharma", 2150, "Student"),
            LeaderboardItem("4", 4, "NIT Trichy", 1980, "Institution"),
            LeaderboardItem("5", 5, "Dr. Amit Verma", 1850, "Faculty")
        )
        leaderboardAdapter.submitList(topPerformers)
    }

    private fun setupListeners() {
        binding.cardStudent.setOnClickListener {
            startActivity(Intent(this, StudentDashboardActivity::class.java))
        }

        binding.cardFaculty.setOnClickListener {
            startActivity(Intent(this, FacultyDashboardActivity::class.java))
        }

        binding.cardIndustry.setOnClickListener {
            startActivity(Intent(this, IndustryDashboardActivity::class.java))
        }

        binding.cardAdmin.setOnClickListener {
            startActivity(Intent(this, CoursesActivity::class.java))
        }

        binding.cardGovt.setOnClickListener {
            startActivity(Intent(this, GovtInternshipsActivity::class.java))
        }

        binding.cardMore.setOnClickListener {
            startActivity(Intent(this, MoreActivity::class.java))
        }

        binding.ivProfile.setOnClickListener {
            // Navigate to profile (Student profile as default)
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.cardProgressAnalytics.setOnClickListener {
            startActivity(Intent(this, ProgressAnalyticsActivity::class.java))
        }
    }
}
