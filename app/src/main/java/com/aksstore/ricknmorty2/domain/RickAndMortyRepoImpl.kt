package com.aksstore.ricknmorty2.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aksstore.ricknmorty2.data.RNMResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RickAndMortyRepoImpl @Inject constructor(private val rickAndMortyService: RickAndMortyService) : RickAndMortyRepository {
    override suspend fun getRickAndMortyCharacters(): Flow<PagingData<RNMResult>>  =
        Pager(config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = { RickAndMortyDataSource(rickAndMortyService) }).flow

    override suspend fun getRickAndMortyCharacterById(characterId: Int): Response<RNMResult> {
        return rickAndMortyService.getCharacterById(characterId)
    }

}