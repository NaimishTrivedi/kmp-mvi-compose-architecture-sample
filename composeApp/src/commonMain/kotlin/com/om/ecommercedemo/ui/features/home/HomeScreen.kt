package com.om.ecommercedemo.ui.features.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.om.ecommercedemo.AppRoute
import com.om.ecommercedemo.base.BaseScreen
import io.ktor.http.encodeURLParameter
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {

    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState()

    BaseScreen(
        isLoading = state.isLoading,
        eventFlow = viewModel.event,
        onEvent = { event ->
            when (event) {
                is HomeEvent.ShowProductDetailScreenEvent -> {
                    // Navigate to product detail screen
                    val json = Json.encodeToString(event.productDto)
                    val encoded = json.encodeURLParameter()
                    navController.navigate("${AppRoute.ProductDetail.route}/$encoded")
                }
            }
        }
    ) { paddingValues ->
        HomeContent(
            state, onIntent = { intent -> viewModel.onIntent(intent) },
            sharedTransitionScope, animatedVisibilityScope
        )
    }
}