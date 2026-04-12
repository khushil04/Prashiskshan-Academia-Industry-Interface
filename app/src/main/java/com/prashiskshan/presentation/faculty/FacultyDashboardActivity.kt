package com.prashiskshan.presentation.faculty

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.databinding.ActivityFacultyDashboardBinding
import com.prashiskshan.presentation.auth.LoginActivity
import com.prashiskshan.presentation.student.ProfileActivity
import com.prashiskshan.presentation.chat.ChatActivity

class FacultyDashboardActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityFacultyDashboardBinding
    private lateinit var authRepository: AuthRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        authRepository = AuthRepository(this)
        
        setupUI()
        setupListeners()
        setupTabs()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupTabs() {
        val adapter = FacultyTabsAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Dashboard"
                1 -> "Approvals"
                2 -> "Reports"
                else -> "Analytics"
            }
        }.attach()
    }
    
    private fun setupListeners() {
        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.ivAIChat.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }
    }
}
