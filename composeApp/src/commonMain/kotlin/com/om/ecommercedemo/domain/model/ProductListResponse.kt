package com.om.ecommercedemo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse(
    val products: List<ProductDto>
)
