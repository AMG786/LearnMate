package com.example.learnmate.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.learnmate.data.room.entities.Interest
import com.example.learnmate.data.room.entities.User

// dao/UserDao.kt
@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUser(username: String, password: String): User?

    @Insert
    suspend fun insertInterest(interest: Interest)

    @Query("SELECT topicName FROM interests WHERE userId = :userId")
    suspend fun getUserInterests(userId: Int): List<String>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    suspend fun checkUsernameExists(username: String): Boolean
}