package com.example.learnmate.data.room.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
// entities/User.kt
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val username: String,
    val email: String,
    val password: String,
    val phone: String?
)