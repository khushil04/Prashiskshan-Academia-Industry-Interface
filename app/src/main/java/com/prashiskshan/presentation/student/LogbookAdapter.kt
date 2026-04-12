package com.prashiskshan.presentation.student

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.data.model.Logbook
import com.prashiskshan.databinding.LogbookItemBinding
import java.text.SimpleDateFormat
import java.util.*

class LogbookAdapter : ListAdapter<Logbook, LogbookAdapter.LogbookViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<Logbook>() {
        override fun areItemsTheSame(oldItem: Logbook, newItem: Logbook): Boolean =
            oldItem.logId == newItem.logId

        override fun areContentsTheSame(oldItem: Logbook, newItem: Logbook): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogbookViewHolder {
        val binding = LogbookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LogbookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LogbookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LogbookViewHolder(private val binding: LogbookItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        fun bind(item: Logbook) {
            binding.tvLogDate.text = dateFormat.format(Date(item.date))
            binding.tvLogTask.text = "Task: ${item.task}"
            binding.tvLogLearning.text = "Learning: ${item.learning}"
        }
    }
}
