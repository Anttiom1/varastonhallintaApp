package com.example.varastohallinta_frontend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.varastohallinta_frontend.viewmodel.CategoryEditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryEditScreen(goToCategories: () -> Unit, goBack: () -> Unit) {
    val categoryEditViewModel: CategoryEditViewModel = viewModel()
    
    LaunchedEffect(key1 = categoryEditViewModel.categoryState.value.done){
        if(categoryEditViewModel.categoryState.value.done){
            categoryEditViewModel.setDone(false)
            goToCategories()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = categoryEditViewModel.categoryState.value.categoryName)
            })
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                categoryEditViewModel.categoryState.value.loading -> CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )

                categoryEditViewModel.categoryState.value.error != null -> Text(text = stringResource(id = R.string.error) +": ${categoryEditViewModel.categoryState.value.error}")
                else -> Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = categoryEditViewModel.categoryState.value.categoryName,
                        onValueChange = { name ->
                            categoryEditViewModel.setName(name)
                        })
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Button(onClick = {
                            categoryEditViewModel.editCategory()
                            
                        }) {
                            Text(text = stringResource(id = R.string.edit))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { goBack() }) {
                            Text(stringResource(id = R.string.back))
                        }
                    }
                }
            }
        }
    }
}