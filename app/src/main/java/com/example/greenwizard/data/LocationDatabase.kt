package com.example.greenwizard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.greenwizard.model.RecyclePoint
import com.example.greenwizard.model.Report

@Database(entities = [Report::class, RecyclePoint::class], version=2, exportSchema = false)
abstract class LocationDatabase: RoomDatabase() {

    abstract fun LocationDao(): LocationDao

    companion object{
        @Volatile
        private var INSTANCE: LocationDatabase? = null

        fun getDatabase(context: Context): LocationDatabase {
            val tempInstances = INSTANCE
            if (tempInstances != null) {
                return tempInstances
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
                    "location_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }

    }
}