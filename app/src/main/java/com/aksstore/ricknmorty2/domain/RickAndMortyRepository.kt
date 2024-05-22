package com.aksstore.ricknmorty2.domain

import androidx.paging.PagingData
import com.aksstore.ricknmorty2.data.RNMResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RickAndMortyRepository {

    suspend fun getRickAndMortyCharacters(): Flow<PagingData<RNMResult>>

    suspend fun getRickAndMortyCharacterById(characterId: Int): Response<RNMResult>

}