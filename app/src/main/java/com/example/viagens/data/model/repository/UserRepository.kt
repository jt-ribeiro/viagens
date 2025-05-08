package com.example.viagens.data.model.repository

import android.content.Context
import com.example.viagens.data.model.User
import com.example.viagens.data.model.database.AppDatabase

class UserRepository(context: Context) {
    private val userDao = AppDatabase.getInstance(context).userDao()

    suspend fun registerUser(user: User) {
        userDao.register(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun checkIfUserExists(email: String): Boolean {
        return userDao.findByEmail(email) != null
    }
}