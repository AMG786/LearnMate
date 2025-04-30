package com.example.learnmate.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.example.learnmate.NavigationListener
import com.example.learnmate.R
import com.example.learnmate.data.repository.UserRepository
import com.example.learnmate.data.room.AppDatabase
import com.example.learnmate.databinding.FragmentInterestSelectionBinding
import kotlinx.coroutines.launch

/**
Created by Abdul Mueez 04/16/2025
 */
class InterestSelectionFragment : Fragment() {
    private var _binding: FragmentInterestSelectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository
    private var userId: Int = -1
    private val selectedInterests = mutableSetOf<String>()
    private val maxInterests = 10
    private val interestButtons = mutableListOf<AppCompatButton>()

    companion object {
        private val availableTopics = listOf(
            "Algorithms", "Data Structures", "Web Development", "Testing",
            "Mobile Development", "Database Systems", "Machine Learning", "AI",
            "Cybersecurity", "Cloud Computing", "Game Development", "UI/UX Design",
            "DevOps", "Embedded Systems", "Networking", "Blockchain"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInterestSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = arguments?.getInt("userId", -1) ?: -1
        if (userId == -1) {
            showErrorAndNavigateBack("User not found")
            return
        }

        userRepository = UserRepository(AppDatabase.getDatabase(requireContext()).userDao())
        setupUI()
    }

    private fun setupUI() {
        setupInterestButtons()

        binding.btnNext.setOnClickListener {
            if (selectedInterests.isNotEmpty()) {
                saveInterestsAndNavigate()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please select at least one interest",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupInterestButtons() {
        // Clear previous references
        interestButtons.clear()

        // Get all buttons from layout
        val buttonIds = listOf(
            R.id.btn_algorithms_1,
            R.id.btn_data_structures_1,
            R.id.btn_web_dev_1,
            R.id.btn_testing_1,
            R.id.btn_algorithms_2,
            R.id.btn_data_structures_2,
            R.id.btn_web_dev_2,
            R.id.btn_testing_2,
            R.id.btn_algorithms_3,
            R.id.btn_data_structures_3,
            R.id.btn_web_dev_3,
            R.id.btn_testing_3,
            R.id.btn_algorithms_4,
            R.id.btn_data_structures_4,
            R.id.btn_web_dev_4,
            R.id.btn_testing_4
        )

        // Assign topics to buttons and set up click listeners
        buttonIds.forEachIndexed { index, buttonId ->
            if (index < availableTopics.size) {
                val button = requireView().findViewById<AppCompatButton>(buttonId)
                button.text = availableTopics[index]
                button.setOnClickListener { toggleInterestSelection(button) }
                interestButtons.add(button)
            }
        }
    }

    private fun toggleInterestSelection(button: AppCompatButton) {
        val interest = button.text.toString()

        if (selectedInterests.contains(interest)) {
            // Deselect
            selectedInterests.remove(interest)
            button.setBackgroundResource(R.drawable.interest_button_background)
        } else {
            // Check if we've reached maximum selections
            if (selectedInterests.size >= maxInterests) {
                Toast.makeText(
                    requireContext(),
                    "You can select up to $maxInterests topics",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            selectedInterests.add(interest)
        }

    }

    private fun saveInterestsAndNavigate() {
        lifecycleScope.launch {
            try {
                // Save interests to database
                userRepository.addUserInterest(userId, selectedInterests.toList())

                // Navigate to dashboard
                val dashboardFragment = DashboardFragment().apply {
                    arguments = Bundle().apply {
                        putInt("userId", userId)
                    }
                }
                (activity as? NavigationListener)?.navigateToFragment(dashboardFragment)
            } catch (e: Exception) {
                showErrorAndNavigateBack("Failed to save interests")
                Log.e("InterestSelection", "Error saving interests", e)
            }
        }
    }

    private fun showErrorAndNavigateBack(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        (activity as? NavigationListener)?.navigateToFragment(LoginFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}