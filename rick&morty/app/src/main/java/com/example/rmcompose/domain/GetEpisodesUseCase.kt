package com.example.rmcompose.domain

import com.example.rmcompose.data.MainRepository
import com.example.rmcompose.data.model.episodes.EpisodesDTO
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(private val repository: MainRepository) {

    suspend fun getEpisodes(episodes: MutableList<Int>): EpisodesDTO{
       return repository.getCharactersEpisodes(episodes)
    }
}