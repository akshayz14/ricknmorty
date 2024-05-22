package com.aksstore.ricknmorty2.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aksstore.ricknmorty2.base.BaseViewModel
import com.aksstore.ricknmorty2.data.RNMResult
import com.aksstore.ricknmorty2.domain.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RickNMortyViewModel @Inject constructor(private val rickAndMortyRepository: RickAndMortyRepository) :
    BaseViewModel() {

    init {
        getAllCharacters()
//        getCharacterById(2)
    }

    private lateinit var _rnmFlow: Flow<PagingData<RNMResult>>
    val rnmFlow: Flow<PagingData<RNMResult>>
        get() = _rnmFlow

    private val _rnmResultStateFlow = MutableStateFlow<RNMResult?>(null)
    val rnmResultStateFlow: StateFlow<RNMResult?> = _rnmResultStateFlow

    fun getAllCharacters() = launchPagingAsync({
        rickAndMortyRepository.getRickAndMortyCharacters().cachedIn(viewModelScope)
    }, {
        _rnmFlow = it
    })
//
//    fun getCharacterById(characterId: Int) = launchAsync({
//        rickAndMortyRepository.getRickAndMortyCharacterById(characterId)
//    }, {
//        _rnmResult = it
//    })

    fun getCharacterById(characterId: Int) = launchAsync({
        rickAndMortyRepository.getRickAndMortyCharacterById(characterId)
    }, {
        _rnmResultStateFlow.value = it
    })

}