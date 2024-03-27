package com.example.varastohallinta_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.varastohallinta_frontend.DbProvider
import com.example.varastohallinta_frontend.api.authService
import com.example.varastohallinta_frontend.api.categoriesService
import com.example.varastohallinta_frontend.api.rentalItemsServices
import com.example.varastohallinta_frontend.model.CategoriesState
import com.example.varastohallinta_frontend.model.CategoryItem
import com.example.varastohallinta_frontend.model.RentItemReq
import com.example.varastohallinta_frontend.model.RentalItemDeleteState
import com.example.varastohallinta_frontend.model.RentalItemRentState
import com.example.varastohallinta_frontend.model.RentalItemState
import com.example.varastohallinta_frontend.model.RentalItemsState
import kotlinx.coroutines.launch

class RentalItemViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val categoryId = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0
    private val _rentalItemsState = mutableStateOf(RentalItemsState())
    val rentalItemsState: State<RentalItemsState> = _rentalItemsState
    private val _rentalItemDeleteState = mutableStateOf(RentalItemDeleteState())
    val rentalItemDeleteState = _rentalItemDeleteState
    private val _rentalItemRentState = mutableStateOf(RentalItemRentState())
    val rentalItemRentState = _rentalItemRentState
    private val _rentItemReq = mutableStateOf(RentItemReq())
    val rentItemReq: State<RentItemReq> = _rentItemReq

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

    fun setRentItemId(id: Int){
        rentalItemRentState.value = rentalItemRentState.value.copy(id = id)
    }

    fun deleteItem(){
        viewModelScope.launch {
            try {
                rentalItemsServices.removeItem(_rentalItemDeleteState.value.id)
                val items = _rentalItemsState.value.list.filter {
                    it.rentalItemId != _rentalItemDeleteState.value.id
                }

                _rentalItemsState.value = _rentalItemsState.value.copy(list = items)

                //Sets id to 0 so that the delete screen goes away
                setDeletableItemId(0)

            } catch(e: Exception) {
                Log.d("asd", e.toString())
                _rentalItemDeleteState.value = _rentalItemDeleteState.value.copy(error = e.toString())
            }
        }
    }

    fun rentItem(){
        viewModelScope.launch {
            try {
                val accessToken = DbProvider.db.accountDao().getToken()
                accessToken?.let {
                    val loggedUser = authService.getAccount("bearer $it")
                    _rentItemReq.value = _rentItemReq.value.copy(userId = loggedUser.authUserId)
                }
                rentalItemsServices.rentItem(_rentalItemRentState.value.id, reqBody = rentItemReq.value)
                setRentItemId(0)
            }
            catch (e: Exception){
                _rentalItemRentState.value = _rentalItemRentState.value.copy(error = e.toString())
                Log.d("antti", e.toString())
            }
        }
    }

    fun getRentalItemsByCategory(){
        viewModelScope.launch {
            try {
                _rentalItemsState.value = _rentalItemsState.value.copy(loading = true)
                val rentalItemsByCategoryRes = rentalItemsServices.getItemsByCategory(categoryId)
                _rentalItemsState.value = _rentalItemsState.value.copy(list = rentalItemsByCategoryRes.list)
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