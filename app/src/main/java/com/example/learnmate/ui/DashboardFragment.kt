package com.example.learnmate.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnmate.NavigationListener
import com.example.learnmate.ui.model.Task
import com.example.learnmate.ui.adapter.TaskAdapter
import com.example.learnmate.data.repository.UserRepository
import com.example.learnmate.data.room.AppDatabase
import com.example.learnmate.databinding.FragmentDashboardBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
Created by Abdul Mueez 04/16/2025
 */
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var taskAdapter: TaskAdapter
    private val tasksList = mutableListOf<Task>()
    private lateinit var userRepository: UserRepository
    private var userId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = arguments?.getInt("userId", -1) ?: -1

        if (userId == -1) {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
            (activity as? NavigationListener)?.navigateToFragment(LoginFragment())
            return
        }

        userRepository = UserRepository(AppDatabase.getDatabase(requireContext()).userDao())

        loadUserData()
        loadTaskData()
        setupRecyclerView()
    }

    private fun loadUserData() {
        lifecycleScope.launch {
            try {
                val user = userRepository.getUserById(userId)
                user?.let {
                    binding.tvUsername.text = it.username
                }
                updateTaskCountText()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error loading user data", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateTaskCountText() {
        val pendingTasksCount = tasksList.count { it.status != Task.Status.COMPLETED }
        binding.tvTasksDue.text = if (pendingTasksCount > 0) {
            "You have $pendingTasksCount task${if (pendingTasksCount > 1) "s" else ""} due"
        } else {
            "You have two task due"
        }
    }
    private fun loadTaskData() {

        tasksList.clear()

        lifecycleScope.launch {
            try {
                // Fetch user's interests from database
                val interests = userRepository.getUserInterests(userId)
                if (interests.isNotEmpty()) {
                    interests.forEachIndexed { index, topic ->
                        delay(200L)
                        tasksList.add(
                            Task(
                                id = index.toLong(),
                                title = "Quiz on $topic",
                                description = "Test your knowledge about $topic",
                                status = when (index % 3) {
                                    0 -> Task.Status.PENDING
                                    1 -> Task.Status.IN_PROGRESS
                                    else -> Task.Status.COMPLETED
                                },
                                topic = topic,
                                isImportant = index == 0
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("DashboardFragment", "Error loading tasks", e)
                tasksList.clear()
                tasksList.add(
                    Task(
                        id = -1,
                        title = "Error loading tasks",
                        description = "Try again later",
                        status = Task.Status.PENDING,
                        isImportant = true
                    )
                )
            }
            taskAdapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {

        taskAdapter = TaskAdapter(tasksList) { task ->
            Toast.makeText(requireContext(), "Clicked on: ${task.title}", Toast.LENGTH_SHORT).show()
            val arg = Bundle().apply {
                putString("topic", task.topic)
            }
            (activity as? NavigationListener)?.navigateToFragment(QuizFragment().apply {
                arguments = arg
            })
        }

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskAdapter
//            setHasFixedSize(true) // Improves performance
//
//            // Apply the layout animation
//            val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
//            layoutAnimation = controller
//            scheduleLayoutAnimation() // Trigger the animation

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
