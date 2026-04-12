package com.prashiskshan.presentation.student

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.databinding.ActivityStudentDashboardBinding
import com.prashiskshan.presentation.analytics.ProgressAnalyticsActivity
import com.prashiskshan.presentation.chat.ChatActivity
import com.prashiskshan.presentation.common.MoreActivity
import com.prashiskshan.presentation.common.NotificationsActivity
import com.prashiskshan.presentation.courses.CoursesActivity
import com.prashiskshan.presentation.events.Event
import com.prashiskshan.presentation.events.EventAdapter
import com.prashiskshan.presentation.events.EventsActivity
import com.prashiskshan.presentation.govt.GovtInternshipsActivity
import com.prashiskshan.presentation.leaderboard.LeaderboardActivity
import com.prashiskshan.presentation.leaderboard.LeaderboardAdapter
import com.prashiskshan.presentation.leaderboard.LeaderboardViewModel

class StudentDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDashboardBinding
    private lateinit var eventAdapter: EventAdapter
    private lateinit var leaderboardAdapter: LeaderboardAdapter
    private val leaderboardViewModel: LeaderboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupListeners()
        setupEventsSection()
        setupLeaderboardSection()
        observeViewModel()
    }

    private fun observeViewModel() {
        leaderboardViewModel.leaderboardItems.observe(this) { items ->
            if (items.isNullOrEmpty()) {
                binding.rvLeaderboard.visibility = View.GONE
            } else {
                binding.rvLeaderboard.visibility = View.VISIBLE
                leaderboardAdapter.submitList(items.take(5)) // Show top 5 on dashboard
            }
        }

        leaderboardViewModel.isLoading.observe(this) { isLoading ->
            // Optionally show a small loading indicator for the leaderboard section
        }
    }

    private fun setupEventsSection() {
        eventAdapter = EventAdapter { _ ->
            startActivity(Intent(this, EventsActivity::class.java))
        }
        binding.rvEvents.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvEvents.adapter = eventAdapter

        val sampleEvents = listOf(
            Event("1", "Tech Fest 2024", "Hackathon", "Join us for the biggest tech event.", "Dec 15-17", "Hybrid", "Ongoing", 1234, ""),
            Event("2", "AI Challenge", "Competition", "Build innovative AI solutions.", "Jan 10-12", "Online", "Upcoming", 856, ""),
            Event("3", "Code Sprint", "Hackathon", "24-hour non-stop coding.", "Feb 5-6", "Offline", "Upcoming", 567, "")
        )
        eventAdapter.submitList(sampleEvents)
    }

    private fun setupLeaderboardSection() {
        leaderboardAdapter = LeaderboardAdapter { _ ->
            startActivity(Intent(this, LeaderboardActivity::class.java))
        }
        binding.rvLeaderboard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvLeaderboard.adapter = leaderboardAdapter
    }

    private fun setupListeners() {
        binding.ivNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        binding.ivAIChat.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }

        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.cardInternships.setOnClickListener {
            startActivity(Intent(this, InternshipListActivity::class.java))
        }

        binding.cardCourses.setOnClickListener {
            startActivity(Intent(this, CoursesActivity::class.java))
        }

        binding.cardGovtInternships.setOnClickListener {
            startActivity(Intent(this, GovtInternshipsActivity::class.java))
        }

        binding.cardAppliedInternships.setOnClickListener {
            startActivity(Intent(this, AppliedInternshipsActivity::class.java))
        }

        binding.cardLogbook.setOnClickListener {
            startActivity(Intent(this, LogbookActivity::class.java))
        }

        binding.cardMore.setOnClickListener {
            startActivity(Intent(this, MoreActivity::class.java))
        }

        binding.cardProgressAnalytics.setOnClickListener {
            startActivity(Intent(this, ProgressAnalyticsActivity::class.java))
        }
    }
}
