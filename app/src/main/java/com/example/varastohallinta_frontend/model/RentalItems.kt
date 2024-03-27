package com.example.varastohallinta_frontend.model

import android.content.ClipData.Item
import com.google.gson.annotations.SerializedName

data class RentalItemsState(
    val list: List<RentalItem> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null

)

data class RentalItemState(
    @SerializedName("rental_item_name")
    val rentalItemName: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val done: Boolean = false,

)

data class RentalItemDeleteState(
    val id: Int = 0,
    val error: String? = null
)
data class RentalItemRentState(
    val id: Int = 0,
    val error: String? = null
)


data class RentalItemsByCategoryRes(
    @SerializedName("items")
    val list: List<RentalItem>
)

data class RentalItem(
    @SerializedName("rental_item_id")
    val rentalItemId: Int = 0,
    @SerializedName("rental_item_name")
    val rentalItemName: String = "",
    @SerializedName("category_category_id")
    val categoryId: Int = 0,
    @SerializedName("created_by_user_id")
    val createdByUserId: Int = 0
)

data class AddRentalItemReq(
    @SerializedName("rental_item_name")
    val rentalItemName: String = "",
    @SerializedName("category_category_id")
    val categoryId: Int = 0,
    @SerializedName("created_by_user_id")
    val createdByUserId: Int = 1
)

data class UpdateItemReq(
    @SerializedName("rental_item_name")
    val rentalItemName: String = ""
)

data class UpdateItemRes(
    @SerializedName("category_category")
    val category: ItemCategory
)

data class ItemCategory(
    @SerializedName("category_id")
    val categoryId: Int = 0
)

data class RentItemReq(
    @SerializedName("auth_user_auth_user_id")
    val userId: Int = 1
)