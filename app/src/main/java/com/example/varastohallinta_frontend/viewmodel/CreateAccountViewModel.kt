package com.example.varastohallinta_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.varastohallinta_frontend.api.authService
import com.example.varastohallinta_frontend.model.CreateAccountReq
import com.example.varastohallinta_frontend.model.CreateAccountState
import kotlinx.coroutines.launch

class CreateAccountViewModel: ViewModel() {
    private val _createAccountState = mutableStateOf(CreateAccountState())
    val createAccountState: State<CreateAccountState> = _createAccountState

    fun setName(name: String){
        _createAccountState.value = _createAccountState.value.copy(username = name)
    }

    fun setPassword(password: String){
        _createAccountState.value = _createAccountState.value.copy(password = password)
    }

    fun setDone(done: Boolean){
        _createAccountState.value = _createAccountState.value.copy(done = done)
    }

    fun createAccount(){
        viewModelScope.launch {
            try {
                _createAccountState.value = _createAccountState.value.copy(loading = true)
                authService.createAccount(
                    CreateAccountReq(username = _createAccountState.value.username, password = _createAccountState.value.password)
                )
                setDone(true)
            }
            catch (e: Exception){
                _createAccountState.value = _createAccountState.value.copy(error = e.toString())
                Log.d("antti", e.toString())
            }
            finally {
                _createAccountState.value = _createAccountState.value.copy(loading = false)
            }
        }
    }
}