package com.aksstore.ricknmorty2.domain

import com.aksstore.ricknmorty2.data.Characters
import com.aksstore.ricknmorty2.data.RNMResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character/")
    suspend fun getRickNMortyCharacters(@Query("page") pageNumber: Int): Response<Characters>

    @GET("character/{characterId}")
    suspend fun getCharacterById(@Path("characterId") characterId: Int): Response<RNMResult>

}