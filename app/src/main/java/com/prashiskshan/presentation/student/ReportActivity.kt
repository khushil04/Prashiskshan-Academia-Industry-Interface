package com.prashiskshan.presentation.student

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.core.Resource
import com.prashiskshan.data.AuthRepository
import com.prashiskshan.data.model.Report
import com.prashiskshan.data.remote.StorageService
import com.prashiskshan.databinding.ActivityReportBinding
import com.prashiskshan.domain.viewmodel.InternshipViewModel
import kotlinx.coroutines.launch
import java.util.*

class ReportActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityReportBinding
    private val viewModel: InternshipViewModel by viewModels()
    private val storageService = StorageService()
    private lateinit var authRepository: AuthRepository
    private lateinit var adapter: ReportAdapter
    
    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { uploadReport(it) }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        authRepository = AuthRepository(this)
        
        setupUI()
        setupRecyclerView()
        setupObservers()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.root.findViewById(com.prashiskshan.R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Internship Reports"

        binding.fabAddReport.setOnClickListener {
            filePickerLauncher.launch("application/pdf")
        }
    }

    private fun setupRecyclerView() {
        adapter = ReportAdapter { report ->
            // Handle download/view
            Toast.makeText(this, "Opening report...", Toast.LENGTH_SHORT).show()
        }
        binding.rvReports.layoutManager = LinearLayoutManager(this)
        binding.rvReports.adapter = adapter
    }

    private fun setupObservers() {
        // In a real app, you'd fetch reports here
        // viewModel.fetchReports(authRepository.getUserId()!!)
        
        viewModel.uploadReportState.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(this, "Uploading...", Toast.LENGTH_SHORT).show()
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
        val userId = authRepository.getUserId() ?: return
        val reportId = UUID.randomUUID().toString()
        val path = "reports/$userId/$reportId.pdf"
        
        lifecycleScope.launch {
            val result = storageService.uploadFile(path, uri)
            if (result is com.prashiskshan.core.Result.Success) {
                val report = Report(
                    reportId = reportId,
                    fileUrl = result.data,
                    submittedBy = userId,
                    createdAt = System.currentTimeMillis()
                )
                viewModel.uploadReport(report)
            } else if (result is com.prashiskshan.core.Result.Error) {
                Toast.makeText(this@ReportActivity, "Storage Error: ${result.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
