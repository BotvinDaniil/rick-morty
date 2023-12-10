package com.example.rmcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rmcompose.data.CharactersPagingSource
import com.example.rmcompose.data.MainRepository
import com.example.rmcompose.data.model.Result
import com.example.rmcompose.data.model.episodes.EpisodesDTO
import com.example.rmcompose.domain.GetEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val getEpisodesUseCase: GetEpisodesUseCase
) :
    ViewModel() {

    var episodesList = MutableStateFlow(EpisodesDTO())
    lateinit var person: Result

    val pagingCharacters: Flow<PagingData<Result>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { CharactersPagingSource(repository) }
    ).flow.cachedIn(viewModelScope)

    fun getEpisodesList(episodes: MutableList<Int>) {
        viewModelScope.launch { episodesList.value = getEpisodesUseCase.getEpisodes(episodes) }
    }

}