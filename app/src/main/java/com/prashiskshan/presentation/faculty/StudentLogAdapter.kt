package com.prashiskshan.presentation.faculty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.data.model.Logbook
import com.prashiskshan.databinding.LogbookItemBinding
import java.text.SimpleDateFormat
import java.util.*

class StudentLogAdapter(
    private val onItemClick: (Logbook) -> Unit
) : ListAdapter<Logbook, StudentLogAdapter.StudentLogViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentLogViewHolder {
        val binding = LogbookItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentLogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StudentLogViewHolder(
        private val binding: LogbookItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        fun bind(logbook: Logbook) {
            binding.apply {
                tvLogDate.text = dateFormat.format(Date(logbook.date))
                tvLogTask.text = "Task: ${logbook.task}"
                tvLogLearning.text = "Learning: ${logbook.learning}"
                
                root.setOnClickListener {
                    onItemClick(logbook)
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Logbook>() {
        override fun areItemsTheSame(oldItem: Logbook, newItem: Logbook): Boolean {
            return oldItem.logId == newItem.logId
        }

        override fun areContentsTheSame(oldItem: Logbook, newItem: Logbook): Boolean {
            return oldItem == newItem
        }
    }
}
