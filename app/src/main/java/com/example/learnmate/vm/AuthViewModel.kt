package com.example.learnmate.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnmate.data.repository.UserRepository
import com.example.learnmate.data.room.entities.User
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginState = MutableLiveData<User?>()
    val loginState: LiveData<User?> = _loginState

    private val _registrationState = MutableLiveData<Long>()
    val registrationState: LiveData<Long> = _registrationState

    fun registerUser(user: User) {
        viewModelScope.launch {
            _registrationState.value = repository.registerUser(user)
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = repository.login(username, password)
        }
    }
}