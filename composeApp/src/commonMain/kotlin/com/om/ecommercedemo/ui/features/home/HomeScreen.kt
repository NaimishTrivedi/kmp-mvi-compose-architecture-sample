package com.om.ecommercedemo.ui.features.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.om.ecommercedemo.base.BaseScreen
import com.om.ecommercedemo.events.NoEvent
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen() {

    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState()

    BaseScreen(
        isLoading = state.isLoading,
        eventFlow = viewModel.event,
        onEvent = {}
    ) { paddingValues ->
        HomeContent(state, onIntent = { intent -> viewModel.onIntent(intent)})
    }
}