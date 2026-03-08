package com.om.ecommercedemo.ui.features.home

import com.om.ecommercedemo.base.UiIntent
import com.om.ecommercedemo.base.UiState
import com.om.ecommercedemo.domain.model.CategoryDto
import com.om.ecommercedemo.domain.model.ProductDto
import com.om.ecommercedemo.events.CoreEvent

data class HomeUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoryDto> = emptyList(),
    val products: List<ProductDto> = emptyList()
) : UiState

sealed class HomeIntent : UiIntent {
    object GetHomeCategoryData : HomeIntent()
    data class CategoryItemClicked(val url: String) : HomeIntent()
    data class ProductItemClicked(val productDto: ProductDto) : HomeIntent()
}

sealed interface HomeEvent : CoreEvent {
    data class ShowProductDetailScreenEvent(val productDto: ProductDto) : HomeEvent
}