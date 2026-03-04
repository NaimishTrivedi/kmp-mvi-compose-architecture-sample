package com.om.ecommercedemo.ui.features.home

import androidx.lifecycle.viewModelScope
import com.om.ecommercedemo.base.BaseViewModel
import com.om.ecommercedemo.domain.data.remote.APIResult
import com.om.ecommercedemo.events.CoreEvent
import com.om.ecommercedemo.events.NoEvent
import com.om.ecommercedemo.repositories.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
) : BaseViewModel<HomeUiState, HomeIntent, CoreEvent>(HomeUiState()) {

    override fun handleIntent(intent: HomeIntent) {
        when(intent){
            is HomeIntent.GetHomeCategoryData -> {
                getCategoryData()
            }
            is HomeIntent.CategoryItemClicked -> {
                getProductData(intent.url)
            }
        }
    }

    private fun getCategoryData(){
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            when(val response = homeRepository.getCategories()){
                is APIResult.Success -> {
                    setState { copy(isLoading = false, categories = response.data) }
                    response.data.firstOrNull()?.let {
                        getProductData(it.url)
                    }
                }

                is APIResult.Error -> {
                    setState { copy(isLoading = false) }
                    sendEvent(CoreEvent.ShowToast(response.message ?: "Unknown Error"))
                }

                is APIResult.Exception -> {
                    setState { copy(isLoading = false) }
                    sendEvent(CoreEvent.ShowToast("Network connection failed"))
                }
            }
        }
    }

    private fun getProductData(url: String){
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            when(val response = homeRepository.getProductList(url)){
                is APIResult.Success -> {
                    setState { copy(isLoading = false, products = response.data.products) }
                }

                is APIResult.Error -> {
                    setState { copy(isLoading = false) }
                    sendEvent(CoreEvent.ShowToast(response.message ?: "Unknown Error"))
                }

                is APIResult.Exception -> {
                    setState { copy(isLoading = false) }
                    sendEvent(CoreEvent.ShowToast("Network connection failed"))
                }
            }
        }
    }

}