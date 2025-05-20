package com.example.learnmate.data.model

import com.google.gson.annotations.SerializedName

data class InterestRequest(
    @SerializedName("interests") val interests: List<String>
)
