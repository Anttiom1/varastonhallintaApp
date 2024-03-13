package com.example.varastohallinta_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.varastohallinta_frontend.api.categoriesService
import com.example.varastohallinta_frontend.model.CategoriesState
import com.example.varastohallinta_frontend.model.CategoryDeleteState
import com.example.varastohallinta_frontend.model.CategoryItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CategoriesViewModel : ViewModel() {


    private val _categoriesState = mutableStateOf(CategoriesState())
    val categoriesState: State<CategoriesState> = _categoriesState
    private val _categoryDeleteState = mutableStateOf(CategoryDeleteState())
    val categoryDeleteState = _categoryDeleteState

    init {
        getCategories()
    }

    fun setDeletableCategoryId(id: Int){
        _categoryDeleteState.value = _categoryDeleteState.value.copy(id = id)
    }


    fun deleteCategory() {
        viewModelScope.launch {
            try {
                categoriesService.removeCategory(_categoryDeleteState.value.id)
                val categories = _categoriesState.value.list.filter {
                    it.categoryId != _categoryDeleteState.value.id
                }

                //Sets id to 0 so that the delete screen goes away
                setDeletableCategoryId(0)

                _categoriesState.value = _categoriesState.value.copy(list = categories)
            } catch(e: Exception) {
                Log.d("asd", e.toString())
                _categoryDeleteState.value = _categoryDeleteState.value.copy(error = e.toString())
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                Log.d("asd", "inside getCategories")
                _categoriesState.value = _categoriesState.value.copy(loading = true)
                val categoriesRes = categoriesService.getCategories()
                _categoriesState.value =
                    _categoriesState.value.copy(list = categoriesRes.categories)
                Log.d("asd", "fetching categories done")
                //_categoriesState.value = _categoriesState.value.copy(loading = false)
            } catch (e: Exception) {
                Log.d("asd", e.toString())
                //_categoriesState.value = _categoriesState.value.copy(loading = false)
                _categoriesState.value = _categoriesState.value.copy(error = e.toString())
            } finally {
                _categoriesState.value = _categoriesState.value.copy(loading = false)

            }
        }
    }

}