package com.prashiskshan.presentation.faculty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.databinding.ActivityApprovalsBinding

class ApprovalsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityApprovalsBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApprovalsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
    }
    
    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Approvals"
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

