package com.example.varastohallinta_frontend

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.varastohallinta_frontend.viewmodel.RentalItemViewModel

@Composable
fun ItemScreen(){
    val vm : RentalItemViewModel = viewModel()
    Text(text = vm.categoriesState.value.list.toString())
}