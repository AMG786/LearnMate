package com.example.learnmate.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.learnmate.data.room.entities.Interest
/**
Created by Abdul Mueez, 04/24/2025
 */
@Dao
interface InterestDao {
    @Insert
    suspend fun insertInterest(interest: Interest)

    @Query("SELECT topicName FROM interests WHERE userId = :userId")
    suspend fun getUserInterests(userId: Int): List<String>
}