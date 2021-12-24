package com.kemsky.pokeapp.di

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.kemsky.pokeapp.ViewModelFactory
import com.kemsky.pokeapp.constant.AppConstant.BASE_URL
import com.kemsky.pokeapp.data.repository.PokeRepository
import com.kemsky.pokeapp.data.repository.PokeRepositoryImpl
import com.kemsky.pokeapp.data.room.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(): PokeRepository = PokeRepositoryImpl {
        ApolloClient.Builder()
            .serverUrl(BASE_URL)
            .build()
    }

    @Provides
    fun provideViewModelProvider(
        repository: PokeRepository,
        @ApplicationContext context: Context
    ): ViewModelFactory = ViewModelFactory(
        repository
    ) {
        Room.databaseBuilder(
            context, PokemonDatabase::class.java, "fav-pokemon"
        ).build()
    }


}