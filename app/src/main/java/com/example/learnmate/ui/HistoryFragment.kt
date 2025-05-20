package com.example.learnmate.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learnmate.ui.adapter.HistoryAdapter
import com.example.learnmate.ui.model.Question
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnmate.Resource
import com.example.learnmate.data.TokenManager
import com.example.learnmate.data.api.RetrofitInstance
import com.example.learnmate.databinding.FragmentHistoryBinding
import com.example.learnmate.vm.HistoryViewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = HistoryViewModel(RetrofitInstance.api)

        setupUI()
        setupRecyclerView()
        setupObservers()
        loadHistoryData()

    }

    private fun setupUI() {
        binding.materialButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        historyAdapter = HistoryAdapter(emptyList())
        binding.rvHistory.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObservers() {
        viewModel.historyState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> showLoading()
                is Resource.Success -> {
                    hideLoading()
                    state.data?.let { historyItems ->
                        println("Received ${historyItems.size} history items")
                        historyItems.forEach { println(it) }
                        var index:Int=0;
                        val allQuestions = historyItems.flatMap { historyItem ->
                            historyItem.questions.map { apiQuestion ->
                                index++;


                                apiQuestion.correctAnswer?.let {
                                    var ind=0;
                                    if(it=="A"){
                                        ind=0
                                    }else if(it=="B"){
                                        ind=1
                                    }else if(it=="C"){
                                        ind=2
                                    }else if(it=="D"){
                                        ind=3
                                    }

                                    apiQuestion.isCorrect?.let { it1 ->
                                        Question(
                                            title = "Question",
                                            questionText = apiQuestion.question,
                                            options = apiQuestion.options,
                                            correctAnswer = it,
                                            id = 1,
                                            number = index,
                                            questionType = Question.QuestionType.MULTIPLE_CHOICE,
                                            selectedOption = ind,
                                            userAnswer = apiQuestion.userAnswer.toString(),
                                            status = it1
                                        )
                                    }
                                }

                            }

                        }

                        if (allQuestions.isEmpty()) {
                            hideLoading()
                        } else {
                            historyAdapter.updateQuestions(allQuestions)
                            hideLoading()
                        }
                    }
                }
                is Resource.Error -> {
                    hideLoading()
                    println("Error occurred: ${state.message}")
                }
            }
        }
    }

    private fun loadHistoryData() {
        val token = TokenManager.getToken(requireContext()) ?: return
        viewModel.fetchUserHistory(token)
    }

    private fun showLoading() {
//        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
//        binding.progressBar.visibility = View.GONE
        System.out.println("Hiding showing")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
//
//class HistoryFragment : Fragment() {
//
//    private var _binding: FragmentHistoryBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var historyAdapter: HistoryAdapter
//    private val questionList = mutableListOf<Question>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setupUI()
//        setupRecyclerView()
//        loadHistoryData()
//    }
//
//    private fun setupUI() {
//        binding.materialButton.setOnClickListener {
//            requireActivity().onBackPressed()
//        }
//    }
//
//    private fun setupRecyclerView() {
//        historyAdapter = HistoryAdapter(questionList)
//        binding.rvHistory.apply {
//            adapter = historyAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
//    }
//
//    private fun loadHistoryData() {
//        // Sample history data - replace this with your actual data loading logic
//        val sampleQuestions = listOf(
//            Question(
//                id = 1L,
//                number = 1,
//                title = "Question 1",
//                questionText = "You can use any question here and setup a couple of answers.",
//                questionType = Question.QuestionType.MULTIPLE_CHOICE,
//                options = listOf("Answer 1", "Answer 2", "Answer 3"),
//                correctAnswer = "Answer 2",
//                selectedOption = 0,  // User selected the first option (index 0)
//                isAnswered = true,
//                status = false
//            ),
//            Question(
//                id = 2L,
//                number = 2,
//                title = "Question 2",
//                questionText = "This is another sample question for the history view.",
//                questionType = Question.QuestionType.MULTIPLE_CHOICE,
//                options = listOf("Option A", "Option B", "Option C", "Option D"),
//                correctAnswer = "Option C",
//                selectedOption = 2,  // User selected the correct option
//                isAnswered = true,
//                status = false
//            ),
//            Question(
//                id = 3L,
//                number = 3,
//                title = "Question 3",
//                questionText = "Is this a yes/no question?",
//                questionType = Question.QuestionType.TOGGLE,
//                options = listOf("Yes", "No"),
//                correctAnswer = "Yes",
//                selectedOption = 1,  // User selected "No"
//                isAnswered = true,
//                status = false
//            )
//        )
//
//        questionList.clear()
//        questionList.addAll(sampleQuestions)
//        historyAdapter.notifyDataSetChanged()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}