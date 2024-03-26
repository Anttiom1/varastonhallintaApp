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
import com.example.varastohallinta_frontend.viewmodel.CategoryAddViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryAddScreen(goBack: () -> Unit,
                      goToCategories: () -> Unit){
    val categoryAddViewModel: CategoryAddViewModel = viewModel()

    LaunchedEffect(key1 = categoryAddViewModel.categoryState.value.done) {
        if (categoryAddViewModel.categoryState.value.done) {
            categoryAddViewModel.setDone(false)
            goToCategories()
        }
    }

        Scaffold(
            topBar = { TopAppBar(title = { Text(text = "Add new category")})}
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(value = categoryAddViewModel.categoryState.value.categoryName,
                        onValueChange = { categoryAddViewModel.setName(it) },
                        placeholder = { Text(text = "Category name")})
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Button(onClick = {
                            categoryAddViewModel.addCategory()

                        }) {
                            Text(text = stringResource(id = R.string.add_category))
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
