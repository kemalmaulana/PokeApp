package com.kemsky.pokeapp.ui.allpokemon.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kemsky.GetPagedPokemonQuery
import com.kemsky.pokeapp.data.repository.PokeRepository
import kotlinx.coroutines.flow.collect

class AllPokemonDataSource(
    private val repository: PokeRepository
) : PagingSource<Int, GetPagedPokemonQuery.Pokemon_v2_pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, GetPagedPokemonQuery.Pokemon_v2_pokemon>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetPagedPokemonQuery.Pokemon_v2_pokemon> {
        try {
            val currentLoadingPageKey = params.key ?: 0
            val response = repository.getPagedAllPokemin(limit = 10, offset = currentLoadingPageKey)
            val responseData = mutableListOf<GetPagedPokemonQuery.Pokemon_v2_pokemon>()
            response.collect {
                responseData.addAll(it.pokemon_v2_pokemon)
            }

            val prevKey = if (currentLoadingPageKey == 0) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(10)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}