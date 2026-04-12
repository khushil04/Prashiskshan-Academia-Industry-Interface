package com.prashiskshan.data.model

data class GeminiRequest(val contents: List<Content>)
data class Content(val parts: List<Part>,
    val role: String = "user")
data class Part(val text: String)
