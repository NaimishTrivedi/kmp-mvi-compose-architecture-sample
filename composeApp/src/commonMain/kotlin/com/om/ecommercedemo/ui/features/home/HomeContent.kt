package com.om.ecommercedemo.ui.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.om.ecommercedemo.domain.model.CategoryDto
import com.om.ecommercedemo.domain.model.ProductDto

@Preview(showBackground = true)
@Composable
fun HomeContent(
    state: HomeUiState = HomeUiState(),
    onIntent: (HomeIntent) -> Unit = {}
){
    LaunchedEffect(Unit){
        onIntent(HomeIntent.GetHomeCategoryData)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
// 1️⃣ Center Logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "LOGO",
                style = MaterialTheme.typography.headlineMedium
            )
        }

// 2️⃣ Categories title
        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )

// 3️⃣ Horizontal category list
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(state.categories) { category ->
                CategoryItem(category){
                    onIntent(HomeIntent.CategoryItemClicked(it))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        BoxWithConstraints {
            val columns = when {
                maxWidth < 600.dp -> 2   // Mobile
                maxWidth < 900.dp -> 3   // Tablet
                else -> 5                // Web
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.products) { product ->
                    ProductItem(product)
                }
            }
        }
    }
}

@Composable
fun CategoryItem(categoryDto: CategoryDto, onClick:(String) -> Unit) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = categoryDto.name,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).clickable{
                onClick(categoryDto.url)
            },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ProductItem(productDto: ProductDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(productDto.thumbnail) // Your URL from API
                    .crossfade(true)
                    .build(),
                contentDescription = productDto.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}