package com.example.varastohallinta_frontend.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.varastohallinta_frontend.model.CategoriesState

class RentalItemViewModel : ViewModel() {
    private val _categoriesState = mutableStateOf(CategoriesState())
    val categoriesState: State<CategoriesState> = _categoriesState
}