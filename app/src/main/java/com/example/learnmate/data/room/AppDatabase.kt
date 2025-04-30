package com.example.learnmate.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.learnmate.data.room.dao.UserDao
import com.example.learnmate.data.room.entities.Interest
import com.example.learnmate.data.room.entities.User

// AppDatabase.kt
@Database(
    entities = [User::class, Interest::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "learning_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}