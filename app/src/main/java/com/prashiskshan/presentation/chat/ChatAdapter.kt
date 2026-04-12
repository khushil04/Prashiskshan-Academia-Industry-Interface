package com.prashiskshan.presentation.chat

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.R
import com.prashiskshan.databinding.ItemChatMessageBinding

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val messages = mutableListOf<ChatMessage>()

    fun add(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    class ChatViewHolder(private val binding: ItemChatMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: ChatMessage) {
            binding.tvMessage.text = message.text
            
            val params = binding.cardMessage.layoutParams as LinearLayout.LayoutParams
            if (message.isUser) {
                params.gravity = Gravity.END
                binding.cardMessage.setCardBackgroundColor(
                    binding.root.context.getColor(R.color.primary)
                )
            } else {
                params.gravity = Gravity.START
                binding.cardMessage.setCardBackgroundColor(
                    binding.root.context.getColor(R.color.analytics_card_bg)
                )
            }
            binding.cardMessage.layoutParams = params
        }
    }
}
