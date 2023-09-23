package com.example.greenwizard.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.greenwizard.model.Feedback


@Dao
interface FeedbackDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFeedback(feedback: Feedback)

    @Query(value = "Select * From feedback_table ORDER BY date DESC")
    fun readAllData(): LiveData<List<Feedback>>

    @Query("SELECT rating FROM feedback_table")
    fun getAllRatings(): List<Float>

}