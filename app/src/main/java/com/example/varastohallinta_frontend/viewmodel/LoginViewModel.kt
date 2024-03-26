package com.example.varastohallinta_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.varastohallinta_frontend.AccountDatabase
import com.example.varastohallinta_frontend.AccountEntity
import com.example.varastohallinta_frontend.DbProvider
import com.example.varastohallinta_frontend.api.authService
import com.example.varastohallinta_frontend.model.AuthReq
import com.example.varastohallinta_frontend.model.AuthRes
import com.example.varastohallinta_frontend.model.LoginState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val db: AccountDatabase = DbProvider.db) : ViewModel() {
    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState>  = _loginState


    init {
        viewModelScope.launch {
            val accessToken = db.accountDao().getToken()
            accessToken?.let {
                val loggedUser = authService.getAccount("bearer $it")
                _loginState.value = _loginState.value.copy(username = loggedUser.username)
                _loginState.value = _loginState.value.copy(authUserId = loggedUser.authUserId)
                setLogin(true)
            }
        }
    }

    fun setUsername(newUsername : String){
        _loginState.value = _loginState.value.copy(username = newUsername)
    }

    fun setPassword(newPassword : String){
        _loginState.value = _loginState.value.copy(password = newPassword)
    }

    //Use only to simulate login delay
    fun setLogin(ok: Boolean){
        _loginState.value = _loginState.value.copy(loginOk = ok)
    }

    fun login(){
        viewModelScope.launch {
            try {
                _loginState.value = _loginState.value.copy(loading = true)
                val res = authService.login(
                    AuthReq(
                        username = _loginState.value.username,
                        password = _loginState.value.password
                    )
                )
                db.accountDao().addToken(
                    AccountEntity(accessToken = res.accessToken)
                )
                Log.d("jyy", res.accessToken)
                setLogin(true)
            }
            catch (e: Exception){
                _loginState.value = _loginState.value.copy(error = e.toString())
            }
            finally {
                _loginState.value = _loginState.value.copy(loading = false)
            }
        }
    }
}