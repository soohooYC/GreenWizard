package com.example.greenwizard.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.greenwizard.R
import com.example.greenwizard.model.Feedback
import com.example.greenwizard.viewmodel.FeedbackViewModel

class addFeedback : Fragment() {

    private val feedbackViewModel: FeedbackViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_feedback, container, false)

        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val ratingView = view.findViewById<TextView>(R.id.ratingView)
        val editComment = view.findViewById<EditText>(R.id.editComment)
        val feedbackbtn = view.findViewById<Button>(R.id.feedbackbtn)

        // Set an OnRatingBarChangeListener for the RatingBar
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            // Update the TextView (ratingView) to show the rating text
            val ratingText = when (rating) {
                1f -> "Terrible"
                2f -> "Bad"
                3f -> "Okay"
                4f -> "Good"
                5f -> "Excellent"
                else -> ""
            }
            ratingView.text = ratingText
        }


        feedbackbtn.setOnClickListener {
            val rating = ratingBar.rating
            val comment = editComment.text.toString()

            if (inputCheck(rating, comment)) {
                // Create a Feedback object with the rating and comment
                val feedback = Feedback(rating, comment)

                // Insert the feedback into the Room database using the ViewModel
                feedbackViewModel.addFeedback(feedback)
                Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_LONG).show()
//                findNavController().navigate(R.id.action_addFeedback_to_listFeedback)
            } else {
                Toast.makeText(requireContext(), "Please enter a valid rating and comment.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun inputCheck(rating: Float, comment: String): Boolean {
        return rating > 0 && !TextUtils.isEmpty(comment)
    }
}
