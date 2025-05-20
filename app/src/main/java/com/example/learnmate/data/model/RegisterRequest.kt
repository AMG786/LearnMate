package com.example.learnmate.data.model

import com.google.gson.annotations.SerializedName

// RegisterRequest.kt
data class RegisterRequest(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)