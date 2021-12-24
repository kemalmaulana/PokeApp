package com.kemsky.pokeapp.data.repository

import com.kemsky.GetAllPokemonQuery
import com.kemsky.GetDetailPokemonQuery
import com.kemsky.GetPagedPokemonQuery
import kotlinx.coroutines.flow.Flow

interface PokeRepository {

    suspend fun getAllPokemon(): Flow<GetAllPokemonQuery.Data>
    suspend fun getPagedAllPokemin(offset: Int, limit: Int): Flow<GetPagedPokemonQuery.Data>
    suspend fun getDetailPokemon(pokeId: Int): Flow<GetDetailPokemonQuery.Data>

}