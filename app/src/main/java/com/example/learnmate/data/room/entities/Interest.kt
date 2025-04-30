package com.example.learnmate.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// entities/Interest.kt
@Entity(tableName = "interests")
data class Interest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val topicName: String
)