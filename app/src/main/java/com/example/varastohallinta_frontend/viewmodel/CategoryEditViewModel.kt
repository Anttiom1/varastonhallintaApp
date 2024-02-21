package com.example.varastohallinta_frontend.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CategoryEditViewModel(savedStateHandle: SavedStateHandle) : ViewModel(){
    val id = savedStateHandle.get<String>("categoryId")?.toIntOrNull() ?: 0

    init {
        Log.d("antti", "$id")
    }
}