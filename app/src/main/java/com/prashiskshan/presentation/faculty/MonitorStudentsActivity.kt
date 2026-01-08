package com.prashiskshan.presentation.faculty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.databinding.ActivityMonitorStudentsBinding

class MonitorStudentsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMonitorStudentsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonitorStudentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Monitor Students"
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

