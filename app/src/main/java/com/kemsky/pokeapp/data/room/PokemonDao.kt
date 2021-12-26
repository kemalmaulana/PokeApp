package com.kemsky.pokeapp.data.room

import androidx.room.*
import com.kemsky.pokeapp.data.room.model.FavPokemonModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT * FROM favpokemonmodel")
    fun getAllFav(): Flow<List<FavPokemonModel>>

    @Query("SELECT * FROM favpokemonmodel WHERE id == :pokeId")
    fun getSingleFav(pokeId: Int): Flow<FavPokemonModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: FavPokemonModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingle(users: FavPokemonModel)

    @Delete
    suspend fun delete(user: FavPokemonModel)

}