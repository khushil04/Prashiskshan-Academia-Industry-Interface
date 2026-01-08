package com.prashiskshan.presentation.student

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.prashiskshan.databinding.ActivityInternshipListBinding

class InternshipListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInternshipListBinding
    private lateinit var adapter: InternshipAdapter
    private val allInternships: MutableList<Internship> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternshipListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Internships"

        setupList()
        setupFilters()
        loadData()
    }

    private fun setupList() {
        adapter = InternshipAdapter { /* onApplyClick */ }
        binding.rvInternships.adapter = adapter
    }

    private fun setupFilters() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etFilterDomain.addTextChangedListener(watcher)
        binding.etFilterCompany.addTextChangedListener(watcher)
        binding.etFilterLocation.addTextChangedListener(watcher)
    }

    private fun loadData() {
        // TODO: Replace with Firestore fetch (collection: "internships")
        // Fallback sample data to visualize UI
        binding.progressBar.isVisible = true
        allInternships.clear()
        allInternships.addAll(
            listOf(
                Internship("1", "Android Developer Intern", "Google India", "Bengaluru", "Android", "Applied", 25),
                Internship("2", "Full Stack Intern", "Infosys", "Pune", "Software Development", "Approved", 60),
                Internship("3", "Data Analyst Intern", "Deloitte", "Gurugram", "Data Analytics", "Completed", 100),
                Internship("4", "Cloud Intern", "Amazon", "Hyderabad", "Cloud", "Applied", 10)
            )
        )
        binding.progressBar.isVisible = false
        applyFilters()
    }

    private fun applyFilters() {
        val domain = binding.etFilterDomain.text?.toString()?.trim()?.lowercase().orEmpty()
        val company = binding.etFilterCompany.text?.toString()?.trim()?.lowercase().orEmpty()
        val location = binding.etFilterLocation.text?.toString()?.trim()?.lowercase().orEmpty()

        val filtered = allInternships.filter { i ->
            (domain.isEmpty() || i.domain.lowercase().contains(domain)) &&
            (company.isEmpty() || i.company.lowercase().contains(company)) &&
            (location.isEmpty() || i.location.lowercase().contains(location))
        }

        adapter.submitList(filtered)
        binding.tvEmpty.isVisible = filtered.isEmpty()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

