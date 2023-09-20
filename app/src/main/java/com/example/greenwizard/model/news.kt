package com.example.greenwizard.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity (tableName = "news_table")
data class News (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val newsDesc: String,
    val date: Long = System.currentTimeMillis(), // Initialize date with the current time
    val imagePath: String? = null // Path to the image file
):Parcelable{
    // Secondary constructor without 'id' parameter
    constructor(title: String, newsDesc: String, imagePath: String) : this(null, title, newsDesc, System.currentTimeMillis(), imagePath)
}


