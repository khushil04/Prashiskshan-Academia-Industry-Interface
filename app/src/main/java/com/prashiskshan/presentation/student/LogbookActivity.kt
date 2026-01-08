package com.prashiskshan.presentation.student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.databinding.ActivityLogbookBinding

class LogbookActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLogbookBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogbookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Logbook"
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

