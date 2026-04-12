package com.prashiskshan.presentation.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prashiskshan.R
import com.prashiskshan.databinding.ItemLeaderboardBinding

class LeaderboardAdapter(
    private val onItemClick: (LeaderboardItem) -> Unit
) : ListAdapter<LeaderboardItem, LeaderboardAdapter.LeaderboardViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<LeaderboardItem>() {
        override fun areItemsTheSame(oldItem: LeaderboardItem, newItem: LeaderboardItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: LeaderboardItem, newItem: LeaderboardItem) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding = ItemLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaderboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1)
    }

    inner class LeaderboardViewHolder(private val binding: ItemLeaderboardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LeaderboardItem, rank: Int) {
            binding.tvRank.text = rank.toString()
            binding.tvName.text = item.name
            binding.tvInternships.text = "${item.completedInternships} Internships Completed"
            binding.tvPoints.text = item.points.toString()
            
            // Set different badge colors for top 3
            val rankColor = when (rank) {
                1 -> R.color.rank_gold
                2 -> R.color.rank_silver
                3 -> R.color.rank_bronze
                else -> R.color.primary
            }
            binding.tvRank.backgroundTintList = ContextCompat.getColorStateList(binding.root.context, rankColor)
            
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}
