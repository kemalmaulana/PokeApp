package com.kemsky.pokeapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kemsky.pokeapp.data.repository.PokeRepository
import com.kemsky.pokeapp.data.room.PokemonDatabase
import com.kemsky.pokeapp.ui.activity.detailpokemon.DetailViewModel
import com.kemsky.pokeapp.ui.fragment.allpokemon.AllPokemonViewModel
import com.kemsky.pokeapp.ui.fragment.favpokemon.FavPokemonViewModel
import javax.inject.Singleton

@Singleton
class ViewModelFactory(
    private val repository: PokeRepository,
    private val database: PokemonDatabase,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AllPokemonViewModel::class.java) -> AllPokemonViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(
                repository,
                database
            ) as T
            modelClass.isAssignableFrom(FavPokemonViewModel::class.java) -> FavPokemonViewModel(
                repository,
                database
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

}