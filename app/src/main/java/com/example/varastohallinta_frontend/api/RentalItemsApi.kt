package com.example.varastohallinta_frontend.api

import com.example.varastohallinta_frontend.model.RentalItemsByCategoryRes
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path


private val retrofit = createClient()

val rentalItemsServices = retrofit.create(RentalItemsApi::class.java)
interface RentalItemsApi {
    @GET("category/{categoryId}/items")
    suspend fun getItemsByCategory(@Path("categoryId") categoryId: Int): RentalItemsByCategoryRes

    @GET("category/{categoryId}")
    suspend fun  getCategoryName(@Path("categoryId") categoryId: Int)
}