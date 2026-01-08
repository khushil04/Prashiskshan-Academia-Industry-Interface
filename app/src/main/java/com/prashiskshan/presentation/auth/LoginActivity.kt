package com.prashiskshan.presentation.auth

import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuthException
import com.prashiskshan.databinding.ActivityLoginBinding
import com.prashiskshan.R
import com.prashiskshan.core.hideKeyboard
import com.prashiskshan.core.isValidEmail
import com.prashiskshan.core.isValidPassword
import com.prashiskshan.data.AuthRepository
import com.prashiskshan.presentation.common.MainDashboardActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository(this)
        
        // Check if user is already logged in
        checkIfLoggedIn()

        setupListeners()
    }
    
    private fun checkIfLoggedIn() {
        if (authRepository.isLoggedIn()) {
            // User is already logged in, navigate to dashboard
            navigateToDashboard()
        }
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener { attemptLogin() }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun attemptLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        binding.tilEmail.error = null
        binding.tilPassword.error = null

        // Validate inputs
        if (email.isEmpty()) {
            binding.tilEmail.error = getString(R.string.error_email_required)
            return
        }
        if (!email.isValidEmail()) {
            binding.tilEmail.error = getString(R.string.error_email_invalid)
            return
        }
        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.error_password_required)
            return
        }
        if (!password.isValidPassword()) {
            binding.tilPassword.error = getString(R.string.error_password_short)
            return
        }

        hideKeyboard()
        showLoading(true)
        
        // Perform Firebase login
        lifecycleScope.launch {
            val result = authRepository.login(email, password)
            
            showLoading(false)
            
            result.fold(
                onSuccess = { user ->
                    // Login successful
                    Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                    navigateToDashboard()
                },
                onFailure = { exception ->
                    // Login failed - show error
                    handleLoginError(exception)
                }
            )
        }
    }
    
    private fun handleLoginError(exception: Throwable) {
        val errorMessage = when {
            exception is FirebaseAuthException -> {
                when (exception.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "Invalid email address"
                    "ERROR_USER_NOT_FOUND" -> "No account found with this email"
                    "ERROR_WRONG_PASSWORD" -> "Incorrect password"
                    "ERROR_USER_DISABLED" -> "This account has been disabled"
                    "ERROR_TOO_MANY_REQUESTS" -> "Too many failed attempts. Please try again later"
                    "ERROR_NETWORK_REQUEST_FAILED" -> "Network error. Please check your connection"
                    else -> "Login failed: ${exception.message}"
                }
            }
            exception.message?.contains("network", ignoreCase = true) == true -> {
                "Network error. Please check your internet connection"
            }
            else -> {
                "Login failed: ${exception.message ?: "Unknown error"}"
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
            binding.btnLogin.isEnabled = false
            binding.btnLogin.text = ""
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isEnabled = true
            binding.btnLogin.text = getString(R.string.login)
        }
    }
}



//package com.prashiskshan.presentation.auth
//
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.snackbar.Snackbar
//import com.prashiskshan.R
//import com.prashiskshan.core.isValidEmail
//import com.prashiskshan.core.isValidPassword
//import com.prashiskshan.databinding.ActivityLoginBinding
//
//class LoginActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityLoginBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupUI()
//        setupListeners()
//    }
//
//    private fun setupUI() {
//        // Hide action bar for login screen
//        supportActionBar?.hide()
//    }
//
//    private fun setupListeners() {
//        binding.btnLogin.setOnClickListener {
//            attemptLogin()
//        }
//
//        binding.tvForgotPassword.setOnClickListener {
//            Snackbar.make(
//                binding.root,
//                "Forgot Password feature coming soon!",
//                Snackbar.LENGTH_SHORT
//            ).show()
//        }
//
//        binding.tvRegister.setOnClickListener {
//            Snackbar.make(
//                binding.root,
//                "Registration feature coming soon!",
//                Snackbar.LENGTH_SHORT
//            ).show()
//        }
//    }
//
//    private fun attemptLogin() {
//        // Get email and password
//        val email = binding.etEmail.text.toString().trim()
//        val password = binding.etPassword.text.toString().trim()
//
//        // Reset errors
//        binding.tilEmail.error = null
//        binding.tilPassword.error = null
//
//        // Validate inputs
//        var isValid = true
//
//        if (email.isEmpty()) {
//            binding.tilEmail.error = getString(R.string.error_email_required)
//            isValid = false
//        } else if (!email.isValidEmail()) {
//            binding.tilEmail.error = getString(R.string.error_email_invalid)
//            isValid = false
//        }
//
//        if (password.isEmpty()) {
//            binding.tilPassword.error = getString(R.string.error_password_required)
//            isValid = false
//        } else if (!password.isValidPassword()) {
//            binding.tilPassword.error = getString(R.string.error_password_short)
//            isValid = false
//        }
//
//        if (!isValid) {
//            return
//        }
//
//        // Show loading
//        showLoading(true)
//
//        // Simulate login (for demo purposes)
//        binding.root.postDelayed({
//            showLoading(false)
//            Snackbar.make(
//                binding.root,
//                "Login Demo - Email: $email\nFirebase integration will be added in next phase",
//                Snackbar.LENGTH_LONG
//            ).show()
//        }, 1500)
//    }
//
//    private fun showLoading(show: Boolean) {
//        if (show) {
//            binding.progressBar.visibility = View.VISIBLE
//            binding.btnLogin.isEnabled = false
//            binding.btnLogin.text = ""
//        } else {
//            binding.progressBar.visibility = View.GONE
//            binding.btnLogin.isEnabled = true
//            binding.btnLogin.text = getString(R.string.login)
//        }
//    }
//}
