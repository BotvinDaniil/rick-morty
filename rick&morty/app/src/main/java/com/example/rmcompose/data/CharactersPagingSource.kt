package com.example.rmcompose.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rmcompose.data.model.Result
import javax.inject.Inject

class CharactersPagingSource @Inject constructor(private val repository: MainRepository) :
    PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getCharacters(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )

            },
            onFailure = {
                LoadResult.Error(it)
            }
        )

    }
    private companion object {
        const val FIRST_PAGE = 1
    }
}