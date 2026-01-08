package com.prashiskshan.presentation.leaderboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.databinding.ActivityLeaderboardBinding

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaderboardBinding
    private lateinit var adapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Leaderboard / Achievements"

        setupRecyclerView()
        loadLeaderboardData()
    }

    private fun setupRecyclerView() {
        adapter = LeaderboardAdapter { item ->
            // Show details or navigate to profile
            // For now, just show a toast or navigate
        }
        binding.rvLeaderboard.layoutManager = LinearLayoutManager(this)
        binding.rvLeaderboard.adapter = adapter
    }

    private fun loadLeaderboardData() {
        val leaderboardItems = listOf(
            LeaderboardItem("1", 1, "Rajesh Kumar", 2450, "Student"),
            LeaderboardItem("2", 2, "IIT Delhi", 2280, "Institution"),
            LeaderboardItem("3", 3, "Priya Sharma", 2150, "Student"),
            LeaderboardItem("4", 4, "NIT Trichy", 1980, "Institution"),
            LeaderboardItem("5", 5, "Dr. Amit Verma", 1850, "Faculty"),
            LeaderboardItem("6", 6, "Sneha Patel", 1720, "Student"),
            LeaderboardItem("7", 7, "BITS Pilani", 1650, "Institution"),
            LeaderboardItem("8", 8, "Vikram Singh", 1520, "Student"),
            LeaderboardItem("9", 9, "Dr. Meera Reddy", 1450, "Faculty"),
            LeaderboardItem("10", 10, "IIIT Hyderabad", 1380, "Institution"),
            LeaderboardItem("11", 11, "Ananya Das", 1320, "Student"),
            LeaderboardItem("12", 12, "IIT Bombay", 1280, "Institution"),
            LeaderboardItem("13", 13, "Arjun Mehta", 1220, "Student"),
            LeaderboardItem("14", 14, "Dr. Kavita Nair", 1180, "Faculty"),
            LeaderboardItem("15", 15, "VIT Vellore", 1150, "Institution")
        )
        adapter.submitList(leaderboardItems)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

