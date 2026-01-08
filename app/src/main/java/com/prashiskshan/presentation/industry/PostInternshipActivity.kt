package com.prashiskshan.presentation.industry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.databinding.ActivityPostInternshipBinding

class PostInternshipActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPostInternshipBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostInternshipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Post Internship"
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

