package com.example.varastohallinta_frontend.api

import com.example.varastohallinta_frontend.model.CategoriesRes
import com.example.varastohallinta_frontend.model.CategoryItem
import retrofit2.http.GET

private val retrofit = createClient()

val categoriesService = retrofit.create(CategoriesApi::class.java)
interface CategoriesApi{
    @GET("category/")
    //GET::http://localhost:8000/api/v1/category
    suspend fun getCategories() : CategoriesRes
}