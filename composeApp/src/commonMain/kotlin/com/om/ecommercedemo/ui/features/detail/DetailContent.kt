package com.om.ecommercedemo.ui.features.detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.om.ecommercedemo.domain.model.ProductDto
import com.om.ecommercedemo.domain.model.ReviewDto
import kotlin.math.ceil
import kotlin.math.roundToInt

@Preview(showBackground = true)
@Composable
fun DetailContent(
    state: DetailUiState = DetailUiState(),
    onIntent: (DetailIntent) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null
) {

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val isWideScreen = maxWidth > 700.dp
        state.product?.let { product ->
            if (isWideScreen) {
                DetailWideLayout(product, sharedTransitionScope, animatedVisibilityScope){
                    onIntent(DetailIntent.OnBackClick)
                }
            }else{
                DetailMobileLayout(product, sharedTransitionScope, animatedVisibilityScope){
                    onIntent(DetailIntent.OnBackClick)
                }
            }
        }
    }
}

@Composable
fun ImageGallery(
    product: ProductDto,
    sharedTransitionScope: SharedTransitionScope?,
    animatedVisibilityScope: AnimatedVisibilityScope?,
    onBackClick: () -> Unit
) {

    with(sharedTransitionScope!!) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {

            val image = product.images.firstOrNull()

            if (image != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(image) // large image
                        .crossfade(true)
                        .placeholderMemoryCacheKey(product.thumbnail) // reuse cached thumbnail
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().sharedElement(
                        sharedContentState = rememberSharedContentState(
                            key = "product-image-${product.id}"
                        ),
                        animatedVisibilityScope = animatedVisibilityScope!!
                    ),
                    contentScale = ContentScale.Crop
                )
            }

            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(16.dp)
                    .size(40.dp)
                    .background(Color.Gray.copy(alpha = 0.3f), CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
        }
    }
}

@Composable
fun ProductTitleSection(product: ProductDto) {

    Column {

        Text(
            product.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = "${product.brand ?: ""} • ${product.category}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(Modifier.height(8.dp))

        RatingRow(product.rating)
    }
}

@Composable
fun RatingRow(rating: Double) {

    Row(verticalAlignment = Alignment.CenterVertically) {

        repeat(5) { index ->

            val color =
                if (index < rating.roundToInt()) Color(0xFFFFC107)
                else Color.LightGray

            Icon(
                Icons.Default.Star,
                null,
                tint = color
            )
        }

        Spacer(Modifier.width(8.dp))

        Text(
            rating.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun PriceCard(product: ProductDto) {

    val discounted =
        product.price - (product.price * product.discountPercentage / 100)
    val roundedUp = ceil(discounted * 100.0) / 100.0
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF6F6F6)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    "$$roundedUp",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    "$${product.price}",
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = TextDecoration.LineThrough
                )

                Spacer(Modifier.width(10.dp))

                DiscountBadge(product.discountPercentage)
            }

            Spacer(Modifier.height(6.dp))

            Text(
                product.availabilityStatus,
                color = Color(0xFF2E7D32)
            )
        }
    }
}

@Composable
fun DiscountBadge(percent: Double) {

    Box(
        modifier = Modifier
            .background(
                Color.Red,
                RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            "${percent.toInt()}% OFF",
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ProductMetaSection(product: ProductDto) {

    Column {

        InfoItem("Stock", product.stock.toString())

        product.shippingInformation?.let {
            InfoItem("Shipping", it)
        }

        product.warrantyInformation?.let {
            InfoItem("Warranty", it)
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {

    Row(
        modifier = Modifier.padding(vertical = 6.dp)
    ) {

        Text(
            "$label:",
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(120.dp)
        )

        Text(value)
    }
}

@Composable
fun DescriptionSection(desc: String) {

    Column {

        Text(
            "Description",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            desc,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ReviewSection(reviews: List<ReviewDto>) {

    Column {

        Text(
            "Customer Reviews",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        reviews.forEach {
            ReviewItem(it)
        }
    }
}

@Composable
fun ReviewItem(review: ReviewDto) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF6F6F6)
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Row {

                Text(
                    review.reviewerName,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.width(8.dp))

                Text("⭐ ${review.rating}")
            }

            Spacer(Modifier.height(4.dp))

            Text(review.comment)
        }
    }
}

@Composable
fun AddToCartSection(
    product: ProductDto,
    onAddToCart: (ProductDto) -> Unit
) {

    Button(
        onClick = { onAddToCart(product) },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {

        Text(
            "Add To Cart",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

