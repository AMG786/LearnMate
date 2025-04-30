package com.example.learnmate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.learnmate.data.repository.UserRepository
import com.example.learnmate.data.room.AppDatabase
import com.example.learnmate.ui.SplashScreenFragment

/**
Created by Abdul Mueez 04/16/2025
 */
class MainActivity : AppCompatActivity(), NavigationListener {
    private lateinit var appDatabase: AppDatabase
    private lateinit var userRepository: UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_LearnMate) // I have Set the main theme manually to remove splash
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the login fragment by default
        navigateToFragment(SplashScreenFragment(), false)
    }

    override fun navigateToFragment(fragment: Fragment, addToBackStack: Boolean) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_container, fragment)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }
}

interface NavigationListener {
    fun navigateToFragment(fragment: Fragment, addToBackStack: Boolean = true)
}