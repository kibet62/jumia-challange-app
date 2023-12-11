package com.example.jumiachallange.data.remote

import android.app.appsearch.SearchResult
import com.example.jumiachallange.model.configarations.Configurations
import com.example.jumiachallange.model.product.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("configurations/")
    suspend fun getConfigurations(): Response<Configurations>

    @GET("search/{search}/page/{page}/")
    suspend fun searchProduct(
        @Path("page") page: Int,
        @Path("search") query: String
    ): SearchResult

    @GET("product/{sku}/")
    suspend fun getProduct(
        @Path("sku") sku: Int,
    ): Response<ProductResponse>

}