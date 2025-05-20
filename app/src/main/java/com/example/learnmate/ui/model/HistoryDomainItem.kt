package com.example.learnmate.ui.model

// Domain model (what your app uses internally)
data class HistoryDomainItem(
    val id: String,
    val topic: String,
    val date: String,
    val score: Int,
    val totalQuestions: Int,
    val questions: List<Question>
)