package com.prashiskshan.presentation.faculty

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FacultyTabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 4
    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> FacultyDashboardFragment()
        1 -> FacultyApprovalsFragment()
        2 -> FacultyReportsFragment()
        else -> FacultyAnalyticsFragment()
    }
}


