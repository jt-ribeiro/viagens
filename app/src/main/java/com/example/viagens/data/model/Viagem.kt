package com.example.viagens.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Viagem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val destino: String,
    val dataInicio: String,
    val dataFim: String,
    val descricao: String,
    val rating: Float,
    val custo: Double,
    val imagemUri: String? = null // Novo campo para URI da imagem
)