package com.prashiskshan.presentation.industry

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.databinding.ActivityIndustryDashboardBinding

class IndustryDashboardActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityIndustryDashboardBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndustryDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupListeners()
    }
    
    private fun setupUI() {
        supportActionBar?.title = "Industry Dashboard"
    }
    
    private fun setupListeners() {
        binding.cardPostInternship.setOnClickListener {
            startActivity(Intent(this, PostInternshipActivity::class.java))
        }
        
        binding.cardApplicants.setOnClickListener {
            startActivity(Intent(this, ApplicantsActivity::class.java))
        }
    }
}

