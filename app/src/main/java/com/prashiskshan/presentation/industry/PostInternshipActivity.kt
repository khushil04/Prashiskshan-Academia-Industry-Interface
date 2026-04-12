package com.prashiskshan.presentation.industry

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.core.Resource
import com.prashiskshan.core.Result
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.data.model.Internship
import com.prashiskshan.databinding.ActivityPostInternshipBinding
import com.prashiskshan.domain.viewmodel.InternshipViewModel
import java.util.UUID

class PostInternshipActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPostInternshipBinding
    private val viewModel: InternshipViewModel by viewModels()
    private lateinit var authRepository: AuthRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostInternshipBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        authRepository = AuthRepository(this)
        
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        binding.btnPost.setOnClickListener {
            validateAndPost()
        }
    }

    private fun setupObservers() {
        viewModel.postInternshipState.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.btnPost.isEnabled = false
                }
                is Resource.Success -> {
                    binding.btnPost.isEnabled = true
                    Toast.makeText(this, "Internship posted successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Resource.Error -> {
                    binding.btnPost.isEnabled = true
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateAndPost() {
        val title = binding.etTitle.text.toString().trim()
        val company = binding.etCompany.text.toString().trim()
        val location = binding.etLocation.text.toString().trim()
        val duration = binding.etDuration.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()

        if (title.isEmpty() || company.isEmpty() || location.isEmpty() || duration.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val userResult = authRepository.getCurrentUser()
        val userId = if (userResult is Result.Success) userResult.data.userId else ""

        val internship = Internship(
            internshipId = UUID.randomUUID().toString(),
            title = title,
            company = company,
            location = location,
            duration = duration,
            description = description,
            postedBy = userId
        )

        viewModel.postInternship(internship)
    }
}
