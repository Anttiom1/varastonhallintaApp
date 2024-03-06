package com.example.varastohallinta_frontend.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.varastohallinta_frontend.api.categoriesService
import com.example.varastohallinta_frontend.model.CategoryState
import com.example.varastohallinta_frontend.model.UpdateCategoryReq
import kotlinx.coroutines.launch

class CategoryEditViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    val id = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0
    private val _categoryState = mutableStateOf(CategoryState())
    val categoryState: State<CategoryState> = _categoryState

    fun setName(newName: String) {
        _categoryState.value = _categoryState.value.copy(categoryName = newName)
    }

    fun editCategory(goToCategories: () -> Unit) {
        viewModelScope.launch {
            try {

                _categoryState.value = _categoryState.value.copy(loading = true)
                categoriesService.editCategory(
                    id, UpdateCategoryReq(
                        categoryName = _categoryState.value.categoryName
                    )
                )

                goToCategories()


            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(error = e.toString())
            } finally {
                _categoryState.value = _categoryState.value.copy(loading = false)
            }
        }
    }

    private fun getCategoryById() {

        viewModelScope.launch {
            try {
                _categoryState.value = _categoryState.value.copy(loading = true)
                val response = categoriesService.getCategoryById(id)
                _categoryState.value =
                    _categoryState.value.copy(categoryName = response.category.categoryName)


            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(error = e.toString())
            } finally {
                _categoryState.value = _categoryState.value.copy(loading = false)
            }
        }
    }

    init {
        getCategoryById()
    }


}