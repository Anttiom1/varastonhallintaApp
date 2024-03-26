package com.example.varastohallinta_frontend.model

import com.google.gson.annotations.SerializedName

data class CreateAccountState(
    val loading: Boolean = false,
    val username: String = "",
    val password: String = "",
    val error: String? = null,
    val done: Boolean = false
    )
data class CreateAccountReq(val username: String = "", val password: String = "")
