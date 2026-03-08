package com.om.ecommercedemo

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.read
import com.om.ecommercedemo.domain.model.ProductDto
import com.om.ecommercedemo.ui.features.detail.DetailScreen
import com.om.ecommercedemo.ui.features.home.HomeScreen
import io.ktor.http.decodeURLPart
import kotlinx.serialization.json.Json

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = AppRoute.Home.route
        ) {

            composable(AppRoute.Home.route) { backStackEntry ->
                HomeScreen(navController,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this
                )
            }

            composable("${AppRoute.ProductDetail.route}/{product}") {backStackEntry ->
                val json = backStackEntry.arguments?.read {
                    getString("product")
                }

                val product = json?.let {
                    Json.decodeFromString<ProductDto>(it.decodeURLPart())
                }

                product?.let {
                    DetailScreen(navController,it,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this)
                }
            }

        }
    }
}
