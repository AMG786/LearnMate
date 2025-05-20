package com.example.learnmate.ui


import android.content.Context
import androidx.lifecycle.lifecycleScope
import com.example.learnmate.NavigationListener
import kotlinx.coroutines.launch
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.learnmate.Resource
import com.example.learnmate.data.TokenManager
import com.example.learnmate.data.api.RetrofitInstance
import com.example.learnmate.data.model.LoginResponse
import com.example.learnmate.data.repository.AuthRepository
import com.example.learnmate.data.repository.UserRepository
import com.example.learnmate.data.room.AppDatabase
import com.example.learnmate.databinding.FragmentLoginBinding
import com.example.learnmate.vm.AuthViewModel

/**
Created by Abdul Mueez 04/16/2025
 */
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authApi = RetrofitInstance.api
        val userDao = AppDatabase.getDatabase(requireContext()).userDao()
        viewModel = AuthViewModel(AuthRepository(authApi, userDao))

        setupClickListeners()
        observeLoginState()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInputs(username, password)) {
                viewModel.login(username, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            (activity as? NavigationListener)?.navigateToFragment(RegisterFragment())
        }
    }

    private fun observeLoginState() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.btnLogin.isEnabled = false
                    //binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.btnLogin.isEnabled = true
                    //binding.progressBar.visibility = View.GONE
                    state.data?.let { handleLoginSuccess(it) }
                }
                is Resource.Error -> {
                    binding.btnLogin.isEnabled = true
                    //binding.progressBar.visibility = View.GONE
                    showError(state.message ?: "Login failed")
                }
            }
        }
    }

    private fun handleLoginSuccess(response: LoginResponse) {

        TokenManager.saveToken(requireContext(), response.token)

        val dashboardFragment = DashboardFragment().apply {
            arguments = Bundle().apply {
                putString("userId", response.userId)
            }
        }
        (activity as? NavigationListener)?.navigateToFragment(dashboardFragment)
    }

    private fun validateInputs(username: String, password: String): Boolean {
        var isValid = true
        if (username.isEmpty()) {
            binding.etUsername.error = "Username required"
            isValid = false
        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Password required"
            isValid = false
        }
        return isValid
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//class LoginFragment : Fragment() {
//    private var _binding: FragmentLoginBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var userRepository: UserRepository
//    private lateinit var viewModel: AuthViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Initialize database and repository
//        userRepository = UserRepository(AppDatabase.getDatabase(requireContext()).userDao())
//        val authApi = RetrofitInstance.api
//        val userDao = AppDatabase.getDatabase(requireContext()).userDao()
//        viewModel = AuthViewModel(AuthRepository(authApi, userDao))
//
//        setupClickListeners()
//    }
//
//    private fun setupClickListeners() {
//        binding.btnLogin.setOnClickListener {
//            val username = binding.etUsername.text.toString()
//            val password = binding.etPassword.text.toString()
//
//            if (validateInputs(username, password)) {
//                loginUser(username, password)
//            }
//        }
//
//        binding.tvRegister.setOnClickListener {
//            (activity as? NavigationListener)?.navigateToFragment(RegisterFragment())
//        }
//    }
//
//    private fun validateInputs(username: String, password: String): Boolean {
//        var isValid = true
//
//        if (username.isEmpty()) {
//            binding.etUsername.error = "Username required"
//            isValid = false
//        }
//
//        if (password.isEmpty()) {
//            binding.etPassword.error = "Password required"
//            isValid = false
//        }
//
//        return isValid
//    }
//
//    private fun loginUser(username: String, password: String) {
//        lifecycleScope.launch {
//            try {
//                viewModel.login(username, password)
//
//                val user = userRepository.getUser(username, password)
//                if (user != null) {
//                    // Login successful
//                    navigateToDashboard(user.id)
//                } else {
//                    showLoginError()
//                }
//            } catch (e: Exception) {
//                showLoginError()
//                Log.e("LoginFragment", "Login error", e)
//            }
//        }
//    }
//
//    private fun navigateToDashboard(userId: Int) {
//        val dashboardFragment = DashboardFragment().apply {
//            arguments = Bundle().apply {
//                putInt("userId", userId)
//            }
//        }
//        (activity as? NavigationListener)?.navigateToFragment(dashboardFragment)
//    }
//
//    private fun showLoginError() {
//        Toast.makeText(
//            requireContext(),
//            "Login failed. Check your credentials",
//            Toast.LENGTH_SHORT
//        ).show()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}