package com.example.varastohallinta_frontend.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.varastohallinta_frontend.model.LoginRes
import com.example.varastohallinta_frontend.model.LoginState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState>  = _loginState

    fun setUsername(newUsername : String){
        _loginState.value = _loginState.value.copy(username = newUsername)
    }

    fun setPassword(newPassword : String){
        _loginState.value = _loginState.value.copy(password = newPassword)
    }

    //Use only to simulate login delay
    private suspend fun _waitForLogin(){
        delay(2000)
    }

    fun login(){

        viewModelScope.launch {
            _loginState.value = _loginState.value.copy(loading = true)
            _waitForLogin()
            val res = LoginRes()
            _loginState.value = _loginState.value.copy(loading = false)
        }
    }
}