package com.om.ecommercedemo.repositories

import com.om.ecommercedemo.domain.data.remote.APIResult
import com.om.ecommercedemo.domain.data.remote.BaseRepository
import com.om.ecommercedemo.domain.model.CategoryDto
import com.om.ecommercedemo.domain.model.ProductListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class HomeRepositoryImpl(
    private val httpClient: HttpClient
) : BaseRepository(), HomeRepository {
    override suspend fun getCategories(): APIResult<List<CategoryDto>> {
        return safeApiCall {
            httpClient.get("products/categories")
        }
    }

    override suspend fun getProductList(url: String): APIResult<ProductListResponse> {
        return safeApiCall {
            httpClient.get(url)
        }
    }


}