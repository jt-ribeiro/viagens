package com.example.viagens.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.viagens.data.dao.TripDao

import com.example.viagens.data.model.Trip
import com.example.viagens.data.repository.TripRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TripRepository

    init {
        val tripDao = AppDatabase.getInstance(application).tripDao()
        repository = TripRepository(tripDao)
    }

    fun insertTrip(trip: Trip) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTrip(trip)
        }
    }
}