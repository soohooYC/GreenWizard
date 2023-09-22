package com.example.greenwizard.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity (tableName = "report_table")
data class Report (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val address: String,
    val description: String,
    val typeofWaste: String,
    val date: Long = System.currentTimeMillis(), // Initialize date with the current time
    val status: String,
    val imagePath: String? = null // Path to the image file
):Parcelable{
    // Secondary constructor without 'id' parameter
    constructor(address: String, description: String, typeofWaste: String, status: String, imagePath: String) :
            this(null, address, description, typeofWaste, System.currentTimeMillis(), status,imagePath)
}


