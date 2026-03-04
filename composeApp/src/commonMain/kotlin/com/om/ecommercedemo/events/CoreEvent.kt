package com.om.ecommercedemo.events

import com.om.ecommercedemo.base.UiEvent

sealed interface CoreEvent : UiEvent {
    data class ShowSnackbar(val message: String, val actionLabel: String? = null) : CoreEvent
    data class ShowToast(val message: String) : CoreEvent
}