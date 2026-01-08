package com.prashiskshan.presentation.industry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.databinding.ActivityApplicantsBinding

class ApplicantsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityApplicantsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicantsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Applicants"
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

