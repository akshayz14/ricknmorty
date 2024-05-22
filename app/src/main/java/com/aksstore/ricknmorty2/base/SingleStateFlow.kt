package com.aksstore.ricknmorty2.base

import androidx.annotation.MainThread
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SingleStateFlow<T> {

    private val _stateFlow = MutableStateFlow<T?>(null)

    val stateFlow: StateFlow<T?>
        get() = _stateFlow

    @MainThread
    fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        owner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                // We use distinctUntilChanged to ensure the observer is only notified of changes
                // when the value actually changes
                owner.lifecycleScope.launch {
                    _stateFlow.collect { value ->
                        if (value != null) {
                            observer.onChanged(value)
                            // Reset to null after dispatching the value
                            _stateFlow.value = null
                        }
                    }
                }
            }
        })
    }

    @MainThread
    fun setValue(value: T) {
        _stateFlow.value = value
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        _stateFlow.value = null
    }


}