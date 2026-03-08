package com.om.ecommercedemo.ui.features.detail

import com.om.ecommercedemo.base.UiIntent
import com.om.ecommercedemo.base.UiState
import com.om.ecommercedemo.domain.model.CategoryDto
import com.om.ecommercedemo.domain.model.ProductDto
import com.om.ecommercedemo.events.CoreEvent

data class DetailUiState(
    val isLoading: Boolean = false,
    val product: ProductDto? = null
) : UiState

sealed class DetailIntent : UiIntent {
    object OnBackClick : DetailIntent()
}

sealed interface DetailEvent : CoreEvent {
    object NavigateBackClicked : DetailEvent
}