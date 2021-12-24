package com.kemsky.pokeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.kemsky.GetDetailPokemonQuery
import com.kemsky.pokeapp.data.repository.PokeRepository
import kotlinx.coroutines.flow.Flow

class DetailViewModel(
    private val repository: PokeRepository
) : ViewModel() {

    suspend fun fetchDetailPokemon(pokeId: Int): Flow<GetDetailPokemonQuery.Data> {
        return repository.getDetailPokemon(pokeId)
    }
}