package com.example.greenwizard.data

import androidx.lifecycle.LiveData

class NewsRepository ( private val newsDao: NewsDao){

    val readAllData: LiveData<List<News>> = newsDao.readAllData()

    suspend fun addNews(news: News){
        newsDao.addNews(news)
    }
}

