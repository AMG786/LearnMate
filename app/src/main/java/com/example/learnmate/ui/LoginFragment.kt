package com.example.learnmate.ui


import androidx.lifecycle.lifecycleScope
import com.example.learnmate.NavigationListener
import kotlinx.coroutines.launch
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.example.learnmate.R
import com.example.learnmate.data.repository.UserRepository
import com.example.learnmate.data.room.AppDatabase
import com.example.learnmate.databinding.FragmentLoginBinding

/**
Created by Abdul Mueez 04/16/2025
 */
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val motionLayout = view?.findViewById<MotionLayout>(R.id.motionLayout)
//        motionLayout?.transitionToEnd()
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize database and repository
        userRepository = UserRepository(AppDatabase.getDatabase(requireContext()).userDao())

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInputs(username, password)) {
                loginUser(username, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            (activity as? NavigationListener)?.navigateToFragment(RegisterFragment())
        }
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

    private fun loginUser(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val user = userRepository.getUser(username, password)
                if (user != null) {
                    // Login successful
                    navigateToDashboard(user.id)
                } else {
                    showLoginError()
                }
            } catch (e: Exception) {
                showLoginError()
                Log.e("LoginFragment", "Login error", e)
            }
        }
    }

    private fun navigateToDashboard(userId: Int) {
        val dashboardFragment = DashboardFragment().apply {
            arguments = Bundle().apply {
                putInt("userId", userId)
            }
        }
        (activity as? NavigationListener)?.navigateToFragment(dashboardFragment)
    }

    private fun showLoginError() {
        Toast.makeText(
            requireContext(),
            "Login failed. Check your credentials",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//class LoginFragment : Fragment() {
//    private lateinit var binding: FragmentLoginBinding
//    private val viewModel: AuthViewModel by viewModels()
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        binding = FragmentLoginBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.btnLogin.setOnClickListener {
//            val username = binding.etUsername.text.toString()
//            val password = binding.etPassword.text.toString()
//
//            if (username.isNotEmpty() && password.isNotEmpty()) {
//                viewModel.login(username, password)
//            }
//        }
//        viewModel.loginState.observe(viewLifecycleOwner) { user ->
//            user?.let {
//                // Navigate to Dashboard with user ID
//                (activity as? NavigationListener)?.navigateToFragment(DashboardFragment())
//            } ?: run {
//                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        binding.tvRegister.setOnClickListener {
//            (activity as? NavigationListener)?.navigateToFragment(RegisterFragment())
//        }
//    }

//    private fun validateInput(username: String, password: String): Boolean {
//        // Add validation logic
//        return username.isNotEmpty() && password.isNotEmpty()
//    }

//    private fun loginUser(username: String, password: String) {
//        lifecycleScope.launch {
//            val user = (activity?.application as? MyApp)?.userRepository?.login(username, password)
//            user?.let {
//                val bundle = Bundle().apply {
//                    putInt("userId", it.id)
//                }
//                (activity as? NavigationListener)?.navigateToFragment(RegisterFragment())
//            } ?: run {
//                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}
//
//class LoginFragment : Fragment() {
//
//    private lateinit var etUsername: EditText
//    private lateinit var etPassword: EditText
//    private lateinit var btnLogin: Button
//    private lateinit var tvRegister: TextView
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_login, container, false)
//
//        etUsername = view.findViewById(R.id.et_username)
//        etPassword = view.findViewById(R.id.et_password)
//        btnLogin = view.findViewById(R.id.btn_login)
//        tvRegister = view.findViewById(R.id.tv_register)
//
//        btnLogin.setOnClickListener {
//            val username = etUsername.text.toString()
//            val password = etPassword.text.toString()
//
//            if (username.isEmpty() || password.isEmpty()) {
//                Toast.makeText(requireContext(), "Please enter both username and password", Toast.LENGTH_SHORT).show()
//            } else {
//
//                (activity as? NavigationListener)?.navigateToFragment(DashboardFragment())
//            }
//        }
//
//        tvRegister.setOnClickListener {
//            (activity as? NavigationListener)?.navigateToFragment(RegisterFragment())
//        }
//
//        return view
//    }
//}