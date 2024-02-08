package com.example.varastohallinta_frontend

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(onMenuClick: () -> Unit){
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                             IconButton(onClick = { onMenuClick() }){
                                 Icon(
                                     imageVector = Icons.Default.Menu,
                                     contentDescription = "Menu")
                             }
            },
            title = { Text(text = "Categories") },)
    }){
        LazyColumn(modifier = Modifier.padding(it)){}
    }
}