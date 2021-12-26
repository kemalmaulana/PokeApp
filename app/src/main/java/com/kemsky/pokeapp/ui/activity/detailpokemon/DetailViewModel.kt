package com.kemsky.pokeapp.ui.activity.detailpokemon

import androidx.lifecycle.ViewModel
import com.kemsky.GetDetailPokemonQuery
import com.kemsky.pokeapp.data.repository.PokeRepository
import com.kemsky.pokeapp.data.room.PokemonDatabase
import com.kemsky.pokeapp.data.room.model.FavPokemonModel
import kotlinx.coroutines.flow.Flow

class DetailViewModel(
    private val repository: PokeRepository,
    private val database: PokemonDatabase
) : ViewModel() {

    suspend fun fetchDetailPokemon(pokeId: Int): Flow<GetDetailPokemonQuery.Data> {
        return repository.getDetailPokemon(pokeId)
    }

    suspend fun addToFavorite(pokemonId: Int, pokemonName: String) {
        if (database.openHelper.writableDatabase.isOpen) {
            val dao = database.favPokemonDao()
            val model = FavPokemonModel(pokemonId, pokemonName)
            dao.insertSingle(model)
        }
    }

    suspend fun deleteFromFavorite(pokemonId: Int, pokemonName: String) {
        if (database.openHelper.writableDatabase.isOpen) {
            val dao = database.favPokemonDao()
            val model = FavPokemonModel(pokemonId, pokemonName)
            dao.delete(model)
        }
    }

    fun getSingleFav(id: Int?): Flow<FavPokemonModel?>? {
        val dao = database.favPokemonDao()
        return id?.let { dao.getSingleFav(it) }
    }
}