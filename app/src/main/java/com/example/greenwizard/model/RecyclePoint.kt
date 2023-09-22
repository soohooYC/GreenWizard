package com.example.greenwizard.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recyclePoint_table")
data class RecyclePoint (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val address: String,
    val imagePath: String? = null // Path to the image file
): Parcelable {
    // Secondary constructor without 'id' parameter
    constructor(name: String, address: String, imagePath: String) :
            this(null, name, address,imagePath)
}