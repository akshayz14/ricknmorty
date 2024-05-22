package com.aksstore.ricknmorty2.di

import com.aksstore.ricknmorty2.domain.RickAndMortyRepoImpl
import com.aksstore.ricknmorty2.domain.RickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRickAndMortyRepository(rickAndMortyRepoImpl: RickAndMortyRepoImpl): RickAndMortyRepository

}