package com.rediz.pokemon.di

import com.rediz.pokemon.data.repository.RepositoryImpl
import com.rediz.pokemon.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideAppRepository(repository: RepositoryImpl): Repository
}