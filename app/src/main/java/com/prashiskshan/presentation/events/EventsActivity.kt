package com.prashiskshan.presentation.events

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.databinding.ActivityEventsBinding

class EventsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventsBinding
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Events / Hackathons"

        setupRecyclerView()
        loadEvents()
    }

    private fun setupRecyclerView() {
        adapter = EventAdapter { event ->
            if (event.registrationUrl.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.registrationUrl))
                startActivity(intent)
            }
        }
        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        binding.rvEvents.adapter = adapter
    }

    private fun loadEvents() {
        val events = listOf(
            Event(
                id = "1",
                title = "Tech Fest 2024",
                type = "Hackathon",
                description = "Join us for the biggest tech fest with coding competitions, workshops, and networking opportunities.",
                date = "Dec 15-17, 2024",
                location = "Hybrid",
                status = "Ongoing",
                participants = 1234,
                registrationUrl = "https://example.com/techfest2024"
            ),
            Event(
                id = "2",
                title = "AI Innovation Challenge",
                type = "Competition",
                description = "Build innovative AI solutions and compete for prizes worth ₹5 Lakhs. Open to all students.",
                date = "Jan 10-12, 2025",
                location = "Online",
                status = "Upcoming",
                participants = 856,
                registrationUrl = "https://example.com/ai-challenge"
            ),
            Event(
                id = "3",
                title = "Code Sprint 2025",
                type = "Hackathon",
                description = "24-hour coding marathon with real-world problem statements. Win exciting prizes and internships.",
                date = "Feb 5-6, 2025",
                location = "Offline",
                status = "Upcoming",
                participants = 567,
                registrationUrl = "https://example.com/codesprint"
            ),
            Event(
                id = "4",
                title = "Startup Pitch Competition",
                type = "Competition",
                description = "Pitch your startup idea to industry experts and investors. Great opportunity for entrepreneurs.",
                date = "Mar 20, 2025",
                location = "Hybrid",
                status = "Upcoming",
                participants = 234,
                registrationUrl = "https://example.com/pitch-comp"
            ),
            Event(
                id = "5",
                title = "Cybersecurity Hackathon",
                type = "Hackathon",
                description = "Test your cybersecurity skills. Find vulnerabilities, build secure systems, and win rewards.",
                date = "Apr 8-9, 2025",
                location = "Online",
                status = "Upcoming",
                participants = 445,
                registrationUrl = "https://example.com/cyber-hack"
            )
        )
        adapter.submitList(events)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

