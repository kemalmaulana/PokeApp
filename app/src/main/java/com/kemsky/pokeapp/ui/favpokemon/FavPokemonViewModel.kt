package com.kemsky.pokeapp.ui.favpokemon

import androidx.lifecycle.ViewModel
import com.kemsky.GetAllPokemonQuery
import com.kemsky.pokeapp.data.repository.PokeRepository
import kotlinx.coroutines.flow.Flow

class FavPokemonViewModel(private val repository: PokeRepository) : ViewModel() {

    suspend fun fetchAllPokemon(): Flow<GetAllPokemonQuery.Data> {
        return repository.getAllPokemon()
    }

}