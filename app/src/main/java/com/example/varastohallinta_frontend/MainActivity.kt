package com.example.varastohallinta_frontend

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                    Greetings("Juuh")

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
