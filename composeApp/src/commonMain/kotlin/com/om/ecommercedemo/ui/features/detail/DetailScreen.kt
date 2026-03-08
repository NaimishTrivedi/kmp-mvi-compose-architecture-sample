package com.om.ecommercedemo.ui.features.detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.om.ecommercedemo.base.BaseScreen
import com.om.ecommercedemo.domain.model.ProductDto
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DetailScreen(
    navController: NavHostController,
    productDto: ProductDto,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val viewModel = koinViewModel<DetailViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(productDto) {
        viewModel.setProduct(productDto)
    }

    BaseScreen(
        isLoading = state.isLoading,
        eventFlow = viewModel.event,
        onEvent = {event ->
            when(event){
                is DetailEvent.NavigateBackClicked -> {
                    // Navigate back
                    navController.popBackStack()
                }
            }
        }
    ) { paddingValues ->
        DetailContent(state, onIntent = { intent -> viewModel.onIntent(intent)},sharedTransitionScope,animatedVisibilityScope)
    }
}