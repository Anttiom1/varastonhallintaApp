package com.example.varastohallinta_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.varastohallinta_frontend.api.rentalItemsServices
import com.example.varastohallinta_frontend.model.AddRentalItemReq
import com.example.varastohallinta_frontend.model.RentalItemState
import com.example.varastohallinta_frontend.model.RentalItemsState
import kotlinx.coroutines.launch

class RentalItemAddToCategoryViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    val categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0
    private val _rentalItemState = mutableStateOf(RentalItemState())
    val rentalItemState : State<RentalItemState> = _rentalItemState

    fun setDone(done: Boolean){
        _rentalItemState.value = _rentalItemState.value.copy(done = done)
    }

    fun setName(newName: String){
        _rentalItemState.value = _rentalItemState.value.copy(rentalItemName = newName)
    }

    fun addItemToCategory(){
        viewModelScope.launch {
            try {
                _rentalItemState.value = _rentalItemState.value.copy(loading = true)
                rentalItemsServices.addItemToCategory(categoryId, AddRentalItemReq(rentalItemName = _rentalItemState.value.rentalItemName))
                setDone(true)
            }
            catch (e: Exception){
                Log.d("juuh", e.toString())
            }
            finally {
                _rentalItemState.value = _rentalItemState.value.copy(loading = false)
            }

        }
    }
}