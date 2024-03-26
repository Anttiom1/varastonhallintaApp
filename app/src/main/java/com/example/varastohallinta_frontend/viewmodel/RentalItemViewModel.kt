package com.example.varastohallinta_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.varastohallinta_frontend.api.categoriesService
import com.example.varastohallinta_frontend.api.rentalItemsServices
import com.example.varastohallinta_frontend.model.CategoriesState
import com.example.varastohallinta_frontend.model.CategoryItem
import com.example.varastohallinta_frontend.model.RentalItemDeleteState
import com.example.varastohallinta_frontend.model.RentalItemsState
import kotlinx.coroutines.launch

class RentalItemViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0
    private val _rentalItemsState = mutableStateOf(RentalItemsState())
    val rentalItemsState: State<RentalItemsState> = _rentalItemsState
    private val _rentalItemDeleteState = mutableStateOf(RentalItemDeleteState())
    val rentalItemDeleteState = _rentalItemDeleteState
    var categoryName: String = ""

    init {
        viewModelScope.launch {
            getRentalItemsByCategory()
            categoryName = categoriesService.getCategoryById(categoryId).category.categoryName
        }
    }


    fun setDeletableItemId(id: Int){
        rentalItemDeleteState.value = rentalItemDeleteState.value.copy(id = id)
    }

    fun deleteItem(){
        viewModelScope.launch {
            try {
                rentalItemsServices.removeItem(_rentalItemDeleteState.value.id)
                val categories = _rentalItemsState.value.list.filter {
                    it.categoryId != _rentalItemDeleteState.value.id
                }

                _rentalItemsState.value = _rentalItemsState.value.copy(list = categories)

                //Sets id to 0 so that the delete screen goes away
                setDeletableItemId(0)

            } catch(e: Exception) {
                Log.d("asd", e.toString())
                _rentalItemDeleteState.value = _rentalItemDeleteState.value.copy(error = e.toString())
            }
        }
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