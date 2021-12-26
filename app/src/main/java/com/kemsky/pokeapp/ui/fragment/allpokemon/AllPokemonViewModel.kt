package com.kemsky.pokeapp.ui.fragment.allpokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kemsky.pokeapp.data.repository.PokeRepository
import com.kemsky.pokeapp.ui.fragment.allpokemon.adapter.AllPokemonDataSource

class AllPokemonViewModel(private val repository: PokeRepository) : ViewModel() {

    val listData = Pager(PagingConfig(pageSize = 10)) {
        AllPokemonDataSource(repository)
    }.flow.cachedIn(viewModelScope)

}