package com.example.varastohallinta_frontend

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.varastohallinta_frontend.viewmodel.CreateAccountViewModel

@Composable
fun CreateAccountScreen(goBack:()->Unit){
    val createAccountViewModel : CreateAccountViewModel = viewModel()
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = createAccountViewModel.createAccountState.value.error){
        createAccountViewModel.createAccountState.value.error?.let {
            Toast.makeText(context, createAccountViewModel.createAccountState.value.error, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = createAccountViewModel.createAccountState.value.done){
        if(createAccountViewModel.createAccountState.value.done) {
            createAccountViewModel.setDone(false)
            goBack()
            Log.d("antti", "amsda")
        }
    }

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(value = createAccountViewModel.createAccountState.value.username,
                    onValueChange = { createAccountViewModel.setName(it) },
                    placeholder = { Text(stringResource(R.string.username)) },
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = createAccountViewModel.createAccountState.value.password,
                    onValueChange = { createAccountViewModel.setPassword(it) },
                    placeholder = { Text(stringResource(R.string.password)) },
                    singleLine = true,
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
                    })
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = {
                        createAccountViewModel.createAccount()
                    }) {
                        Text(text = "Create account")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { goBack() }) {
                        Text(stringResource(id = R.string.back))
                    }
                }
            }
        }
    }
}