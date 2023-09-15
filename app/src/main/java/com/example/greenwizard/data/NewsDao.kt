package com.example.greenwizard.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNews(news : News)

    @Query(value = "Select * FROM news_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<News>>
}