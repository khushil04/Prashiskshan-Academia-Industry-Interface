package com.prashiskshan.presentation.student

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.core.Resource
import com.prashiskshan.core.Result
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.databinding.ActivityLogbookBinding
import com.prashiskshan.domain.viewmodel.InternshipViewModel

class LogbookActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLogbookBinding
    private val viewModel: InternshipViewModel by viewModels()
    private lateinit var authRepository: AuthRepository
    private lateinit var adapter: LogbookAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogbookBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        authRepository = AuthRepository(this)
        
        setupUI()
        setupRecyclerView()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        val userResult = authRepository.getCurrentUser()
        if (userResult is Result.Success) {
            viewModel.fetchLogbook(userResult.data.userId)
        }
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.root.findViewById(com.prashiskshan.R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Daily Logbook"

        binding.fabAddEntry.setOnClickListener {
            startActivity(Intent(this, AddLogbookActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        adapter = LogbookAdapter()
        binding.rvLogbook.layoutManager = LinearLayoutManager(this)
        binding.rvLogbook.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.logbookEntries.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show progress if needed
                }
                is Resource.Success -> {
                    adapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
