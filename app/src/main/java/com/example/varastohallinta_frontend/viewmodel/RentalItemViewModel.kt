package com.example.varastohallinta_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.varastohallinta_frontend.api.rentalItemsServices
import com.example.varastohallinta_frontend.model.CategoriesState
import com.example.varastohallinta_frontend.model.RentalItemsState
import kotlinx.coroutines.launch

class RentalItemViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0
    private val _rentalItemsState = mutableStateOf(RentalItemsState())
    val rentalItemsState: State<RentalItemsState> = _rentalItemsState

    init {
        getRentalItemsByCategory()
    }

    fun getRentalItemsByCategory(){
        viewModelScope.launch {
            try {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = true)
                val rentalItemsByCategoryRes = rentalItemsServices.getItemsByCategory(categoryId)
                _rentalItemsState.value = _rentalItemsState.value.copy(list = rentalItemsByCategoryRes.list)
                Log.d("test","test")
            }
            catch (e: Exception){
                Log.d("test", e.toString())
            }
            finally {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = false)
            }
        }
    }
}