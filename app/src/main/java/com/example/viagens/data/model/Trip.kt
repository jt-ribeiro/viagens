package com.example.viagens.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String, // email do utilizador logado
    val name: String,
    val description: String,
    val rating: Float,
    val imageUri: String? = null
)