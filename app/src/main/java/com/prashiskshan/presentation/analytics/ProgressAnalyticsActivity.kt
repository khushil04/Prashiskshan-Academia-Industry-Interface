package com.prashiskshan.presentation.analytics

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.prashiskshan.R
import com.prashiskshan.databinding.ActivityProgressAnalyticsBinding

class ProgressAnalyticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressAnalyticsBinding
    private val viewModel: ProgressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressAnalyticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupWeeklyActivityChart()
        observeViewModel()
        viewModel.fetchProgress()
    }

    private fun observeViewModel() {
        viewModel.progressData.observe(this) { progress ->
            binding.tvHello.text = "Hello, ${progress.userName}"
            
            // Internship Progress
            binding.pbInternship.progress = progress.internshipProgress
            binding.tvInternshipPercent.text = "${progress.internshipProgress}%"
            
            // Logbook Progress
            binding.pbLogbook.progress = progress.logbookProgress
            binding.tvLogbookPercent.text = "${progress.logbookProgress}%"
            
            // Report Status
            binding.tvReportStatus.text = if (progress.reportSubmitted) "Yes" else "No"
            binding.tvReportStatus.setTextColor(
                ContextCompat.getColor(this, if (progress.reportSubmitted) R.color.primary else R.color.text_primary)
            )
            
            // Courses
            binding.tvCoursesCount.text = progress.coursesCompleted.toString()
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupWeeklyActivityChart() {
        // Sample data for weekly activity (can be connected to a real data source later)
        val entries = listOf(
            BarEntry(0f, 40f), // M
            BarEntry(1f, 60f), // T
            BarEntry(2f, 30f), // W
            BarEntry(3f, 80f), // T
            BarEntry(4f, 50f), // F
            BarEntry(5f, 20f), // S
            BarEntry(6f, 15f)  // S
        )

        val barDataSet = BarDataSet(entries, "Activity")
        val defaultColor = ContextCompat.getColor(this, R.color.analytics_bar_default)
        val highlightColor = ContextCompat.getColor(this, R.color.analytics_purple)
        
        barDataSet.colors = entries.mapIndexed { index, _ -> if (index == 3) highlightColor else defaultColor }
        barDataSet.setDrawValues(false)

        binding.barChart.apply {
            data = BarData(barDataSet).apply { barWidth = 0.4f }
            description.isEnabled = false
            legend.isEnabled = false
            setDrawGridBackground(false)
            setDrawBarShadow(false)
            setTouchEnabled(false)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                setDrawAxisLine(false)
                textColor = ContextCompat.getColor(this@ProgressAnalyticsActivity, R.color.text_secondary)
                valueFormatter = IndexAxisValueFormatter(listOf("M", "T", "W", "T", "F", "S", "S"))
                granularity = 1f
            }
            
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            invalidate()
        }
    }
}
