package com.prashiskshan.presentation.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.databinding.CourseCardItemBinding

class CourseAdapter(
    private val onEnroll: (Course) -> Unit
) : ListAdapter<Course, CourseAdapter.CourseViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Course, newItem: Course) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = CourseCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) = holder.bind(getItem(position))

    inner class CourseViewHolder(private val binding: CourseCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Course) {
            binding.tvCourseTitle.text = item.title
            binding.tvProvider.text = "${item.provider} • ${item.domain}"
            binding.tvBadge.text = if (item.isMandatory) "Mandatory" else "Future"
            binding.btnEnroll.setOnClickListener { onEnroll(item) }
        }
    }
}


