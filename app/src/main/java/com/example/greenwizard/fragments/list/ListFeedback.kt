package com.example.greenwizard.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.greenwizard.R
import com.example.greenwizard.viewmodel.FeedbackViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import kotlinx.coroutines.launch


class listFeedback : Fragment() {
    private val feedbackViewModel: FeedbackViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list_feedback, container, false)

        val averageRatingBar = view.findViewById<RatingBar>(R.id.averageRatingBar)

        viewLifecycleOwner.lifecycleScope.launch {
            // Calculate the average rating when the view is created
            feedbackViewModel.calculateAverageRating()
            feedbackViewModel.averageRating.observe(viewLifecycleOwner, { averageRating ->
                // Update the RatingBar with the averageRating value
                averageRatingBar.rating = averageRating ?: DEFAULT_AVERAGE_RATING
            })
        }

        // RecyclerView
        val adapter = FeedbackAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.StarView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // FeedbackViewModel
        feedbackViewModel.readAllData.observe(viewLifecycleOwner, Observer { ratingList ->
            adapter.setData(ratingList)
        })

        return view

    }

    companion object {
        private const val DEFAULT_AVERAGE_RATING = 0f // Set your default average rating value here
    }

}
