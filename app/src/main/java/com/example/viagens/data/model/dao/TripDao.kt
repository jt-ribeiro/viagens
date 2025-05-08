package com.example.viagens.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import com.example.viagens.data.model.Trip

@Dao
interface TripDao {

    @Insert
    suspend fun insert(trip: Trip)

    @Query("SELECT * FROM trips WHERE userId = :userId")
    suspend fun getAllTrips(userId: String): List<Trip>

    @Query("SELECT * FROM trips WHERE id = :tripId")
    suspend fun getTripById(tripId: Int): Trip?

    @Query("DELETE FROM trips WHERE id = :tripId")
    suspend fun deleteById(tripId: Int)
}