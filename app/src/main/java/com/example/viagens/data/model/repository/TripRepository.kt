package com.example.viagens.data.repository

import com.example.viagens.data.dao.TripDao
import com.example.viagens.data.model.Trip

class TripRepository(private val tripDao: TripDao) {

    suspend fun insertTrip(trip: Trip) {
        tripDao.insert(trip)
    }

    suspend fun getAllTrips(userId: String): List<Trip> {
        return tripDao.getAllTrips(userId)
    }
}