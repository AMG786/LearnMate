package com.example.learnmate.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnmate.Resource
import com.example.learnmate.data.api.ApiService
import com.example.learnmate.data.model.HistoryItem
import com.example.learnmate.data.TokenManager
import com.example.learnmate.ui.model.HistoryDomainItem
import com.example.learnmate.ui.model.Question
import kotlinx.coroutines.launch

//class HistoryViewModel(private val apiService: ApiService) : ViewModel() {
//    private val _historyState = MutableLiveData<Resource<List<HistoryItem>>>()
//    val historyState: LiveData<Resource<List<HistoryItem>>> = _historyState
//
//    fun fetchUserHistory(token: String) {
//        viewModelScope.launch {
//            _historyState.value = Resource.Loading()
//            try {
//                val response = apiService.getUserHistory("Bearer $token")
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        _historyState.value = Resource.Success(it)
//                    } ?: run {
//                        _historyState.value = Resource.Error("Empty response")
//                    }
//                } else {
//                    _historyState.value = Resource.Error(response.message())
//                }
//            } catch (e: Exception) {
//                _historyState.value = Resource.Error(e.message ?: "Unknown error")
//            }
//        }
//    }
//}
// Extension function to convert API models
class HistoryViewModel(private val apiService: ApiService) : ViewModel() {
    private val _historyState = MutableLiveData<Resource<List<HistoryItem>>>()
    val historyState: LiveData<Resource<List<HistoryItem>>> = _historyState

    fun fetchUserHistory(token: String) {
        viewModelScope.launch {
            _historyState.value = Resource.Loading()
            try {
                val response = apiService.getUserHistory("Bearer $token")
                if (response.isSuccessful) {
                    response.body()?.let {
                        println("API Response: $it") // Add logging
                        _historyState.value = Resource.Success(it)
                    } ?: run {
                        println("Empty response body")
                        _historyState.value = Resource.Error("Empty response")
                    }
                } else {
                    println("API Error: ${response.code()} - ${response.message()}")
                    _historyState.value = Resource.Error("API error: ${response.code()}")
                }
            } catch (e: Exception) {
                println("Exception: ${e.message}")
                e.printStackTrace()
                _historyState.value = Resource.Error("Network error: ${e.message}")
            }
        }
    }
}