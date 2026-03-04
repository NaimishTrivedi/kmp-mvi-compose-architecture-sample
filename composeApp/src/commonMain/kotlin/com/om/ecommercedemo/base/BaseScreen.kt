package com.om.ecommercedemo.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.om.ecommercedemo.events.CoreEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@Composable
fun <E : UiEvent> BaseScreen(
    isLoading: Boolean = false,
    topBar: @Composable (() -> Unit)? = null,
    eventFlow: Flow<E>? = null,
    onEvent: (E) -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var toastMessage by remember { mutableStateOf<String?>(null) }

// Unified Event Listener
    LaunchedEffect(eventFlow) {
        eventFlow?.collect { event ->
// In KMP, we handle common UI events here
// Toasts must be implemented as custom Compose overlays since
// iOS/Web don't have a native Toast.makeText
            when (event) {
                is CoreEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                }
                is CoreEvent.ShowToast -> {
                    toastMessage = event.message
                }
                else -> onEvent(event)
            }
        }
    }

// Auto-hide Toast logic
    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            delay(2000) // Show for 2 seconds
            toastMessage = null
        }
    }

    Scaffold(
        topBar = { topBar?.invoke() },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            content(paddingValues)

            toastMessage?.let { message ->
                KmpToastUI(message)
            }

            if (isLoading) {
                LoadingOverlay()
            }
        }
    }
}

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun BoxScope.KmpToastUI(message: String) {
    Surface(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 64.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color.Black.copy(alpha = 0.8f),
        tonalElevation = 4.dp
    ) {
        Text(
            text = message,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}