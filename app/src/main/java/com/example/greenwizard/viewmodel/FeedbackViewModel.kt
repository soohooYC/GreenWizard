package com.example.greenwizard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.greenwizard.data.FeedbackDatabase
import com.example.greenwizard.model.Feedback
import com.example.greenwizard.repository.FeedbackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData


class FeedbackViewModel (application: Application) : AndroidViewModel(application)  {

    val readAllData: LiveData<List<Feedback>> // Change visibility to public
    private val repository: FeedbackRepository

    init {
        val feedbackDao = FeedbackDatabase.getDatabase(application).feedbackDao()
        repository = FeedbackRepository(feedbackDao)
        readAllData = repository.readAllData
    }
    fun addFeedback(feedback: Feedback) {
        viewModelScope.launch(Dispatchers.IO ) {
            repository.addFeedback(feedback)
        }
    }

    private val _averageRating = MutableLiveData<Float>()
    val averageRating: LiveData<Float>
        get() = _averageRating

    fun calculateAverageRating() {
        viewModelScope.launch {
            _averageRating.value = repository.getAverageRating()
        }
    }

}