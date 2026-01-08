package com.prashiskshan.presentation.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.R
import com.prashiskshan.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAboutBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "About"
        
        binding.tvVersion.text = "Version 1.0"
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

