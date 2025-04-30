package com.example.learnmate.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnmate.ui.model.Question
import com.example.learnmate.Resource
import com.example.learnmate.data.repository.QuizRepository
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {
    private val _quizState = MutableLiveData<Resource<List<Question>>>()
    val quizState: LiveData<Resource<List<Question>>> = _quizState

    private val _navigateToResults = MutableLiveData<Boolean>()
    val navigateToResults: LiveData<Boolean> = _navigateToResults

    fun fetchQuiz(topic: String) {
        _quizState.value = Resource.Loading()
        viewModelScope.launch {
            _quizState.value = repository.getQuizQuestions(topic)
        }
    }

    fun submitQuiz(questions: List<Question>) {
        // Calculate score or process answers

        _navigateToResults.value = true
    }

    fun doneNavigating() {
        _navigateToResults.value = false
    }
}