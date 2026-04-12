package com.prashiskshan.presentation.events

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.databinding.ItemEventBinding

class EventAdapter(
    private val onEventClick: (Event) -> Unit
) : ListAdapter<Event, EventAdapter.EventViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.tvEventTitle.text = event.title
            binding.tvEventType.text = event.type
            binding.tvEventDescription.text = event.description
            binding.tvEventDate.text = event.date
            binding.tvEventStatus.text = event.status
            binding.tvEventParticipants.text = "${event.participants} Registered"
            
            if (event.registrationUrl.isNotEmpty()) {
                binding.btnRegister.visibility = View.VISIBLE
                binding.btnRegister.setOnClickListener {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.registrationUrl))
                        it.context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(it.context, "Invalid link", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                binding.btnRegister.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                onEventClick(event)
            }
        }
    }
}
