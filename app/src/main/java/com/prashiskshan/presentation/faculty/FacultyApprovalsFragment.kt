package com.prashiskshan.presentation.faculty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.core.Constants
import com.prashiskshan.core.Resource
import com.prashiskshan.databinding.FragmentFacultyApprovalsBinding
import com.prashiskshan.domain.viewmodel.FacultyViewModel

class FacultyApprovalsFragment : Fragment() {
    private var _binding: FragmentFacultyApprovalsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: FacultyViewModel by viewModels()
    private lateinit var adapter: ApprovalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFacultyApprovalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.fetchPendingApplications()
    }

    private fun setupRecyclerView() {
        adapter = ApprovalAdapter(
            onApprove = { application ->
                viewModel.updateApplicationStatus(application.applicationId, Constants.STATUS_APPROVED)
            },
            onReject = { application ->
                viewModel.updateApplicationStatus(application.applicationId, Constants.STATUS_REJECTED)
            }
        )
        binding.rvApprovals.layoutManager = LinearLayoutManager(requireContext())
        binding.rvApprovals.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.pendingApplications.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show progress if needed
                }
                is Resource.Success -> {
                    adapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.updateStatus.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show progress
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Status updated successfully", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
