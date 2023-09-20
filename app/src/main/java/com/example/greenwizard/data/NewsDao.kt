package com.example.greenwizard.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.greenwizard.fragments.update.UpdateNews
import com.example.greenwizard.model.News

@Dao
interface NewsDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNews(news : News)

    @Update
    suspend fun updateNews(news: News)

    @Delete
    suspend fun deleteNews(news: News)

    @Query(value = "Select * FROM news_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<News>>

}