package com.prashiskshan.presentation.common

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.core.Resource
import com.prashiskshan.data.repository.AuthRepository
import com.prashiskshan.databinding.ActivityNotificationsBinding
import com.prashiskshan.domain.viewmodel.InternshipViewModel

class NotificationsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityNotificationsBinding
    private val viewModel: InternshipViewModel by viewModels()
    private lateinit var adapter: NotificationAdapter
    private lateinit var authRepository: AuthRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        authRepository = AuthRepository(this)
        
        setupUI()
        setupRecyclerView()
        observeViewModel()
        
        binding.swipeRefresh.setOnRefreshListener {
            loadNotifications()
        }
        
        loadNotifications()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Notifications"
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        adapter = NotificationAdapter()
        binding.rvNotifications.apply {
            layoutManager = LinearLayoutManager(this@NotificationsActivity)
            adapter = this@NotificationsActivity.adapter
        }
    }

    private fun loadNotifications() {
        val userId = authRepository.getCurrentUser().getOrNull()?.userId
        if (userId != null) {
            viewModel.fetchNotifications(userId)
        } else {
            binding.swipeRefresh.isRefreshing = false
            binding.tvEmpty.visibility = View.VISIBLE
            binding.tvEmpty.text = "Please login to see notifications"
        }
    }

    private fun observeViewModel() {
        viewModel.notifications.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    if (!binding.swipeRefresh.isRefreshing) {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    binding.tvEmpty.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    val list = resource.data ?: emptyList()
                    adapter.submitList(list)
                    binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.tvEmpty.text = "Failed to load notifications"
                }
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
