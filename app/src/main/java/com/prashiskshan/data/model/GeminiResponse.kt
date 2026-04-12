package com.prashiskshan.data.model

data class GeminiResponse(
    val candidates: List<Candidate>? = null,
    val promptFeedback: PromptFeedback? = null
)

data class Candidate(
    val content: Content?,
    val finishReason: String?,
    val index: Int?,
    val safetyRatings: List<SafetyRating>?
)

data class PromptFeedback(
    val safetyRatings: List<SafetyRating>?
)

data class SafetyRating(
    val category: String,
    val probability: String
)



//package com.prashiskshan.data.model
//
//data class GeminiResponse(
//    val candidates: List<Candidate> = emptyList()
//)
//
//data class Candidate(
//    val content: Content?
//)
