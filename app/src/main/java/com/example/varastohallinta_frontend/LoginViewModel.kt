package com.example.varastohallinta_frontend

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _loginState = mutableStateOf<LoginReq>(LoginReq())
    val loginState: State<LoginReq>  = _loginState

    fun setLoginState(newLoginState : LoginReq){
        _loginState.value = newLoginState
    }
}

