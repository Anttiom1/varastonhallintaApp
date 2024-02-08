package com.example.varastohallinta_frontend.model
//login request sent to backend rest API
//model of mvvm
data class LoginState(val username: String = "",
                      val password: String = "",
                      val loading: Boolean = false
)

data class LoginRes(val id: Int = 1,
                    val role: String = "normaluser")
