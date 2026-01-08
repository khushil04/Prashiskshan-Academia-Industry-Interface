package com.prashiskshan.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.view.View

class FutureSkillsAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<FutureSkillsAdapter.VH>() {

    class VH(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val chip = Chip(parent.context)
        chip.layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { rightMargin = 12 }
        return VH(chip)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        (holder.view as Chip).text = items[position]
    }

    override fun getItemCount(): Int = items.size
}


