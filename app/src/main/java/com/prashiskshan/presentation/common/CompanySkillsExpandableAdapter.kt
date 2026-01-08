package com.prashiskshan.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.prashiskshan.R

class CompanySkillsExpandableAdapter(
    private val companies: List<String>,
    private val companyToSkills: Map<String, List<String>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int = companies.size
    override fun getChildrenCount(groupPosition: Int): Int =
        companyToSkills[companies[groupPosition]]?.size ?: 0
    override fun getGroup(groupPosition: Int): Any = companies[groupPosition]
    override fun getChild(groupPosition: Int, childPosition: Int): Any =
        companyToSkills[companies[groupPosition]]?.get(childPosition) ?: ""
    override fun getGroupId(groupPosition: Int): Long = groupPosition.toLong()
    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()
    override fun hasStableIds(): Boolean = false

    override fun getGroupView(
        groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup
    ): View {
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.item_company_group, parent, false)
        val tvCompany = view.findViewById<TextView>(R.id.tvCompany)
        val ivLogo = view.findViewById<ImageView>(R.id.ivLogo)
        tvCompany.text = companies[groupPosition]
        ivLogo.setImageResource(R.mipmap.ic_launcher_round) // placeholder logo
        return view
    }

    override fun getChildView(
        groupPosition: Int, childPosition: Int, isLastChild: Boolean,
        convertView: View?, parent: ViewGroup
    ): View {
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.item_skill_child, parent, false)
        val tvSkill = view.findViewById<TextView>(R.id.tvSkill)
        tvSkill.text = getChild(groupPosition, childPosition).toString()
        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = false
}


