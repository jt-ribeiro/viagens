package com.example.viagens.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.viagens.data.model.Viagem
import com.example.viagens.data.repository.ViagemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViagemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ViagemRepository = ViagemRepository(application)

    // Função para inserir uma viagem
    fun inserirViagem(viagem: Viagem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.inserir(viagem)
        }
    }

    // Função para atualizar uma viagem
    fun updateViagem(viagem: Viagem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(viagem)
        }
    }

    // Função para excluir uma viagem
    fun deleteViagem(viagem: Viagem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(viagem)
        }
    }

    fun getViagemById(id: Long, onResult: (Viagem?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val viagem = repository.getViagemById(id)
            withContext(Dispatchers.Main) {
                onResult(viagem)
            }
        }
    }


    // Função para obter todas as viagens
    fun getTodasViagens(onResult: (List<Viagem>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val viagens = repository.getTodasViagens()
            withContext(Dispatchers.Main) {
                onResult(viagens)
            }
        }
    }
}
