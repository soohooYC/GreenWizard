package com.example.greenwizard.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel (application: Application): AndroidViewModel(application){

    private val readAllData: LiveData<List<News>>
    private val repository: NewsRepository

    init {
        val newsDao = NewsDatabase.getDatabase(application).newsDao()
        repository = NewsRepository(newsDao)
        readAllData = repository.readAllData
    }

    fun addNews(news: News){
        viewModelScope.launch(Dispatchers.IO){
            repository.addNews(news)
        }
    }
}