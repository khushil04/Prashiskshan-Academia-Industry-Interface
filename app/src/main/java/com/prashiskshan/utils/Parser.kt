package com.prashiskshan.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prashiskshan.data.model.EducationItem

object Parser {

    private val gson = Gson()

    fun parse(text: String?): List<EducationItem> {
        if (text.isNullOrBlank()) return emptyList()

        return try {
            var cleaned = text
                .replace("```json", "")
                .replace("```", "")
                .trim()

            val start = cleaned.indexOf("[")
            val end = cleaned.lastIndexOf("]")
            if (start != -1 && end != -1) {
                cleaned = cleaned.substring(start, end + 1)
            }

            val type = object : TypeToken<List<EducationItem>>() {}.type
            gson.fromJson(cleaned, type)

        } catch (e: Exception) {
            emptyList()
        }
    }
}
