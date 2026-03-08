package com.om.ecommercedemo.ui.features.detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.om.ecommercedemo.domain.model.ProductDto

@Composable
fun DetailWideLayout(
    product: ProductDto,
    sharedTransitionScope: SharedTransitionScope?,
    animatedVisibilityScope: AnimatedVisibilityScope?,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 24.dp)
        ) {

            ImageGallery(
                product = product,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                onBackClick = onBackClick
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            DetailInfoContent(product)
        }
    }
}

@Composable
fun DetailInfoContent(product: ProductDto) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        ProductTitleSection(product)

        Spacer(Modifier.height(12.dp))

        PriceCard(product)

        Spacer(Modifier.height(20.dp))

        ProductMetaSection(product)

        Spacer(Modifier.height(20.dp))

        DescriptionSection(product.description)

        Spacer(Modifier.height(24.dp))

        ReviewSection(product.reviews)

        Spacer(Modifier.height(30.dp))

        AddToCartSection(product) { }
    }
}