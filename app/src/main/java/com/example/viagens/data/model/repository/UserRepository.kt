package com.example.viagens.data.model.repository

import android.content.Context
import com.example.viagens.data.model.User


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

    suspend fun updateUser(user: User) {
        userDao.insert(user) // Sobrescreve por ser mesmo ID (email)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.findByEmail(email)
    }
}