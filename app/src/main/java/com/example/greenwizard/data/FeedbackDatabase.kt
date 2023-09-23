package com.example.greenwizard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.greenwizard.model.Feedback

@Database (entities = [Feedback::class], version=1, exportSchema = false)
abstract class FeedbackDatabase: RoomDatabase() {

    abstract fun feedbackDao(): FeedbackDao

    companion object{
        @Volatile
        private var INSTANCE: FeedbackDatabase? = null

        fun getDatabase(context: Context): FeedbackDatabase {
            val tempInstances = INSTANCE
            if (tempInstances != null) {
                return tempInstances
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FeedbackDatabase::class.java,
                    "feedback_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }

    }
}