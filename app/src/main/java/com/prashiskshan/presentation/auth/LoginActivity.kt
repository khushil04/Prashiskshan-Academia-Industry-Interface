package com.prashiskshan.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.prashiskshan.databinding.ActivityLoginBinding
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.core.Result
import com.prashiskshan.presentation.student.StudentDashboardActivity
import com.prashiskshan.presentation.faculty.FacultyDashboardActivity
import com.prashiskshan.presentation.industry.IndustryDashboardActivity
import com.prashiskshan.R
import com.prashiskshan.core.Constants
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install Splash Screen BEFORE super.onCreate
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository(this)

        // Check if user is already logged in
        if (authRepository.isLoggedIn()) {
            val userResult = authRepository.getCurrentUser()
            if (userResult is Result.Success) {
                navigateToDashboard(userResult.data.role)
                return
            }
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedRole = when (binding.toggleRole.checkedButtonId) {
                R.id.btnRoleStudent -> Constants.USER_TYPE_STUDENT
                R.id.btnRoleFaculty -> Constants.USER_TYPE_FACULTY
                R.id.btnRoleIndustry -> Constants.USER_TYPE_INDUSTRY
                else -> Constants.USER_TYPE_STUDENT
            }

            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.isEnabled = false

            lifecycleScope.launch {
                val result = authRepository.login(email, password, selectedRole)
                
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.isEnabled = true

                when (result) {
                    is Result.Success -> {
                        navigateToDashboard(selectedRole)
                    }
                    is Result.Error -> {
                        Toast.makeText(this@LoginActivity, result.message ?: "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun navigateToDashboard(role: String?) {
        val intent = when (role?.lowercase()) {
            Constants.USER_TYPE_FACULTY -> Intent(this, FacultyDashboardActivity::class.java)
            Constants.USER_TYPE_INDUSTRY -> Intent(this, IndustryDashboardActivity::class.java)
            else -> Intent(this, StudentDashboardActivity::class.java)
        }
        intent.putExtra(Constants.EXTRA_USER_TYPE, role ?: Constants.USER_TYPE_STUDENT)
        startActivity(intent)
        finish()
    }
}
