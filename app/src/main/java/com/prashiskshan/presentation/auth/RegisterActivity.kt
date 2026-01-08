package com.prashiskshan.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuthException
import com.prashiskshan.R
import com.prashiskshan.core.Constants
import com.prashiskshan.core.isValidEmail
import com.prashiskshan.core.isValidPassword
import com.prashiskshan.core.hideKeyboard
import com.prashiskshan.databinding.ActivityRegisterBinding
import com.prashiskshan.data.AuthRepository
import com.prashiskshan.presentation.common.MainDashboardActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authRepository: AuthRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        authRepository = AuthRepository(this)
        
        setupUI()
        setupListeners()
    }
    
    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.register)
    }
    
    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            attemptRegister()
        }
        
        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
    
    private fun attemptRegister() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        
        // Reset errors
        binding.tilName.error = null
        binding.tilEmail.error = null
        binding.tilPassword.error = null
        binding.tilConfirmPassword.error = null
        
        var isValid = true
        
        if (name.isEmpty()) {
            binding.tilName.error = "Name is required"
            isValid = false
        }
        
        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_email_required)
            isValid = false
        } else if (!email.isValidEmail()) {
            binding.tilEmail.error = getString(R.string.error_email_invalid)
            isValid = false
        }
        
        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_password_required)
            isValid = false
        } else if (!password.isValidPassword()) {
            binding.tilPassword.error = getString(R.string.error_password_short)
            isValid = false
        }
        
        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = "Please confirm password"
            isValid = false
        } else if (password != confirmPassword) {
            binding.tilConfirmPassword.error = "Passwords do not match"
            isValid = false
        }
        
        if (!isValid) {
            return
        }
        
        hideKeyboard()
        showLoading(true)
        
        // Perform Firebase registration
        lifecycleScope.launch {
            // Default to student type - can be changed later if needed
            val result = authRepository.register(
                name = name,
                email = email,
                password = password,
                userType = Constants.USER_TYPE_STUDENT
            )
            
            showLoading(false)
            
            result.fold(
                onSuccess = { user ->
                    // Registration successful
                    Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                    navigateToDashboard()
                },
                onFailure = { exception ->
                    // Registration failed - show error
                    handleRegistrationError(exception)
                }
            )
        }
    }
    
    private fun handleRegistrationError(exception: Throwable) {
        val errorMessage = when {
            exception is FirebaseAuthException -> {
                when (exception.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "Invalid email address"
                    "ERROR_EMAIL_ALREADY_IN_USE" -> "An account with this email already exists"
                    "ERROR_WEAK_PASSWORD" -> "Password is too weak. Please use a stronger password"
                    "ERROR_NETWORK_REQUEST_FAILED" -> "Network error. Please check your connection"
                    else -> "Registration failed: ${exception.message}"
                }
            }
            exception.message?.contains("network", ignoreCase = true) == true -> {
                "Network error. Please check your internet connection"
            }
            else -> {
                "Registration failed: ${exception.message ?: "Unknown error"}"
            }
        }
        
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
    }
    
    private fun navigateToDashboard() {
        val intent = Intent(this, MainDashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun showLoading(show: Boolean) {
        if (show) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnRegister.isEnabled = true
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

