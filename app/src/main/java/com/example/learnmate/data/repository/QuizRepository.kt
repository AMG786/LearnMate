package com.example.learnmate.data.repository

import com.example.learnmate.ui.model.Question
import com.example.learnmate.Resource
import com.example.learnmate.data.api.ApiService
/**
Created by Abdul Mueez, 04/24/2025
 */
class QuizRepository(private val apiService: ApiService) {
    suspend fun getQuizQuestions(topic: String): Resource<List<Question>> {
        return try {
            val response = apiService.getQuiz(topic)
            if (response.isSuccessful) {
                val questions = response.body()?.quiz?.mapIndexed { index, quizQuestion ->
                    quizQuestion.toQuestion().copy(
                        id = index.toLong(),
                        number = index + 1,
                        title = "Question ${index + 1}"
                    )
                } ?: emptyList()
                Resource.Success(questions)
            } else {
                Resource.Error("API Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}