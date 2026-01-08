package com.prashiskshan.presentation.analytics

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.prashiskshan.databinding.ActivityProgressAnalyticsBinding

class ProgressAnalyticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressAnalyticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressAnalyticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Progress Analytics"

        setupInternshipChart()
        setupCourseChart()
    }

    private fun setupInternshipChart() {
        val entries = listOf(
            PieEntry(60f, "Completed"),
            PieEntry(30f, "Ongoing"),
            PieEntry(10f, "Pending")
        )

        val dataSet = PieDataSet(entries, "Internship Progress")
        dataSet.colors = listOf(
            Color.parseColor("#4CAF50"), // Green for Completed
            Color.parseColor("#2196F3"), // Blue for Ongoing
            Color.parseColor("#FF9800")  // Orange for Pending
        )
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 14f

        val data = PieData(dataSet)
        binding.progressChart.data = data
        binding.progressChart.description.isEnabled = false
        binding.progressChart.legend.isEnabled = true
        binding.progressChart.setEntryLabelColor(Color.BLACK)
        binding.progressChart.setEntryLabelTextSize(12f)
        binding.progressChart.setUsePercentValues(true)
        binding.progressChart.invalidate()
    }

    private fun setupCourseChart() {
        val entries = listOf(
            PieEntry(70f, "Completed"),
            PieEntry(20f, "In Progress"),
            PieEntry(10f, "Not Started")
        )

        val dataSet = PieDataSet(entries, "Course Progress")
        dataSet.colors = listOf(
            Color.parseColor("#4CAF50"), // Green for Completed
            Color.parseColor("#2196F3"), // Blue for In Progress
            Color.parseColor("#9E9E9E")  // Gray for Not Started
        )
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 14f

        val data = PieData(dataSet)
        binding.courseChart.data = data
        binding.courseChart.description.isEnabled = false
        binding.courseChart.legend.isEnabled = true
        binding.courseChart.setEntryLabelColor(Color.BLACK)
        binding.courseChart.setEntryLabelTextSize(12f)
        binding.courseChart.setUsePercentValues(true)
        binding.courseChart.invalidate()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

