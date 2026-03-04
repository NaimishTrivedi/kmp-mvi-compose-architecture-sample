package com.om.ecommercedemo.repositories

import com.om.ecommercedemo.domain.data.remote.APIResult
import com.om.ecommercedemo.domain.model.CategoryDto
import com.om.ecommercedemo.domain.model.ProductListResponse

interface HomeRepository {
    suspend fun getCategories(): APIResult<List<CategoryDto>>

    suspend fun getProductList(url: String): APIResult<ProductListResponse>
}