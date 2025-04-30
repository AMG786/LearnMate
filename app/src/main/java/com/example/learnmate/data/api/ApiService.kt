package com.example.learnmate.data.api

import com.example.learnmate.data.model.QuizResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
/**
 Created by Abdul Mueez, 04/24/2025
 */
interface ApiService {

    @GET("getQuiz")
    suspend fun getQuiz(
        @Query("topic") topic: String
    ): Response<QuizResponse>
}


