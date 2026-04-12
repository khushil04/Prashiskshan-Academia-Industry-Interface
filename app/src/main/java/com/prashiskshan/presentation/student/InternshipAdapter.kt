package com.prashiskshan.presentation.student

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.data.model.Internship
import com.prashiskshan.databinding.InternshipListItemBinding

class InternshipAdapter(
    private val onApplyClick: (Internship) -> Unit
) : ListAdapter<Internship, InternshipAdapter.InternshipViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<Internship>() {
        override fun areItemsTheSame(oldItem: Internship, newItem: Internship): Boolean =
            oldItem.internshipId == newItem.internshipId

        override fun areContentsTheSame(oldItem: Internship, newItem: Internship): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InternshipViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InternshipListItemBinding.inflate(inflater, parent, false)
        return InternshipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InternshipViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class InternshipViewHolder(
        private val binding: InternshipListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Internship) {
            binding.tvTitle.text = item.title
            binding.tvCompanyName.text = item.company
            binding.tvLocation.text = "${item.location} • ${item.domain}"
            binding.tvStatus.text = "${item.completionPercent}% Completed"
            binding.progressCompletion.progress = item.completionPercent
            binding.btnApply.setOnClickListener { onApplyClick(item) }
        }
    }
}
