package com.prashiskshan.presentation.industry

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.R
import com.prashiskshan.core.Constants
import com.prashiskshan.databinding.ItemApplicantBinding

class ApplicantsAdapter(
    private val onAcceptClick: (ApplicantItem) -> Unit,
    private val onRejectClick: (ApplicantItem) -> Unit
) : ListAdapter<ApplicantItem, ApplicantsAdapter.ApplicantViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicantViewHolder {
        val binding = ItemApplicantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplicantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApplicantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ApplicantViewHolder(private val binding: ItemApplicantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ApplicantItem) {
            binding.tvStudentName.text = item.studentName
            binding.tvSkills.text = "Skills: ${item.skills}"
            binding.tvStatus.text = "Status: ${item.application.status}"

            // Status color
            val statusColor = when (item.application.status) {
                Constants.STATUS_APPROVED -> ContextCompat.getColor(binding.root.context, R.color.success)
                Constants.STATUS_REJECTED -> ContextCompat.getColor(binding.root.context, R.color.error)
                else -> ContextCompat.getColor(binding.root.context, R.color.text_secondary)
            }
            binding.tvStatus.setTextColor(statusColor)

            // Buttons visibility based on status
            val isPending = item.application.status == Constants.STATUS_PENDING
            binding.btnAccept.isEnabled = isPending
            binding.btnReject.isEnabled = isPending

            binding.tvResume.setOnClickListener {
                if (item.resumeUrl.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.resumeUrl))
                    binding.root.context.startActivity(intent)
                }
            }

            binding.btnAccept.setOnClickListener { onAcceptClick(item) }
            binding.btnReject.setOnClickListener { onRejectClick(item) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ApplicantItem>() {
        override fun areItemsTheSame(oldItem: ApplicantItem, newItem: ApplicantItem): Boolean {
            return oldItem.application.applicationId == newItem.application.applicationId
        }

        override fun areContentsTheSame(oldItem: ApplicantItem, newItem: ApplicantItem): Boolean {
            return oldItem == newItem
        }
    }
}
