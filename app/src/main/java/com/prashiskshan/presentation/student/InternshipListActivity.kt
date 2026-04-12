package com.prashiskshan.presentation.student

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.prashiskshan.core.Resource
import com.prashiskshan.core.Result
import com.prashiskshan.data.model.Application
import com.prashiskshan.data.model.Internship
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.databinding.ActivityInternshipListBinding
import com.prashiskshan.domain.viewmodel.InternshipViewModel
import java.util.UUID

class InternshipListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInternshipListBinding
    private lateinit var adapter: InternshipAdapter
    private val viewModel: InternshipViewModel by viewModels()
    private lateinit var authRepository: AuthRepository
    private val allInternships: MutableList<Internship> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternshipListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Internships"

        setupList()
        setupFilters()
        observeViewModel()
        
        // Fetch real data from Firestore
        viewModel.fetchInternships()
    }

    private fun setupList() {
        adapter = InternshipAdapter { internship ->
            applyForInternship(internship)
        }
        binding.rvInternships.adapter = adapter
    }

    private fun applyForInternship(internship: Internship) {
        val userResult = authRepository.getCurrentUser()
        val studentId = if (userResult is Result.Success) userResult.data.userId else ""
        
        if (studentId.isEmpty()) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val application = Application(
            applicationId = UUID.randomUUID().toString(),
            internshipId = internship.internshipId,
            studentId = studentId,
            status = "Pending",
            appliedAt = System.currentTimeMillis()
        )

        viewModel.applyInternship(application)
    }

    private fun observeViewModel() {
        // Observe internship list from Firestore
        viewModel.internships.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    allInternships.clear()
                    resource.data?.let { allInternships.addAll(it) }
                    applyFilters()
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observe application status
        viewModel.applyInternshipState.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, "Application Submitted Successfully!", Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupFilters() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etFilterDomain.addTextChangedListener(watcher)
        binding.etFilterCompany.addTextChangedListener(watcher)
        binding.etFilterLocation.addTextChangedListener(watcher)
    }

    private fun applyFilters() {
        val domain = binding.etFilterDomain.text?.toString()?.trim()?.lowercase().orEmpty()
        val company = binding.etFilterCompany.text?.toString()?.trim()?.lowercase().orEmpty()
        val location = binding.etFilterLocation.text?.toString()?.trim()?.lowercase().orEmpty()

        val filtered = allInternships.filter { i ->
            (domain.isEmpty() || i.domain.lowercase().contains(domain)) ||
            (company.isEmpty() || i.company.lowercase().contains(company)) ||
            (location.isEmpty() || i.location.lowercase().contains(location)) ||
            (domain.isEmpty() && company.isEmpty() && location.isEmpty())
        }

        adapter.submitList(filtered)
        binding.tvEmpty.isVisible = filtered.isEmpty()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
