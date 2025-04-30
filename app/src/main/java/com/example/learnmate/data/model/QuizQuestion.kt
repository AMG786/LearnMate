package com.example.learnmate.data.model

import com.example.learnmate.ui.model.Question
import com.google.gson.annotations.SerializedName

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    @SerializedName("correct_answer")
    val correctAnswer: String
) {
    fun toQuestion(): Question {
        return Question(
            id = 0, // Temporary ID
            number = 0, // Will be set later
            title = "Question",
            questionText = question,
            questionType = Question.QuestionType.MULTIPLE_CHOICE,
            options = options,
            correctAnswer = correctAnswer
        )
    }
}
//data class QuizQuestion(
//    val question: String,
//    val options: List<String>,
//    @SerializedName("correct_answer")
//    val correctAnswer: String
//)