package com.prashiskshan.presentation.faculty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.data.model.Report
import com.prashiskshan.databinding.ReportItemBinding
import java.text.SimpleDateFormat
import java.util.*

class FacultyReportAdapter(
    private val onDownloadClick: (Report) -> Unit,
    private val onApproveClick: (Report) -> Unit,
    private val onRejectClick: (Report) -> Unit
) : ListAdapter<Report, FacultyReportAdapter.ReportViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ReportItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReportViewHolder(private val binding: ReportItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        fun bind(report: Report) {
            binding.apply {
                tvReportName.text = "Report: ${report.internshipId.takeLast(5)}"
                tvReportDate.text = "Submitted: ${dateFormat.format(Date(report.createdAt))}"
                
                ivDownload.setOnClickListener { onDownloadClick(report) }
                
                // Note: We'll add Approve/Reject buttons to the layout or handle them via long press/dialog
                // for now, let's assume we use the existing download icon and might add buttons later.
                // To keep it simple, we can use the root click for approval dialog or add buttons.
                
                root.setOnLongClickListener {
                    onApproveClick(report)
                    true
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Report>() {
        override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean =
            oldItem.reportId == newItem.reportId

        override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean =
            oldItem == newItem
    }
}
