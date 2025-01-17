package com.example.varastohallinta_frontend.model

import com.google.gson.annotations.SerializedName

data class CategoriesState(
    val list: List<CategoryItem> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)

data class CategoryState(
    val categoryName: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val done: Boolean = false
)

data class CategoryDeleteState(
    val id: Int = 0,
    val error: String? = null
)

data class CategoryItem(
    @SerializedName("category_id")
    val categoryId: Int = 0,
    @SerializedName("category_name")
    val categoryName: String = ""
)

data class CategoriesRes(val categories: List<CategoryItem> = emptyList())

data class CategoryRes(val category: CategoryItem)

data class UpdateCategoryReq(
    @SerializedName("category_name")
    val categoryName: String)

data class AddCategoryReq(
    @SerializedName("category_name")
    val categoryName: String
)
