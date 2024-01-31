package com.example.varastohallinta_frontend

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.varastohallinta_frontend.ui.theme.Varastohallinta_frontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Varastohallinta_frontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val loginViewModel: LoginViewModel = viewModel()

                    Column(
                        //Column täyttää koko ruudun
                        modifier = Modifier.fillMaxSize(),
                        //Keskittää pystysuuntaan
                        verticalArrangement = Arrangement.Center,
                        //Keskittää vaakasuuntaan
                        horizontalAlignment = Alignment.CenterHorizontally

                    ){
                        OutlinedTextField(value = loginViewModel.loginState.value.username,
                            onValueChange = {newUsername ->
                                            loginViewModel.setLoginState(LoginReq(username = newUsername, loginViewModel.loginState.value.password))
                                            },
                            placeholder = {Text("Username")}
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(value = loginViewModel.loginState.value.password,
                            onValueChange = {newPassword->
                                            loginViewModel.setLoginState(LoginReq(loginViewModel.loginState.value.username, password = newPassword))
                                            },
                            placeholder = {Text("Password")}
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { }) {
                            Text("Login")
                            
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greetings(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize()
        ,verticalArrangement = Arrangement.Center
        ,horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            text = "Hello $name!"
        )
        Button(onClick = { Log.d("juuh", "nappi")}) {
           Text("paina")
        }
    }
}
