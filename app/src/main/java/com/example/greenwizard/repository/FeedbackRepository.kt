package com.example.greenwizard.repository

import androidx.lifecycle.LiveData
import com.example.greenwizard.data.FeedbackDao
import com.example.greenwizard.model.Feedback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedbackRepository(private val feedbackDao: FeedbackDao) {

    val readAllData: LiveData<List<Feedback>> = feedbackDao.readAllData()

    suspend fun addFeedback(feedback: Feedback) {
        withContext(Dispatchers.IO) {
            feedbackDao.addFeedback(feedback)
        }
    }

    suspend fun getAverageRating(): Float {
        return withContext(Dispatchers.IO) {
            val ratings = feedbackDao.getAllRatings()
            if (ratings.isNotEmpty()) {
                val sum = ratings.sum()
                sum.toFloat() / ratings.size
            } else {
                0f
            }
        }
    }
}
