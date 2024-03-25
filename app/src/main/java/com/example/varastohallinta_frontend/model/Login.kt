package com.example.varastohallinta_frontend.model

import com.google.gson.annotations.SerializedName

//login request sent to backend rest API
//model of mvvm
data class LoginState(
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null
)

data class AuthReq(val username: String = "", val password: String = "")

data class AuthRes(
    @SerializedName("access_token")
    val accessToken: String = ""
)