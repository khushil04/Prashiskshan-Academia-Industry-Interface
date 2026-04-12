package com.prashiskshan.presentation.leaderboard

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.databinding.ActivityLeaderboardBinding

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaderboardBinding
    private val viewModel: LeaderboardViewModel by viewModels()
    private lateinit var adapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Leaderboard"
    }

    private fun setupRecyclerView() {
        adapter = LeaderboardAdapter { item ->
            // Handle item click if needed
        }
        binding.rvLeaderboard.apply {
            layoutManager = LinearLayoutManager(this@LeaderboardActivity)
            adapter = this@LeaderboardActivity.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.leaderboardItems.observe(this) { items ->
            if (items.isNotEmpty()) {
                // Update Top 3 UI (static in layout, but we update data)
                updateTopThree(items.take(3))
                // Update List (excluding top 3 or showing all)
                adapter.submitList(items)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            // Show/hide progress bar if you have one
        }
    }

    private fun updateTopThree(topThree: List<LeaderboardItem>) {
        // Logic to update the names and points in the header if they are bound to views
        // This depends on the specific IDs in your activity_leaderboard.xml
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
