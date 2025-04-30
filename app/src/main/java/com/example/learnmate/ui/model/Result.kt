package com.example.learnmate.ui.model

data class Result(
    val id: Long,
    val number: Int,
    val questionTitle: String,
    val questionText: String="",
    val correctAnswer: String="",
    val userAnswer: String="",
    val isCorrect: Boolean=false,
    val explanation: String? = null
)