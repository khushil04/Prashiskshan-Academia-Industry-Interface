package com.prashiskshan.data.remote

import com.prashiskshan.data.model.GeminiRequest
import com.prashiskshan.data.model.GeminiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
//    @POST("v1beta/models/gemini-1.5-flash:generateContent")
    @POST("v1beta/models/gemini-2.5-flash:generateContent")
    fun generateContent(
        @Body body: GeminiRequest
    ): Call<GeminiResponse>
}
