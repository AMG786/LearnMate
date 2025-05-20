package com.example.learnmate.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learnmate.Resource
import com.example.learnmate.data.model.LoginResponse
import com.example.learnmate.data.model.RegisterResponse
import com.example.learnmate.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
private val repository: AuthRepository) : ViewModel() {
    private val _registerState = MutableLiveData<Resource<RegisterResponse>>()
    val registerState: LiveData<Resource<RegisterResponse>> = _registerState

private val _loginState = MutableLiveData<Resource<LoginResponse>>()
    val loginState: LiveData<Resource<LoginResponse>> = _loginState

    fun login(usernameOrEmail: String, password: String) {
        viewModelScope.launch {
            _loginState.value = Resource.Loading()
            _loginState.value = repository.login(usernameOrEmail, password)
        }
    }
    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = Resource.Loading()
            _registerState.value = repository.register(username, email, password)
        }
    }

    //    private val _loginState = MutableLiveData<User?>()
//    val loginState: LiveData<User?> = _loginState
//
//    private val _registrationState = MutableLiveData<Long>()
//    val registrationState: LiveData<Long> = _registrationState
//
//    fun registerUser(user: User) {
//        viewModelScope.launch {
//            _registrationState.value = repository.registerUser(user)
//        }
//    }
//
//    fun login(username: String, password: String) {
//        viewModelScope.launch {
//            _loginState.value = repository.login(username, password)
//        }
//    }

}