package com.prashiskshan.presentation.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.prashiskshan.R
import com.prashiskshan.core.isValidEmail
import com.prashiskshan.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityForgotPasswordBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupListeners()
    }
    
    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.forgot_password)
    }
    
    private fun setupListeners() {
        binding.btnResetPassword.setOnClickListener {
            attemptPasswordReset()
        }
    }
    
    private fun attemptPasswordReset() {
        val email = binding.etEmail.text.toString().trim()
        
        binding.tilEmail.error = null
        
        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_email_required)
            return
        }
        
        if (!email.isValidEmail()) {
            binding.tilEmail.error = getString(R.string.error_email_invalid)
            return
        }
        
        showLoading(true)
        
        binding.root.postDelayed({
            showLoading(false)
            Snackbar.make(
                binding.root,
                "Password reset email sent to $email",
                Snackbar.LENGTH_LONG
            ).show()
        }, 1500)
    }
    
    private fun showLoading(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnResetPassword.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnResetPassword.isEnabled = true
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

