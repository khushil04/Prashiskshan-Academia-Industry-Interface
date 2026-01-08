package com.prashiskshan.presentation.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
        holder.bind(getItem(position))
    }

    inner class LeaderboardViewHolder(private val binding: ItemLeaderboardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LeaderboardItem) {
            binding.tvRank.text = item.rank.toString()
            binding.tvName.text = item.name
            binding.tvCategory.text = item.category
            binding.tvPoints.text = formatPoints(item.points)
            
            // Set different badge colors for top 3
            val rankColor = when (item.rank) {
                1 -> com.prashiskshan.R.color.rank_gold
                2 -> com.prashiskshan.R.color.rank_silver
                3 -> com.prashiskshan.R.color.rank_bronze
                else -> com.prashiskshan.R.color.primary
            }
            binding.tvRank.setBackgroundResource(rankColor)
            
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
        
        private fun formatPoints(points: Int): String {
            return when {
                points >= 1000 -> String.format("%.1fK", points / 1000.0)
                else -> points.toString()
            }
        }
    }
}

