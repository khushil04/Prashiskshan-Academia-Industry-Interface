package com.prashiskshan.presentation.govt

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.isVisible


import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.databinding.ActivityGovtInternshipsBinding

class GovtInternshipsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGovtInternshipsBinding
    private lateinit var adapter: GovtInternshipAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGovtInternshipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Govt Internships"

        setupList()
        loadData()
    }

    private fun setupList() {
        adapter = GovtInternshipAdapter(
            onApply = { openLink(it.link) },
            onSave = { saveForLater(it) }
        )
        binding.rvGovtInternships.layoutManager = LinearLayoutManager(this)
        binding.rvGovtInternships.adapter = adapter
    }

    private fun loadData() {
        binding.progress.isVisible = true
        val items = listOf(
            GovtInternship(
                id = "moe-1",
                title = "MoE Internship Programme",
                org = "Ministry of Education (MoE)",
                eligibility = "UG/PG Students",
                duration = "6-12 weeks",
                link = "https://www.education.gov.in/"
            ),
            GovtInternship(
                id = "aicte-1",
                title = "AICTE Internship Portal",
                org = "AICTE (Govt of India)",
                eligibility = "Students registered in AICTE approved institutions",
                duration = "Varies",
                link = "https://internship.aicte-india.org/"
            ),
            GovtInternship(
                id = "mojs-1",
                title = "MoJS Internship",
                org = "Ministry of Jal Shakti",
                eligibility = "UG/PG in relevant streams",
                duration = "8-12 weeks",
                link = "https://jalshakti-dowr.gov.in/"
            )
        )
        adapter.submitList(items)
        binding.progress.isVisible = false
        binding.tvEmpty.isVisible = items.isEmpty()
    }

    private fun openLink(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun saveForLater(item: GovtInternship) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val saved = prefs.getStringSet("saved_govt_internships", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        saved.add(item.id)
        prefs.edit { putStringSet("saved_govt_internships", saved) }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}


