package com.example.varastohallinta_frontend.model

import com.google.gson.annotations.SerializedName

//login request sent to backend rest API
//model of mvvm
data class LoginState(
    @SerializedName("auth_user_id")
    val authUserId: Int = 0,
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val loginOk: Boolean = false
)

data class LoggedUserRes(
    val username: String = "",
    @SerializedName("auth_user_id")
    val authUserId: Int = 0)

data class AuthReq(val username: String = "", val password: String = "")

data class AuthRes(
    @SerializedName("access_token")
    val accessToken: String = ""
)