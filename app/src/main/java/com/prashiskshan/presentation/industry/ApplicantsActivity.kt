package com.prashiskshan.presentation.industry

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.databinding.ActivityApplicantsBinding
import com.prashiskshan.core.Constants

class ApplicantsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityApplicantsBinding
    private val viewModel: ApplicantsViewModel by viewModels()
    private lateinit var adapter: ApplicantsAdapter
    private lateinit var authRepository: AuthRepository
    private var currentIndustryId: String = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicantsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        authRepository = AuthRepository(this)
        val userResult = authRepository.getCurrentUser()
        if (userResult.isSuccess) {
            currentIndustryId = userResult.getOrNull()?.userId ?: ""
        }

        setupUI()
        setupRecyclerView()
        observeViewModel()
        
        if (currentIndustryId.isNotEmpty()) {
            viewModel.fetchApplicants(currentIndustryId)
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.root.findViewById(com.prashiskshan.R.id.toolbar)) // Assuming there is a toolbar, wait activity_applicants didn't have one in my previous read
        // Re-reading activity_applicants.xml showed it didn't have a toolbar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Applicants"
    }
    
    private fun setupRecyclerView() {
        adapter = ApplicantsAdapter(
            onAcceptClick = { item ->
                viewModel.updateStatus(item.application.applicationId, Constants.STATUS_APPROVED, currentIndustryId)
            },
            onRejectClick = { item ->
                viewModel.updateStatus(item.application.applicationId, Constants.STATUS_REJECTED, currentIndustryId)
            }
        )
        binding.rvApplicants.layoutManager = LinearLayoutManager(this)
        binding.rvApplicants.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.applicants.observe(this) { list ->
            adapter.submitList(list)
            binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            // Show/hide progress bar if you have one
        }

        viewModel.updateStatus.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Status updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to update status", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
