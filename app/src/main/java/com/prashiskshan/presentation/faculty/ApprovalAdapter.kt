package com.prashiskshan.presentation.faculty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.data.model.Application
import com.prashiskshan.databinding.ItemApprovalBinding
import java.text.SimpleDateFormat
import java.util.*

class ApprovalAdapter(
    private val onApprove: (Application) -> Unit,
    private val onReject: (Application) -> Unit
) : ListAdapter<Application, ApprovalAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemApprovalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemApprovalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(application: Application) {
            binding.tvStudentName.text = "Student ID: ${application.studentId}"
            binding.tvInternshipTitle.text = "Internship ID: ${application.internshipId}"
            
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            binding.tvAppliedDate.text = "Applied on: ${sdf.format(Date(application.appliedAt))}"

            binding.btnApprove.setOnClickListener { onApprove(application) }
            binding.btnReject.setOnClickListener { onReject(application) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Application>() {
        override fun areItemsTheSame(oldItem: Application, newItem: Application) = oldItem.applicationId == newItem.applicationId
        override fun areContentsTheSame(oldItem: Application, newItem: Application) = oldItem == newItem
    }
}
