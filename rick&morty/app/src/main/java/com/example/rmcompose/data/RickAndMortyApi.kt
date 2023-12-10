package com.example.rmcompose.data

import com.example.rmcompose.data.model.CharactersDTO
import com.example.rmcompose.data.model.episodes.EpisodesDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val URL = "https://rickandmortyapi.com/api/"

object RickAndMortyApi {

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val gettingCharactersApi: GettingCharactersInterface =
        retrofit.create(GettingCharactersInterface::class.java)
}
interface GettingCharactersInterface {

    @GET("character")
    suspend fun getCharacters(@Query(value = "page") page: Int): CharactersDTO

    @GET("episode/{ids}")
    suspend fun getEpisodes(@Path("ids") array: String): EpisodesDTO

}