package com.kemsky.pokeapp.data.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.kemsky.GetAllPokemonQuery
import com.kemsky.GetDetailPokemonQuery
import com.kemsky.GetPagedPokemonQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeRepositoryImpl(private val graphQLService: () -> ApolloClient) : PokeRepository {

    override suspend fun getAllPokemon(): Flow<GetAllPokemonQuery.Data> {
        return flow {
            val response = graphQLService().query(GetAllPokemonQuery()).execute()
            response.data?.let { emit(it) }
        }
    }

    override suspend fun getPagedAllPokemin(
        offset: Int,
        limit: Int
    ): Flow<GetPagedPokemonQuery.Data> {
        return flow {
            val response = graphQLService().query(
                GetPagedPokemonQuery(
                    limit = Optional.presentIfNotNull(limit),
                    offset = Optional.presentIfNotNull(offset)
                )
            ).execute()
            response.data?.let { emit(it) }
        }
    }

    override suspend fun getDetailPokemon(pokeId: Int): Flow<GetDetailPokemonQuery.Data> {
        return flow {
            val response = graphQLService().query(
                GetDetailPokemonQuery(
                    pokeId = Optional.presentIfNotNull(pokeId)
                )
            ).execute()
            response.data?.let { emit(it) }
        }
    }


}