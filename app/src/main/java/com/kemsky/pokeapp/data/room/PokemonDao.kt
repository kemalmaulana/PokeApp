package com.kemsky.pokeapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kemsky.pokeapp.data.room.model.FavPokemonModel

@Dao
interface PokemonDao {
    @Query("SELECT * FROM favpokemonmodel")
    fun getAllFav(): List<FavPokemonModel>

    @Query("SELECT * FROM favpokemonmodel WHERE id == :pokeId")
    fun getSingleFav(pokeId: Int): FavPokemonModel

    @Insert
    fun insertAll(vararg users: FavPokemonModel)

    @Delete
    fun delete(user: FavPokemonModel)

}