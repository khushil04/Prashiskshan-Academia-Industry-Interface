package com.prashiskshan.presentation.student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.core.Constants
import com.prashiskshan.data.model.Application
import com.prashiskshan.databinding.AppliedInternshipItemBinding
import java.text.SimpleDateFormat
import java.util.*

class AppliedInternshipsAdapter(
    private val onUploadReportClick: (Application) -> Unit
) : ListAdapter<Application, AppliedInternshipsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val binding: AppliedInternshipItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(application: Application, onUploadReportClick: (Application) -> Unit) {
            binding.tvInternshipId.text = "Internship ID: #${application.internshipId.takeLast(5)}"
            
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = Date(application.appliedAt)
            binding.tvAppliedDate.text = "Applied on: ${sdf.format(date)}"
            
            binding.tvStatus.text = application.status.uppercase()
            
            // Show "Upload Report" button if the application is approved/ongoing
            if (application.status == Constants.STATUS_APPROVED || application.status == Constants.STATUS_ONGOING) {
                binding.btnUploadReport.visibility = View.VISIBLE
                binding.btnUploadReport.setOnClickListener { onUploadReportClick(application) }
            } else {
                binding.btnUploadReport.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AppliedInternshipItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onUploadReportClick)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Application>() {
        override fun areItemsTheSame(oldItem: Application, newItem: Application): Boolean {
            return oldItem.applicationId == newItem.applicationId
        }

        override fun areContentsTheSame(oldItem: Application, newItem: Application): Boolean {
            return oldItem == newItem
        }
    }
}
