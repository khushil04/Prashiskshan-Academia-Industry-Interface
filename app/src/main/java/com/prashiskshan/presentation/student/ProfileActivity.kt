package com.prashiskshan.presentation.student

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.data.AuthRepository
import com.prashiskshan.databinding.ActivityProfileBinding
import com.prashiskshan.presentation.auth.LoginActivity

class ProfileActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityProfileBinding
    private lateinit var authRepository: AuthRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        authRepository = AuthRepository(this)
        
        setupUI()
        setupListeners()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"
        
        // Fill in user details if available
        val currentUser = authRepository.getCurrentUser()
        if (currentUser != null) {
            binding.tvName.text = currentUser.displayName ?: "User"
            binding.tvEmail.text = currentUser.email ?: ""
        }
    }

    private fun setupListeners() {
        binding.btnLogout.setOnClickListener {
            logoutUser()
        }
        
        binding.btnEditProfile.setOnClickListener {
            // Implementation for edit profile
        }
    }

    private fun logoutUser() {
        authRepository.logout()
        
        // Navigate back to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
