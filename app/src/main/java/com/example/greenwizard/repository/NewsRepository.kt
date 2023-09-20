package com.example.greenwizard.repository

import androidx.lifecycle.LiveData
import com.example.greenwizard.data.NewsDao
import com.example.greenwizard.model.News

class NewsRepository ( private val newsDao: NewsDao){

    val readAllData: LiveData<List<News>> = newsDao.readAllData()

    suspend fun addNews(news: News){
        newsDao.addNews(news)
    }

    suspend fun updateNews(news: News) {
        newsDao.updateNews(news)
    }

    suspend fun deleteNews(news: News) {
        newsDao.deleteNews(news)
    }
}

