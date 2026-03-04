package com.om.ecommercedemo.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UiState
interface UiIntent
interface UiEvent

abstract class BaseViewModel<S : UiState, I : UiIntent, E : UiEvent>(initialState: S) : ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    // Channels are better for one-time events (navigation/toasts) than SharedFlow
    private val _event = Channel<E>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun onIntent(intent: I) {
        handleIntent(intent)
    }

    protected abstract fun handleIntent(intent: I)

    protected fun setState(reducer: S.() -> S) {
        _state.update { it.reducer() }
    }

    protected fun sendEvent(event: E) {
        viewModelScope.launch { _event.send(event) }
    }
}