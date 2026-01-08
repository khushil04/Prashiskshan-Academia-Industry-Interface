package com.prashiskshan.presentation.student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityReportBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Reports"
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

