package com.example.varastohallinta_frontend.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.varastohallinta_frontend.api.categoriesService
import com.example.varastohallinta_frontend.model.AddCategoryReq
import com.example.varastohallinta_frontend.model.CategoryState
import com.example.varastohallinta_frontend.model.UpdateCategoryReq
import kotlinx.coroutines.launch

class CategoryAddViewModel : ViewModel(){
    private val _categoryState = mutableStateOf(CategoryState())
    val categoryState: State<CategoryState> = _categoryState

    fun setDone(done: Boolean){
        _categoryState.value = _categoryState.value.copy(done=done)
    }

    fun setName(newName: String) {
        _categoryState.value = _categoryState.value.copy(categoryName = newName)
    }

    fun addCategory() {
        viewModelScope.launch {
            try {

                _categoryState.value = _categoryState.value.copy(loading = true)
                categoriesService.addCategory(
                    AddCategoryReq(
                        categoryName = _categoryState.value.categoryName
                    )
                )

                setDone(true)


            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(error = e.toString())
            } finally {
                _categoryState.value = _categoryState.value.copy(loading = false)
            }
        }
    }
}