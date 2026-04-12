package com.prashiskshan.presentation.faculty

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.prashiskshan.databinding.ActivityPostEventBinding
import com.prashiskshan.presentation.events.Event

class PostEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostEventBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val types = listOf("Hackathon", "Workshop", "Tech Fest", "Competition", "Webinar")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, types)
        binding.actvType.setAdapter(adapter)
    }

    private fun setupListeners() {
        binding.btnPost.setOnClickListener {
            validateAndPost()
        }
    }

    private fun validateAndPost() {
        val title = binding.etTitle.text.toString().trim()
        val type = binding.actvType.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val date = binding.etDate.text.toString().trim()
        val registrationUrl = binding.etRegistrationUrl.text.toString().trim()

        if (title.isEmpty() || type.isEmpty() || description.isEmpty() || date.isEmpty() || registrationUrl.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val eventId = db.collection("events").document().id
        val event = Event(
            id = eventId,
            title = title,
            type = type,
            description = description,
            date = date,
            registrationUrl = registrationUrl,
            status = "Upcoming"
        )

        binding.btnPost.isEnabled = false
        db.collection("events").document(eventId)
            .set(event)
            .addOnSuccessListener {
                Toast.makeText(this, "Event posted successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                binding.btnPost.isEnabled = true
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
