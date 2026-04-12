package com.prashiskshan.presentation.student

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.core.Resource
import com.prashiskshan.core.Result
import com.prashiskshan.data.model.Logbook
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.databinding.ActivityAddLogbookBinding
import com.prashiskshan.domain.viewmodel.InternshipViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddLogbookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddLogbookBinding
    private val viewModel: InternshipViewModel by viewModels()
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLogbookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository(this)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        binding.tvCurrentDate.text = "Date: ${sdf.format(Date())}"

        binding.btnSubmitLog.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
        val task = binding.etTask.text.toString().trim()
        val learning = binding.etLearning.text.toString().trim()

        if (task.isEmpty() || learning.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val userResult = authRepository.getCurrentUser()
        val studentId = if (userResult is Result.Success) userResult.data.userId else ""

        if (studentId.isEmpty()) {
            Toast.makeText(this, "Error: User not found", Toast.LENGTH_SHORT).show()
            return
        }

        val logEntry = Logbook(
            logId = UUID.randomUUID().toString(),
            studentId = studentId,
            task = task,
            learning = learning,
            date = System.currentTimeMillis()
        )

        viewModel.submitLogbook(logEntry)
    }

    private fun observeViewModel() {
        viewModel.submitLogbookState.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.btnSubmitLog.isEnabled = false
                }
                is Resource.Success -> {
                    binding.btnSubmitLog.isEnabled = true
                    Toast.makeText(this, "Logbook entry submitted successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Resource.Error -> {
                    binding.btnSubmitLog.isEnabled = true
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
