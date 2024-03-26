package com.example.varastohallinta_frontend.api

import com.example.varastohallinta_frontend.model.AuthReq
import com.example.varastohallinta_frontend.model.AuthRes
import com.example.varastohallinta_frontend.model.CreateAccountReq
import com.example.varastohallinta_frontend.model.LoggedUserRes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

private val retrofit = createClient()

val authService = retrofit.create(AuthApi::class.java)

interface AuthApi{

    @POST("auth/login")
    suspend fun login(@Body req: AuthReq): AuthRes

    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") bearerToken: String)

    @GET("auth/account")
    suspend fun getAccount(@Header("Authorization") bearerToken: String): LoggedUserRes

    @POST("auth/register")
    suspend fun createAccount(@Body req: CreateAccountReq)
}