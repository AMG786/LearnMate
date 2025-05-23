package com.example.learnmate.data.api

import android.content.Context
import com.example.learnmate.data.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
/**
Created by Abdul Mueez, 04/24/2025
 */
object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:5000/"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Connection", "close")
                .build()
            chain.proceed(request)
        }
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
