package com.prashiskshan.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.prashiskshan.R
import com.prashiskshan.databinding.ActivityRegisterBinding
import com.prashiskshan.core.hideKeyboard
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.core.Constants
import com.prashiskshan.presentation.student.StudentDashboardActivity
import com.prashiskshan.presentation.faculty.FacultyDashboardActivity
import com.prashiskshan.presentation.industry.IndustryDashboardActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authRepository: AuthRepository
    private var selectedRole: String = Constants.USER_TYPE_STUDENT // Default role

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository(this)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener { attemptRegistration() }

        binding.tvLogin.setOnClickListener {
            finish()
        }

        binding.toggleRole.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                selectedRole = when (checkedId) {
                    R.id.btnRoleStudent -> Constants.USER_TYPE_STUDENT
                    R.id.btnRoleFaculty -> Constants.USER_TYPE_FACULTY
                    R.id.btnRoleIndustry -> Constants.USER_TYPE_INDUSTRY
                    else -> Constants.USER_TYPE_STUDENT
                }
            }
        }
    }

    private fun attemptRegistration() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        hideKeyboard()
        showLoading(true)

        lifecycleScope.launch {
            val result = authRepository.register(name, email, password, selectedRole)
            showLoading(false)

            if (result.isSuccess) {
                Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                navigateToDashboard(selectedRole)
            } else {
                val message = result.exceptionOrNull()?.message ?: "Registration failed"
                Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun navigateToDashboard(role: String) {
        val intent = when (role.lowercase()) {
            Constants.USER_TYPE_FACULTY -> Intent(this, FacultyDashboardActivity::class.java)
            Constants.USER_TYPE_INDUSTRY -> Intent(this, IndustryDashboardActivity::class.java)
            else -> Intent(this, StudentDashboardActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.btnRegister.isEnabled = !show
        binding.btnRegister.text = if (show) "" else getString(R.string.register)
    }
}
