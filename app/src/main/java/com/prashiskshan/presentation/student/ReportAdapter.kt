package com.prashiskshan.presentation.student

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.data.model.Report
import com.prashiskshan.databinding.ReportItemBinding
import java.text.SimpleDateFormat
import java.util.*

class ReportAdapter(
    private val onDownloadClick: (Report) -> Unit
) : ListAdapter<Report, ReportAdapter.ReportViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<Report>() {
        override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean =
            oldItem.reportId == newItem.reportId

        override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ReportItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReportViewHolder(private val binding: ReportItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        fun bind(item: Report) {
            binding.tvReportName.text = "Report_${item.reportId.take(5)}.pdf"
            binding.tvReportDate.text = "Submitted on: ${dateFormat.format(Date(item.createdAt))}"
            binding.ivDownload.setOnClickListener { onDownloadClick(item) }
        }
    }
}
