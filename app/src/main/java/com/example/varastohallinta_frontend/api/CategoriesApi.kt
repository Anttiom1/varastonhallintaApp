package com.example.varastohallinta_frontend.api

import com.example.varastohallinta_frontend.model.AddCategoryReq
import com.example.varastohallinta_frontend.model.CategoriesRes

import com.example.varastohallinta_frontend.model.CategoryItem
import com.example.varastohallinta_frontend.model.CategoryRes
import com.example.varastohallinta_frontend.model.RentalItemsByCategoryRes
import com.example.varastohallinta_frontend.model.UpdateCategoryReq
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private val retrofit = createClient()

val categoriesService = retrofit.create(CategoriesApi::class.java)
interface CategoriesApi {

    @GET("category/")
    // GET::http://localhost:8000/api/v1/category/
    suspend fun getCategories(): CategoriesRes

    // GET::https://localhost:8000/api/v1/catgory/{categoryId}
    @GET("category/{categoryId}")
    suspend fun getCategoryById(@Path("categoryId") categoryId: Int): CategoryRes

    @PUT("category/{categoryId}")
    suspend fun editCategory(
        @Path("categoryId") categoryId: Int,
        @Body reqBody: UpdateCategoryReq
    ): CategoryRes

    @DELETE("category/{categoryId}")
    suspend fun  removeCategory(@Path("categoryId") categoryId: Int)

    @POST("category/")
    suspend fun addCategory(
        @Body reqBody: AddCategoryReq
    )



}