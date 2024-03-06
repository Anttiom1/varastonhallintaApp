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
import com.example.varastohallinta_frontend.model.CategoryItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CategoriesViewModel : ViewModel() {


    private val _categoriesState = mutableStateOf(CategoriesState())
    val categoriesState: State<CategoriesState> = _categoriesState

    init {
        getCategories()
    }


    fun deleteCategory(category: CategoryItem) {
        viewModelScope.launch {
            try {
                categoriesService.removeCategory(category.categoryId)
                val categories = _categoriesState.value.list.filter {
                    // aina kun ehto on totta (true), categoryItem (it) menee listaan (categories)
                    // jäljelle jäävät kaikki muut, paitsi category
                    it.categoryId != category.categoryId
                }

                _categoriesState.value = _categoriesState.value.copy(list = categories)
            } catch(e: Exception) {
                Log.d("juhani", e.toString())
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                Log.d("juhani", "inside getCategories")
                _categoriesState.value = _categoriesState.value.copy(loading = true)
                val categoriesRes = categoriesService.getCategories()
                _categoriesState.value =
                    _categoriesState.value.copy(list = categoriesRes.categories)
                Log.d("juhani", "fetching catgories done")
                //_categoriesState.value = _categoriesState.value.copy(loading = false)
            } catch (e: Exception) {
                Log.d("juhani", e.toString())
                //_categoriesState.value = _categoriesState.value.copy(loading = false)
                _categoriesState.value = _categoriesState.value.copy(error = e.toString())
            } finally {
                _categoriesState.value = _categoriesState.value.copy(loading = false)
            }
        }
    }

}