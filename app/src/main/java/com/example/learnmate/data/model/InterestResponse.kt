package com.example.learnmate.data.model

import com.google.gson.annotations.SerializedName

data class InterestResponse(
    @SerializedName("interests") val interests: List<String>,
    @SerializedName("count") val count: Int
)