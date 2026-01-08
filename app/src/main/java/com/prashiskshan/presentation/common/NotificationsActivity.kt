package com.prashiskshan.presentation.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.databinding.ActivityNotificationsBinding

class NotificationsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityNotificationsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Notifications"
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

