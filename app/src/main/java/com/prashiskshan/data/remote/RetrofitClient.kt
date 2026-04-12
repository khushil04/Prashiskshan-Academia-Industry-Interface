package com.prashiskshan.data.remote

import com.prashiskshan.data.model.GeminiRequest
import kotlinx.coroutines.delay
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://generativelanguage.googleapis.com/"
    private const val API_KEY = "AIzaSyBH9SjYb72mxh7rwp7zspQSXS0iQyixINw"


//    private val interceptor = Interceptor { chain ->
//        val newUrl = chain.request().url.newBuilder()
//            .addQueryParameter("key", API_KEY)
//            .build()
//        val newReq = chain.request().newBuilder().url(newUrl).build()
//        chain.proceed(newReq)
//    }
    private val interceptor = Interceptor { chain ->
        val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()
        val request = original.newBuilder().url(url).build()
        chain.proceed(request)
    }

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(logger)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    }
}


//package com.prashiskshan.data.remote
//
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//object RetrofitClient {
//
//    private const val BASE_URL = "https://generativelanguage.googleapis.com/"
//    private const val API_KEY = "AIzaSyBH9SjYb72mxh7rwp7zspQSXS0iQyixINw"
//
//    private val interceptor = Interceptor { chain ->
//        val newUrl = chain.request().url.newBuilder()
//            .addQueryParameter("key", API_KEY)
//            .build()
//        val newReq = chain.request().newBuilder().url(newUrl).build()
//        chain.proceed(newReq)
//    }
//
//    private val logger = HttpLoggingInterceptor().apply {
//        level = HttpLoggingInterceptor.Level.BODY
//    }
//
//    private val client = OkHttpClient.Builder()
//        .addInterceptor(interceptor)
//        .addInterceptor(logger)
//        .build()
//
//    val api: ApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiService::class.java)
//    }
//}
