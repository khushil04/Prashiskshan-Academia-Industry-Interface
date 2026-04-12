package com.prashiskshan.presentation.student

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.prashiskshan.core.Resource
import com.prashiskshan.data.model.Application
import com.prashiskshan.data.model.Report
import com.prashiskshan.data.remote.StorageService
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.databinding.ActivityAppliedInternshipsBinding
import com.prashiskshan.domain.viewmodel.InternshipViewModel
import kotlinx.coroutines.launch
import java.util.*

class AppliedInternshipsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppliedInternshipsBinding
    private val viewModel: InternshipViewModel by viewModels()
    private lateinit var adapter: AppliedInternshipsAdapter
    private lateinit var authRepository: AuthRepository
    private val storageService = StorageService()
    
    private var selectedApplication: Application? = null

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { uploadReport(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppliedInternshipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository(this)
        
        setupToolbar()
        setupRecyclerView()
        observeViewModel()
        loadAppliedInternships()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        adapter = AppliedInternshipsAdapter { application ->
            selectedApplication = application
            filePickerLauncher.launch("application/pdf")
        }
        binding.rvAppliedInternships.adapter = adapter
    }

    private fun loadAppliedInternships() {
        val studentId = authRepository.getCurrentUser().getOrNull()?.userId
        if (studentId != null) {
            viewModel.fetchAppliedInternships(studentId)
        } else {
            Toast.makeText(this, "User session not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.applications.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvEmpty.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val applications = resource.data ?: emptyList()
                    adapter.submitList(applications)
                    binding.tvEmpty.visibility = if (applications.isEmpty()) View.VISIBLE else View.GONE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, resource.message ?: "Error loading applications", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.uploadReportState.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(this, "Uploading report...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(this, "Report uploaded successfully!", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Upload failed: ${resource.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun uploadReport(uri: Uri) {
        val application = selectedApplication ?: return
        val studentId = authRepository.getCurrentUser().getOrNull()?.userId ?: return
        val reportId = UUID.randomUUID().toString()
        val path = "reports/$studentId/${application.internshipId}_report.pdf"
        
        lifecycleScope.launch {
            val result = storageService.uploadFile(path, uri)
            if (result is com.prashiskshan.core.Result.Success) {
                val report = Report(
                    reportId = reportId,
                    studentId = studentId,
                    internshipId = application.internshipId,
                    fileUrl = result.data,
                    status = "Pending",
                    submittedBy = studentId,
                    createdAt = System.currentTimeMillis()
                )
                viewModel.uploadReport(report)
            } else if (result is com.prashiskshan.core.Result.Error) {
                Toast.makeText(this@AppliedInternshipsActivity, "Storage Error: ${result.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
