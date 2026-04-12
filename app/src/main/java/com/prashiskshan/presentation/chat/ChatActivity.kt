package com.prashiskshan.presentation.chat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashiskshan.databinding.ActivityChatBinding
import com.prashiskshan.data.model.*
import com.prashiskshan.data.remote.RetrofitClient
import com.prashiskshan.utils.Parser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val adapter = ChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupListeners()

        adapter.add(
            ChatMessage(
                "Hello! I'm your Prashiskshan AI assistant. How can I help you today?",
                false
            )
        )
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        binding.rvChat.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        binding.rvChat.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnSend.setOnClickListener {
            val query = binding.etMessage.text.toString().trim()
            if (query.isNotEmpty()) {
                send(query)
                binding.etMessage.text.clear()
            }
        }
    }

    // ✅ MAIN SEND FUNCTION
    private fun send(query: String) {
        adapter.add(ChatMessage(query, true))
        binding.rvChat.smoothScrollToPosition(adapter.itemCount - 1)

        val prompt = """
            You are a helpful educational and career assistant for the "Prashiskshan" app.
            The user is asking: "$query"

            Guidelines:
            1. Suggest courses, internships (including govt).
            2. Be clear and helpful.
            3. If possible, give structured data.
            4. Be friendly and professional.
        """.trimIndent()

        val req = GeminiRequest(
            contents = listOf(Content(listOf(Part(prompt))))
        )

        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE

            val text = callAIWithRetry(req)

            binding.progressBar.visibility = View.GONE

            // Try parsing JSON if exists
            val items = Parser.parse(text)

            val reply = if (items.isNotEmpty()) {
                items.joinToString("\n\n") {
                    "📌 ${it.title}\n🏢 ${it.provider}\n⭐ ${it.type}"
                }
            } else {
                text
            }

            adapter.add(ChatMessage(reply, false))
            binding.rvChat.smoothScrollToPosition(adapter.itemCount - 1)
        }
    }

    // ✅ RETRY LOGIC (VERY IMPORTANT)
    private suspend fun callAIWithRetry(request: GeminiRequest): String {

        repeat(3) {
            try {
                val response = RetrofitClient.api.generateContent(request).awaitResponse()

                if (response.isSuccessful) {
                    return response.body()
                        ?.candidates?.firstOrNull()
                        ?.content?.parts?.firstOrNull()?.text
                        ?: "No response"
                } else if (response.code() == 503) {
                    delay(2000) // wait 2 sec and retry
                } else {
                    return "Error: ${response.code()}"
                }

            } catch (e: Exception) {
                delay(2000)
            }
        }

        return "⚠️ Server is busy. Please try again."
    }
}






//package com.prashiskshan.presentation.chat
//
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.prashiskshan.databinding.ActivityChatBinding
//import com.prashiskshan.data.model.*
//import com.prashiskshan.data.remote.RetrofitClient
//import com.prashiskshan.utils.Parser
//import kotlinx.coroutines.launch
//import retrofit2.awaitResponse
//
//class ChatActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityChatBinding
//    private val adapter = ChatAdapter()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityChatBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupToolbar()
//        setupRecyclerView()
//        setupListeners()
//
//        // Add welcome message
//        if (adapter.itemCount == 0) {
//            adapter.add(ChatMessage("Hello! I'm your Prashiskshan AI assistant. How can I help you today?", false))
//        }
//    }
//
//    private fun setupToolbar() {
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        binding.toolbar.setNavigationOnClickListener { finish() }
//    }
//
//    private fun setupRecyclerView() {
//        binding.rvChat.layoutManager = LinearLayoutManager(this).apply {
//            stackFromEnd = true
//        }
//        binding.rvChat.adapter = adapter
//    }
//
//    private fun setupListeners() {
//        binding.btnSend.setOnClickListener {
//            val query = binding.etMessage.text.toString().trim()
//            if (query.isNotEmpty()) {
//                send(query)
//                binding.etMessage.text.clear()
//            }
//        }
//    }
//
//    private fun send(query: String) {
//        adapter.add(ChatMessage(query, true))
//        binding.rvChat.smoothScrollToPosition(adapter.itemCount - 1)
//
//        binding.progressBar.visibility = View.VISIBLE
//
//        val prompt = """
//            You are a helpful educational and career assistant for the "Prashiskshan" app.
//            The user is asking: "$query"
//
//            Guidelines:
//            1. If they ask for courses or internships, provide them.
//            2. If you find specific items, you can format them as JSON if you wish, but also provide a natural language explanation.
//            3. If it's a general question, just answer it helpfully.
//            4. Be friendly and professional.
//
//            App Context: Prashiskshan helps students find internships (Govt & Private), courses, and tracks learning progress.
//        """.trimIndent()
//
//        val req = GeminiRequest(
//            contents = listOf(Content(listOf(Part(prompt))))
//        )
//
//        lifecycleScope.launch {
//            try {
//                val resp = RetrofitClient.api.generateContent(req).awaitResponse()
//                binding.progressBar.visibility = View.GONE
//
//                if (resp.isSuccessful) {
//                    val body = resp.body()
//                    val text = body?.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
//
//                    if (text.isNullOrBlank()) {
//                        val finishReason = body?.candidates?.firstOrNull()?.let { "Finish Reason: something went wrong" } ?: "No candidates"
//                        adapter.add(ChatMessage("I couldn't generate a response. Please try rephrasing your question. ($finishReason)", false))
//                    } else {
//                        // Try to parse if there's JSON, otherwise just show text
//                        val items = Parser.parse(text)
//                        val reply = if (items.isNotEmpty()) {
//                            val listText = items.joinToString("\n\n") {
//                                "📌 ${it.title}\n🏢 ${it.provider}\n⭐ ${it.type}"
//                            }
//                            // Append the AI's descriptive text if it exists outside the JSON
//                            val cleanText = text.replace(Regex("```json[\\s\\S]*?```"), "").trim()
//                            if (cleanText.isNotEmpty()) "$cleanText\n\n$listText" else listText
//                        } else {
//                            text
//                        }
//                        adapter.add(ChatMessage(reply, false))
//                    }
//                } else {
//                    val errorMsg = resp.errorBody()?.string() ?: "Error: ${resp.code()}"
//                    adapter.add(ChatMessage("Sorry, I'm having trouble connecting to the server. $errorMsg", false))
//                }
//                binding.rvChat.smoothScrollToPosition(adapter.itemCount - 1)
//            } catch (e: Exception) {
//                binding.progressBar.visibility = View.GONE
//                adapter.add(ChatMessage("Error: ${e.message}. Please check your internet connection.", false))
//                binding.rvChat.smoothScrollToPosition(adapter.itemCount - 1)
//            }
//        }
//    }
//}
