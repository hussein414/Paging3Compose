package com.example.pagingcompose.data.client

import com.example.pagingcompose.data.model.api.BeerDto
import com.example.pagingcompose.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerApi {
    @GET(Constants.END_POINT_URL)
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<BeerDto>
}