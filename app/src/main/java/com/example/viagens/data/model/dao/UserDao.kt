package com.example.viagens.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.viagens.data.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun register(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): User?
}