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
    val done: Boolean = false
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
    val rentalItemName: String = ""
)

data class AddRentalItemReq(
    @SerializedName("rental_item_name")
    val rentalItemName: String = ""
)