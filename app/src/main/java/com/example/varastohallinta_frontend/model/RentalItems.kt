package com.example.varastohallinta_frontend.model

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