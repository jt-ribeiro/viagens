package com.example.viagens.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.viagens.data.model.User
import com.example.viagens.data.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository = UserRepository(application)

    fun registerUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.registerUser(user)
        }
    }

    fun loginUser(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.loginUser(email, password)

            // ✅ Mude para Main Dispatcher antes de chamar o callback
            withContext(Dispatchers.Main) {
                onResult(result)
            }
        }
    }

    fun checkIfUserExists(email: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val exists = repository.checkIfUserExists(email)

            // ✅ Mude para Main Dispatcher antes de chamar o callback
            withContext(Dispatchers.Main) {
                onResult(exists)
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun getUserByEmail(email: String, onResult: (User?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUserByEmail(email)
            withContext(Dispatchers.Main) {
                onResult(user)
            }
        }
    }
}