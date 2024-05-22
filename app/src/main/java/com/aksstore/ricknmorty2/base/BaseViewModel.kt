package com.aksstore.ricknmorty2.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response


abstract class BaseViewModel : ViewModel(){

    var progressLiveEvent = SingleLiveEvent<Boolean>()
    var errorMessage = SingleLiveEvent<String>()
    var errorMessage2 = SingleStateFlow<String>()

    inline fun <T> launchAsync(
        crossinline execute: suspend () -> Response<T>,
        crossinline onSuccess: (T) -> Unit,
        showProgress: Boolean = true
    ) {
        viewModelScope.launch {
            if (showProgress)
                progressLiveEvent.value = true
            try {
                val result = execute()
                if (result.isSuccessful)
                    onSuccess(result.body()!!)
                else
                    errorMessage.value = result.message()
            } catch (ex: Exception) {
                errorMessage.value = ex.message
            } finally {
                progressLiveEvent.value = false
            }
        }
    }

    inline fun <T> launchPagingAsync(
        crossinline execute : suspend () -> Flow<T>,
        crossinline onSuccess : (Flow<T>) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)
            } catch (ex : Exception) {
//                errorMessage2.stateFlow.collect {
//                    if (it != null) {
//                        ex.message?.let { it1 -> errorMessage2.setValue(it1) }
//                    }
//                }
                errorMessage.value = ex.message
            }
        }
    }

}