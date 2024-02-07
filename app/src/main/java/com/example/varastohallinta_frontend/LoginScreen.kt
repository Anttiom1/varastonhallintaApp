package com.example.varastohallinta_frontend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen() {
    val loginViewModel: LoginViewModel = viewModel()
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            loginViewModel.loginState.value.loading -> CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )

            else -> Column(
                //Column täyttää koko ruudun
                modifier = Modifier.fillMaxSize(),
                //Keskittää pystysuuntaan
                verticalArrangement = Arrangement.Center,
                //Keskittää vaakasuuntaan
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                OutlinedTextField(value = loginViewModel.loginState.value.username,
                    onValueChange = { newUsername ->
                        loginViewModel.setUsername(newUsername)
                    },
                    placeholder = { Text("Username") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = loginViewModel.loginState.value.password,
                    onValueChange = { newPassword ->
                        loginViewModel.setPassword(newPassword)
                    },
                    placeholder = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)

                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { loginViewModel.login() }) {
                    Text("Login")
                }
            }
        }
    }
}