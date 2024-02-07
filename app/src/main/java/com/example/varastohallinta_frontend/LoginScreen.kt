package com.example.varastohallinta_frontend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.yield

@Composable
fun LoginScreen() {
    val loginViewModel: LoginViewModel = viewModel()
    var isPasswordVisible by remember { mutableStateOf(false) }
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
                    placeholder = { Text(stringResource(R.string.password)) },
                    //Changes the visibility of the password
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None
                            else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        //Button to change password visibility
                        IconButton(
                            onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }
                        ) {
                            //Change icon and show password in clear text or as hidden
                            Icon(imageVector = if (isPasswordVisible) ImageVector.vectorResource(R.drawable.baseline_visibility_24)
                                else ImageVector.vectorResource(R.drawable.baseline_visibility_off_24)
                                , contentDescription = if (isPasswordVisible) stringResource(R.string.hide_password)
                                else stringResource( R.string.show_password
                                ))
                        }
                    }

                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { loginViewModel.login() }) {
                    Text("Login")
                }
            }
        }
    }
}