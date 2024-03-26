package com.example.varastohallinta_frontend.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.varastohallinta_frontend.AccountDatabase
import com.example.varastohallinta_frontend.DbProvider
import com.example.varastohallinta_frontend.api.authService
import kotlinx.coroutines.launch

data class LogoutState(val loading: Boolean = false,
                       val error: String? = null,
                       val logoutOk: Boolean = false,
                       )

class LogoutViewModel(private val db: AccountDatabase = DbProvider.db) : ViewModel(){

    private val _logoutState = mutableStateOf(LogoutState())
    val logoutState: State<LogoutState> = _logoutState

    fun setLogout(ok: Boolean){
        _logoutState.value = _logoutState.value.copy(logoutOk = ok)
    }

    fun logout(){
        viewModelScope.launch {
            try {
                _logoutState.value = _logoutState.value.copy(loading = true)
                val accessToken = db.accountDao().getToken()
                accessToken?.let {
                    authService.logout("Bearer $it")
                    db.accountDao().removeTokens()
                }
                setLogout(true)
            }
            catch (e: Exception){
                _logoutState.value = _logoutState.value.copy(error = e.toString())
            }
            finally {
                _logoutState.value = _logoutState.value.copy(loading = false)
            }
        }
    }
}