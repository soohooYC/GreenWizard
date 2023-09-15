package com.example.greenwizard.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "news_table")
data class News (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val newsDesc: String
)


