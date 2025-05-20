package com.example.learnmate.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnmate.Resource
import com.example.learnmate.data.repository.InterestsRepository
import kotlinx.coroutines.launch

class InterestsViewModel(
    private val repository: InterestsRepository,

) : ViewModel() {
    private val _saveState = MutableLiveData<Resource<Boolean>>()
    val saveState: LiveData<Resource<Boolean>> = _saveState

    fun saveInterests(userId: Int, token: String, interests: List<String>) {
        viewModelScope.launch {
            _saveState.value = Resource.Loading()
            _saveState.value = repository.saveInterests(userId, token, interests)
        }
    }
}