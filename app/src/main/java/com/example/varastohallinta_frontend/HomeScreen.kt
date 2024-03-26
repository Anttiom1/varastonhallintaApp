package com.example.varastohallinta_frontend

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.varastohallinta_frontend.viewmodel.HomeViewModel

@Composable
fun HomeScreen(goToLoginScreen: ()-> Unit){
    val homeViewModel: HomeViewModel = viewModel()
    val context = LocalContext.current

    LaunchedEffect(key1 = homeViewModel.logoutState.value.error){
        homeViewModel.logoutState.value.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = homeViewModel.logoutState.value.logoutOk){
        if(homeViewModel.logoutState.value.logoutOk){
            homeViewModel.setLogout(false)
            goToLoginScreen()
        }
    }

    Button(onClick = { homeViewModel.logout() }) {
        Text(text = "Logout")
        
    }
}