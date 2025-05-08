package com.example.viagens.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.viagens.data.model.Viagem

@Dao
interface ViagemDao {

    // Busca uma viagem pelo ID
    @Query("SELECT * FROM viagem WHERE id = :id")
    fun getViagemById(id: Long): Viagem

    // Insere uma nova viagem
    @Insert
    fun inserir(viagem: Viagem)

    // Atualiza uma viagem existente
    @Update
    fun update(viagem: Viagem)

    // Deleta uma viagem
    @Delete
    fun delete(viagem: Viagem)

    // Obtém todas as viagens
    @Query("SELECT * FROM viagem")
    fun getTodasViagens(): List<Viagem>
}
