package com.prashiskshan.presentation.industry

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.prashiskshan.databinding.ActivityIndustryDashboardBinding
import com.prashiskshan.presentation.student.ProfileActivity
import com.prashiskshan.data.repository.AuthRepository

class IndustryDashboardActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityIndustryDashboardBinding
    private val viewModel: IndustryDashboardViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIndustryDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupListeners()
        observeViewModel()
        
        viewModel.fetchStats()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
    
    private fun observeViewModel() {
        viewModel.stats.observe(this) { stats ->
            binding.tvCompanyName.text = stats.companyName
            binding.tvPostingsCount.text = stats.postingsCount.toString()
            binding.tvApplicantsCount.text = stats.applicantsCount.toString()
        }

        viewModel.isLoading.observe(this) { isLoading ->
            // You can show a loading indicator if needed
        }
    }
    
    private fun setupListeners() {
        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.cardPostInternship.setOnClickListener {
            startActivity(Intent(this, PostInternshipActivity::class.java))
        }
        
        binding.cardApplicants.setOnClickListener {
            startActivity(Intent(this, ApplicantsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchStats()
    }
}
