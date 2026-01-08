package com.prashiskshan.presentation.student

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.R
import com.prashiskshan.databinding.ActivityStudentDashboardBinding

class StudentDashboardActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityStudentDashboardBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupListeners()
    }
    
    private fun setupUI() {
        supportActionBar?.title = "Student Dashboard"
    }
    
    private fun setupListeners() {
        binding.cardInternships.setOnClickListener {
            startActivity(Intent(this, InternshipListActivity::class.java))
        }
        
        binding.cardLogbook.setOnClickListener {
            startActivity(Intent(this, LogbookActivity::class.java))
        }
        
        binding.cardReports.setOnClickListener {
            startActivity(Intent(this, ReportActivity::class.java))
        }
        
        binding.cardProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}

