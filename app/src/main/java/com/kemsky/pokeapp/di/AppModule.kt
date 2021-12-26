package com.kemsky.pokeapp.di

import android.content.Context
import com.kemsky.pokeapp.ViewModelFactory
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
    fun provideRepository(): PokeRepository = PokeRepositoryImpl(PokeRepositoryImpl.invoke())

    @Provides
    fun provideViewModelProvider(
        repository: PokeRepository,
        @ApplicationContext context: Context
    ): ViewModelFactory = ViewModelFactory(
        repository, PokemonDatabase.getDatabase(context)
    )


}