package com.prashiskshan.presentation.govt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.databinding.GovtInternshipItemBinding

class GovtInternshipAdapter(
    private val onApply: (GovtInternship) -> Unit,
    private val onSave: (GovtInternship) -> Unit
) : ListAdapter<GovtInternship, GovtInternshipAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<GovtInternship>() {
        override fun areItemsTheSame(oldItem: GovtInternship, newItem: GovtInternship) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: GovtInternship, newItem: GovtInternship) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = GovtInternshipItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    inner class VH(private val binding: GovtInternshipItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GovtInternship) {
            binding.tvTitle.text = item.title
            binding.tvOrg.text = item.org
            binding.tvEligibility.text = "Eligibility: ${item.eligibility}"
            binding.tvDuration.text = "Duration: ${item.duration}"
            binding.btnApply.setOnClickListener { onApply(item) }
            binding.btnSave.setOnClickListener { onSave(item) }
        }
    }
}


