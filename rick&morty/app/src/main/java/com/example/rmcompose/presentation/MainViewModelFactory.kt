package com.example.rmcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rmcompose.data.MainRepository
import com.example.rmcompose.domain.GetEpisodesUseCase
import javax.inject.Inject

class MainViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(), GetEpisodesUseCase(MainRepository())) as T
        }
        throw IllegalArgumentException("Unknown class")
    }
}