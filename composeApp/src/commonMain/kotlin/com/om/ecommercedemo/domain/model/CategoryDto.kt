package com.om.ecommercedemo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val slug: String,
    val name: String,
    val url: String
)
