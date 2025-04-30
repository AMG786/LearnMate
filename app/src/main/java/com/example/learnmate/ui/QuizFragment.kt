package com.example.learnmate.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnmate.NavigationListener
import com.example.learnmate.ui.model.Question
import com.example.learnmate.ui.adapter.QuestionAdapter
import com.example.learnmate.Resource
import com.example.learnmate.data.api.RetrofitInstance
import com.example.learnmate.data.repository.QuizRepository
import com.example.learnmate.databinding.FragmentQuizBinding
import com.example.learnmate.vm.QuizViewModel
import com.example.learnmate.vm.QuizViewModelFactory


/**
Created by Abdul Mueez 04/16/2025
 */
class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuizViewModel
    private lateinit var adapter: QuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        setupObservers()

        val topic = arguments?.getString("topic") ?: "ANDROID DEVELOPMENT"

        viewModel.fetchQuiz(topic)
        binding.tvTaskTitle.text = "Quiz on $topic";
        binding.tvTaskDescription.text = "Test your knowledge of $topic Hope you love it \uD83D\uDE04";

        binding.btnSubmit.setOnClickListener {

            adapter.currentList?.let { questions ->
                viewModel.submitQuiz(questions)
            }

        }
    }

    private fun setupViewModel() {
        val factory = QuizViewModelFactory(QuizRepository(RetrofitInstance.api))
        viewModel = ViewModelProvider(this, factory)[QuizViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = QuestionAdapter { question, selectedOption ->
            // Update question in ViewModel if needed
        }
        binding.rvQuestions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@QuizFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.quizState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> showLoading()
                is Resource.Success -> showQuestions(state.data)
                is Resource.Error -> showError(state.message)
            }
        }

        viewModel.navigateToResults.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                val resultsFragment = ResultsFragment().apply {
                    adapter.currentList?.let { questions ->
                    arguments = Bundle().apply {
                        putSerializable("quizResponses", ArrayList(questions))
                    }}
                }
                (activity as? NavigationListener)?.navigateToFragment(resultsFragment)
                viewModel.doneNavigating()
            }
        }
    }

    private fun showLoading() {
       binding.progressBar.visibility = View.VISIBLE
        binding.rvQuestions.visibility = View.GONE
    }

    private fun showQuestions(questions: List<Question>?) {
        binding.progressBar.visibility = View.GONE
        questions?.let {
            adapter.submitList(it)
            binding.rvQuestions.visibility = View.VISIBLE
        } ?: showError("No questions found")
    }

    private fun showError(message: String?) {
       binding.progressBar.visibility = View.GONE
        binding.rvQuestions.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}