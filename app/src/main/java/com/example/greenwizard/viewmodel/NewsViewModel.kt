package com.example.greenwizard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.greenwizard.data.NewsDatabase
import com.example.greenwizard.repository.NewsRepository
import com.example.greenwizard.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<News>> // Change visibility to public
    private val repository: NewsRepository

    init {
        val newsDao = NewsDatabase.getDatabase(application).newsDao()
        repository = NewsRepository(newsDao)
        readAllData = repository.readAllData
    }

    fun addNews(news: News) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNews(news)
        }
    }

    fun updateNews(news: News) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNews(news)
        }
    }

    fun deleteNews(news: News) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNews(news)
        }
    }
}
