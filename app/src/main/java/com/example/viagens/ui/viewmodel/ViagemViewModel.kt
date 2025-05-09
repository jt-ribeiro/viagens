package com.example.viagens.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.viagens.data.model.Viagem
import com.example.viagens.data.repository.ViagemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViagemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ViagemRepository = ViagemRepository(application)

    private val _viagens = MutableLiveData<List<Viagem>>()
    val viagens: LiveData<List<Viagem>> get() = _viagens



    fun inserirViagem(viagem: Viagem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.inserir(viagem)
                Log.d("ViagemViewModel", "Viagem inserida: $viagem")
                // Recarregar todas as viagens após inserção
                val updatedViagens = repository.getTodasViagens()
                withContext(Dispatchers.Main) {
                    _viagens.value = updatedViagens
                }
            } catch (e: Exception) {
                Log.e("ViagemViewModel", "Erro ao inserir viagem", e)
            }
        }
    }

    fun updateViagem(viagem: Viagem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.update(viagem)
                Log.d("ViagemViewModel", "Viagem atualizada: $viagem")
                val updatedViagens = repository.getTodasViagens()
                withContext(Dispatchers.Main) {
                    _viagens.value = updatedViagens
                }
            } catch (e: Exception) {
                Log.e("ViagemViewModel", "Erro ao atualizar viagem", e)
            }
        }
    }

    fun deleteViagem(viagem: Viagem) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.delete(viagem)
                Log.d("ViagemViewModel", "Viagem deletada: $viagem")
                val updatedViagens = repository.getTodasViagens()
                withContext(Dispatchers.Main) {
                    _viagens.value = updatedViagens
                }
            } catch (e: Exception) {
                Log.e("ViagemViewModel", "Erro ao deletar viagem", e)
            }
        }
    }

    fun getViagemById(id: Long, onResult: (Viagem?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val viagem = repository.getViagemById(id)
                withContext(Dispatchers.Main) {
                    onResult(viagem)
                }
            } catch (e: Exception) {
                Log.e("ViagemViewModel", "Erro ao buscar viagem por ID: $id", e)
                withContext(Dispatchers.Main) {
                    onResult(null)
                }
            }
        }
    }

    fun getTodasViagens(onResult: (List<Viagem>) -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val viagens = repository.getTodasViagens()
                withContext(Dispatchers.Main) {
                    _viagens.value = viagens
                    onResult(viagens)
                }
            } catch (e: Exception) {
                Log.e("ViagemViewModel", "Erro ao buscar todas as viagens", e)
                withContext(Dispatchers.Main) {
                    _viagens.value = emptyList()
                    onResult(emptyList())
                }
            }
        }
    }



}