package com.om.ecommercedemo.ui.features.detail

import com.om.ecommercedemo.base.BaseViewModel
import com.om.ecommercedemo.domain.model.ProductDto
import com.om.ecommercedemo.events.CoreEvent

class DetailViewModel : BaseViewModel<DetailUiState, DetailIntent, CoreEvent>(DetailUiState()) {

    override fun handleIntent(intent: DetailIntent) {
        when(intent){
            is DetailIntent.OnBackClick -> {
                sendEvent(DetailEvent.NavigateBackClicked)
            }
        }
    }

    fun setProduct(product: ProductDto) {
        setState { copy(product = product) }
    }
}