package com.prashiskshan.presentation.faculty

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.prashiskshan.databinding.FragmentFacultyDashboardBinding
import com.prashiskshan.presentation.events.Event
import com.prashiskshan.presentation.events.EventAdapter

class FacultyDashboardFragment : Fragment() {
    private var _binding: FragmentFacultyDashboardBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFacultyDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventsRecyclerView()
        setupListeners()
        loadEvents()
    }

    private fun setupEventsRecyclerView() {
        eventAdapter = EventAdapter { event ->
            // Faculty can view details or manage registrants
            Toast.makeText(requireContext(), "Event: ${event.title}", Toast.LENGTH_SHORT).show()
        }
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = eventAdapter
    }

    private fun setupListeners() {
        binding.cardPostEvent.setOnClickListener {
            startActivity(Intent(requireContext(), PostEventActivity::class.java))
        }
    }

    private fun loadEvents() {
        db.collection("events")
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Toast.makeText(requireContext(), "Error loading events", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                val events = value?.toObjects(Event::class.java) ?: emptyList()
                eventAdapter.submitList(events)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
