package com.example.varastohallinta_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.varastohallinta_frontend.api.rentalItemsServices
import com.example.varastohallinta_frontend.model.RentalItemState
import com.example.varastohallinta_frontend.model.RentalItemsState
import com.example.varastohallinta_frontend.model.UpdateItemReq
import com.example.varastohallinta_frontend.model.UpdateItemRes
import kotlinx.coroutines.launch

class RentalItemEditViewModel(savedStateHandle: SavedStateHandle): ViewModel() {
    val id = savedStateHandle.get<String>("rentalItemId")?.toIntOrNull() ?: 0

    private val _rentalItemState = mutableStateOf(RentalItemState())
    val rentalItemState: State<RentalItemState> = _rentalItemState
    lateinit var rentalItemEditRes: UpdateItemRes

    fun setDone(done: Boolean){
        _rentalItemState.value = _rentalItemState.value.copy(done = done)
    }

    fun setName(name: String){
        _rentalItemState.value = _rentalItemState.value.copy(rentalItemName = name)
    }

    fun editRentalItem(){
        viewModelScope.launch {
            try {
                _rentalItemState.value = _rentalItemState.value.copy(loading = true)
                rentalItemEditRes = rentalItemsServices.editItem(id, UpdateItemReq(
                    rentalItemName = _rentalItemState.value.rentalItemName)
                )

                setDone(true)
            }
            catch (e: Exception){
                _rentalItemState.value = _rentalItemState.value.copy(error = e.toString())
            }
            finally {
                _rentalItemState.value = _rentalItemState.value.copy(loading = false)
            }
        }
    }

}