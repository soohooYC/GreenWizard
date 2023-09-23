package com.example.greenwizard.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "feedback_table")
data class Feedback (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val rating: Float,
    val comment: String,
    val date: Long = System.currentTimeMillis() // Initialize date with the current time
){
    // Secondary constructor without 'id' parameter
    constructor(rating: Float, comment: String) : this(null, rating, comment, System.currentTimeMillis())
}