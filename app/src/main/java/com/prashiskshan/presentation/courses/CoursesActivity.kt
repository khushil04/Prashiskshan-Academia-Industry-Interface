package com.prashiskshan.presentation.courses

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.prashiskshan.databinding.ActivityCoursesBinding

class CoursesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoursesBinding
    private lateinit var adapter: CourseAdapter
    private val allCourses = mutableListOf<Course>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoursesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Courses"

        setupList()
        setupChips()
        loadData()
    }

    private fun setupList() {
        adapter = CourseAdapter { course ->
            val i = Intent(this, CourseWebViewActivity::class.java)
            i.putExtra("url", course.url)
            i.putExtra("title", course.title)
            startActivity(i)
        }
        binding.rvCourses.layoutManager = GridLayoutManager(this, 2)
        binding.rvCourses.adapter = adapter
    }

    private fun setupChips() {
        (binding.chipMandatory as Chip).setOnCheckedChangeListener { _, _ -> applyFilters() }
        (binding.chipFuture as Chip).setOnCheckedChangeListener { _, _ -> applyFilters() }
    }

    private fun loadData() {
        binding.progressBar.isVisible = true
        allCourses.clear()
        allCourses.addAll(
            listOf(
                Course("1","Kotlin for Android","Coursera","Android", true, "https://www.coursera.org"),
                Course("2","Jetpack Compose Basics","Udemy","Android", false, "https://www.udemy.com"),
                Course("3","SQL Fundamentals","NPTEL","Data", true, "https://nptel.ac.in"),
                Course("4","AWS Cloud Practitioner","Coursera","Cloud", false, "https://www.coursera.org")
            )
        )
        binding.progressBar.isVisible = false
        applyFilters()
    }

    private fun applyFilters() {
        val showMandatory = binding.chipMandatory.isChecked
        val showFuture = binding.chipFuture.isChecked
        val filtered = allCourses.filter { c ->
            (showMandatory && c.isMandatory) || (showFuture && !c.isMandatory) || (!showMandatory && !showFuture)
        }
        adapter.submitList(filtered)
        binding.tvEmpty.isVisible = filtered.isEmpty()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}


