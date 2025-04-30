package com.example.learnmate

import android.app.Application
import com.example.learnmate.data.repository.UserRepository
import com.example.learnmate.data.room.AppDatabase

class MyApp : Application() {
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()
        val database = AppDatabase.getDatabase(this)
        userRepository = UserRepository(database.userDao())
    }
}