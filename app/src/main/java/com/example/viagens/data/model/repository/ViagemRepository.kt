package com.example.viagens.data.repository

import android.content.Context
import com.example.viagens.data.model.Viagem
import com.example.viagens.data.model.dao.ViagemDao
import com.example.viagens.data.model.database.AppDatabase

class ViagemRepository(context: Context) {

    private val viagemDao: ViagemDao = AppDatabase.getInstance(context).viagemDao()

    // Função para inserir uma nova viagem
    fun inserir(viagem: Viagem) {
        viagemDao.inserir(viagem)
    }

    // Função para atualizar uma viagem existente
    fun update(viagem: Viagem) {
        viagemDao.update(viagem)
    }

    // Função para excluir uma viagem
    fun delete(viagem: Viagem) {
        viagemDao.delete(viagem)
    }

    // Função para buscar uma viagem por id
    fun getViagemById(id: Long): Viagem {
        return viagemDao.getViagemById(id)
    }

    // Função para buscar todas as viagens
    fun getTodasViagens(): List<Viagem> {
        return viagemDao.getTodasViagens()
    }
}
