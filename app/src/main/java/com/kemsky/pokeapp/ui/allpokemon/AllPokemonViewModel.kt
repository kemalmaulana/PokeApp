package com.kemsky.pokeapp.ui.allpokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kemsky.GetAllPokemonQuery
import com.kemsky.pokeapp.data.repository.PokeRepository
import com.kemsky.pokeapp.ui.allpokemon.adapter.AllPokemonDataSource
import kotlinx.coroutines.flow.Flow

class AllPokemonViewModel(private val repository: PokeRepository) : ViewModel() {

    suspend fun fetchAllPokemon(): Flow<GetAllPokemonQuery.Data> {
        return repository.getAllPokemon()
    }

    val listData = Pager(PagingConfig(pageSize = 10)) {
        AllPokemonDataSource(repository)
    }.flow.cachedIn(viewModelScope)

}