package com.kemsky.pokeapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kemsky.pokeapp.data.room.model.FavPokemonModel

@Database(entities = [FavPokemonModel::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun favPokemonDao(): PokemonDao
}