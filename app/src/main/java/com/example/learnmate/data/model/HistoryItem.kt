package com.example.learnmate.data.model

import com.example.learnmate.ui.model.Question
import com.google.android.material.color.utilities.Score
import com.google.gson.annotations.SerializedName


data class HistoryItem(
    val id: String,
    val topic: String,
    val questions: List<ApiQuestion>,
    val date: String,  // Changed from DateWrapper to String
    val score: Int,
    @SerializedName("completed_at") val completedAt: String?,  // Made nullable
    @SerializedName("totalQuestions") val totalQuestions: Int
)