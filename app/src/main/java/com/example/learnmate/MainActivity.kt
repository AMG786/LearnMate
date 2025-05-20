package com.example.learnmate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.learnmate.data.repository.UserRepository
import com.example.learnmate.data.room.AppDatabase
import com.example.learnmate.ui.HistoryFragment
import com.example.learnmate.ui.ProfileFragment
import com.example.learnmate.ui.SplashScreenFragment
import com.example.learnmate.ui.UpgradeAccountFragment

import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentsClient

/**
Created by Abdul Mueez 04/16/2025
 */
class MainActivity : AppCompatActivity(), NavigationListener {
    private lateinit var appDatabase: AppDatabase
    private lateinit var userRepository: UserRepository
    companion object {
        public const val LOAD_PAYMENT_DATA_REQUEST_CODE = 991
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_LearnMate) // I have Set the main theme manually to remove splash
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the login fragment by default
        navigateToFragment(SplashScreenFragment(), false)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val paymentData = PaymentData.getFromIntent(data!!)
                        val info = paymentData?.toJson()
                        Log.d("GooglePay", "Payment info: $info")
                        // Send this info to your server
                    }
                    RESULT_CANCELED -> {
                        // The user cancelled
                    }
                    AutoResolveHelper.RESULT_ERROR -> {
                        val status = AutoResolveHelper.getStatusFromIntent(data)
                        Log.e("GooglePay", "Error: ${status?.statusMessage}")
                        System.out.println("fdfdfdffffffffffffffffffffffffff Error")
                    }
                }
            }
        }
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