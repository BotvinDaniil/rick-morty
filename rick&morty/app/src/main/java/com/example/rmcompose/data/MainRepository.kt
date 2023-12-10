package com.example.rmcompose.data



import com.example.rmcompose.data.model.Result
import com.example.rmcompose.data.model.episodes.EpisodesDTO
import kotlinx.coroutines.delay
import javax.inject.Inject



class MainRepository @Inject constructor() {

    suspend fun getCharacters(page: Int): List<Result> {
        delay(2000)
        return RickAndMortyApi.gettingCharactersApi.getCharacters(page).results
    }

    suspend fun getCharactersEpisodes(episodes: MutableList<Int>): EpisodesDTO {

        return RickAndMortyApi.gettingCharactersApi.getEpisodes(episodes.toString())
    }
}