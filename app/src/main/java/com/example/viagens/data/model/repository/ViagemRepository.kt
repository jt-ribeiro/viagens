package com.example.viagens.data.repository

import android.app.Application
import com.example.viagens.data.model.Viagem
import com.example.viagens.data.model.database.AppDatabase

class ViagemRepository(application: Application) {

    private val viagemDao = AppDatabase.getInstance(application).viagemDao()

    // Operações com Viagem
    suspend fun inserir(viagem: Viagem) {
        viagemDao.inserir(viagem)
    }

    suspend fun update(viagem: Viagem) {
        viagemDao.update(viagem)
    }

    suspend fun delete(viagem: Viagem) {
        viagemDao.delete(viagem)
    }

    suspend fun getViagemById(id: Long): Viagem? {
        return viagemDao.getViagemById(id)
    }

    suspend fun getTodasViagens(): List<Viagem> {
        return viagemDao.getTodasViagens() ?: emptyList()
    }


}