package com.kemsky.pokeapp.ui.fragment.favpokemon

import androidx.lifecycle.ViewModel
import com.kemsky.GetAllPokemonQuery
import com.kemsky.pokeapp.data.repository.PokeRepository
import com.kemsky.pokeapp.data.room.PokemonDatabase
import com.kemsky.pokeapp.data.room.model.FavPokemonModel
import kotlinx.coroutines.flow.Flow

class FavPokemonViewModel(
    private val repository: PokeRepository,
    private val database: PokemonDatabase
) : ViewModel() {

    suspend fun fetchAllPokemon(): Flow<GetAllPokemonQuery.Data> {
        return repository.getAllPokemon()
    }

    fun getListFav(): Flow<List<FavPokemonModel>> {
        val dao = database.favPokemonDao()
        return dao.getAllFav()
    }

}