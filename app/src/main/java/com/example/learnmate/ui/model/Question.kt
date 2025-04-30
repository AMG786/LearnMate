package com.example.learnmate.ui.model

import java.io.Serializable

data class Question(
    val id: Long,
    val number: Int,
    val title: String,
    val questionText: String,
    val questionType: QuestionType,
    val options: List<String>,
    var correctAnswer: String,
    var selectedOption: Int = -1,
    var isAnswered: Boolean = false,
    var status:Boolean = false
): Serializable {
    enum class QuestionType {
        MULTIPLE_CHOICE,
        TOGGLE
    }
}