package com.example.varastohallinta_frontend.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.varastohallinta_frontend.model.userSettings

class SettingsViewModel : ViewModel() {
    private val _userSettingsState = mutableStateOf(userSettings())
    val userSettingsState : State<userSettings> = _userSettingsState

    fun setDarkMode(darkMode : Boolean){
        _userSettingsState.value = _userSettingsState.value.copy(darkMode = darkMode)
    }

    fun setTestMode(testMode : Boolean){
        _userSettingsState.value = _userSettingsState.value.copy(testMode = testMode)
    }
}