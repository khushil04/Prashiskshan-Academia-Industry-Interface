package com.prashiskshan.presentation.faculty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.core.Constants
import com.prashiskshan.core.Resource
import com.prashiskshan.data.model.Report
import com.prashiskshan.databinding.FragmentFacultyReportsBinding
import com.prashiskshan.domain.viewmodel.FacultyViewModel

class FacultyReportsFragment : Fragment() {
    private var _binding: FragmentFacultyReportsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: FacultyViewModel by viewModels()
    private lateinit var logAdapter: StudentLogAdapter
    private lateinit var reportAdapter: FacultyReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFacultyReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        observeViewModel()
        viewModel.fetchAllLogbooks()
        viewModel.fetchAllReports()
    }

    private fun setupRecyclerViews() {
        // Logbook Adapter
        logAdapter = StudentLogAdapter { logbook ->
            Toast.makeText(requireContext(), "Logbook: ${logbook.task}", Toast.LENGTH_SHORT).show()
        }
        binding.rvStudentLogs.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = logAdapter
        }

        // Report Adapter
        reportAdapter = FacultyReportAdapter(
            onDownloadClick = { report ->
                Toast.makeText(requireContext(), "Downloading report...", Toast.LENGTH_SHORT).show()
            },
            onApproveClick = { report ->
                showApprovalDialog(report)
            },
            onRejectClick = { report ->
                viewModel.updateReportStatus(report.reportId, Constants.STATUS_REJECTED)
            }
        )
        binding.rvReports.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reportAdapter
        }
    }

    private fun showApprovalDialog(report: Report) {
        AlertDialog.Builder(requireContext())
            .setTitle("Approve Report")
            .setMessage("Do you want to approve this report?")
            .setPositiveButton("Approve") { _, _ ->
                viewModel.updateReportStatus(report.reportId, Constants.STATUS_APPROVED)
            }
            .setNegativeButton("Reject") { _, _ ->
                viewModel.updateReportStatus(report.reportId, Constants.STATUS_REJECTED)
            }
            .setNeutralButton("Cancel", null)
            .show()
    }

    private fun observeViewModel() {
        viewModel.allLogbooks.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> { }
                is Resource.Success -> {
                    logAdapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.allReports.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> { }
                is Resource.Success -> {
                    reportAdapter.submitList(resource.data)
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
