package com.example.varastohallinta_frontend.api

import com.example.varastohallinta_frontend.model.AuthReq
import com.example.varastohallinta_frontend.model.AuthRes
import retrofit2.http.Body
import retrofit2.http.POST

private val retrofit = createClient()

val authService = retrofit.create(AuthApi::class.java)

interface AuthApi{

    @POST("auth/login")
    suspend fun login(@Body req: AuthReq): AuthRes
}