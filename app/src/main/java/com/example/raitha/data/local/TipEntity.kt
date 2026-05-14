package com.example.raitha.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tips")
data class TipEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val cropType: String,
    val imageUrl: String?,
    val isKannada: Boolean,
    val district: String,
    val season: String,
    val geminiCachedResponse: String? = null
)
