package com.prashiskshan.presentation.faculty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.prashiskshan.databinding.ActivityFacultyDashboardBinding

class FacultyDashboardActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityFacultyDashboardBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        supportActionBar?.title = "Faculty"

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
}

