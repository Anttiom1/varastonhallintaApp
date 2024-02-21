package com.example.varastohallinta_frontend.viewmodel

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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

    private suspend fun waitForCategories(){
        delay(2000)
    }

    fun deleteCategory(category: CategoryItem){
        val categories = _categoriesState.value.list.filter {
            // aina kun ehto on totta (true), categoryItem (it) menee listaan (categories)
            // jäljelle jäävät kaikki muut, paitsi category
            it.id != category.id
        }
        _categoriesState.value = _categoriesState.value.copy(list=categories)
    }

    private fun getCategories(){
        viewModelScope.launch {
            _categoriesState.value = _categoriesState.value.copy(loading = true)
            waitForCategories()
            _categoriesState.value = _categoriesState.value.copy(list= listOf(
                CategoryItem(id=1, name = "Kategoria 1"),
                CategoryItem(id=2, name = "Kategoria 2"),
                CategoryItem(id=3, name = "Kategoria 3")
            ), loading = false)
        }
    }
}