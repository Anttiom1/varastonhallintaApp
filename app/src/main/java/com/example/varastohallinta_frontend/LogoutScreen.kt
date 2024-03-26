package com.example.varastohallinta_frontend

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.varastohallinta_frontend.viewmodel.LogoutViewModel

@Composable
fun LogoutScreen(goToLoginScreen: ()-> Unit){
    val logoutViewModel: LogoutViewModel = viewModel()
    val context = LocalContext.current



    LaunchedEffect(key1 = logoutViewModel.logoutState.value.error){
        logoutViewModel.logoutState.value.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = logoutViewModel.logoutState.value.logoutOk){
        if(logoutViewModel.logoutState.value.logoutOk){
            logoutViewModel.setLogout(false)
            goToLoginScreen()
            Log.d("antti", "ok")
        }
        else
            logoutViewModel.logout()
    }
}