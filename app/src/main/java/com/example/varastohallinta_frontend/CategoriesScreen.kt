package com.example.varastohallinta_frontend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.varastohallinta_frontend.model.CategoryItem
import com.example.varastohallinta_frontend.viewmodel.CategoriesViewModel
import java.time.LocalDateTime


@Composable
fun ItemImage() {
    AsyncImage(
        model = "https://picsum.photos/seed/${LocalDateTime.now()}/200",
        contentDescription = null,
    )
}

@Composable
fun ConfirmCategoryDelete(onDismiss: () -> Unit, onConfirm: () -> Unit){
    AlertDialog(onDismissRequest = {  },
        dismissButton = { TextButton(onClick = { onDismiss() }) {
            Text(text = "Cancel")
        }},
        confirmButton = { TextButton(onClick = { onConfirm() }) {
            Text(text = "Confirm")
        } },
        icon = { Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete category"
    )}, text = {Text("Are you sure you want to delete this category?")},
        title = {Text("Delete Category") },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(onMenuClick: () -> Unit, gotoCategoryEdit: (CategoryItem) -> Unit) {
    val categoriesVm: CategoriesViewModel = viewModel()

    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    onMenuClick()
                }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
            },
            title = { Text(text = "Categories") })
    }) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                categoriesVm.categoriesState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                categoriesVm.categoriesState.value.error != null ->
                    Text(text = "error: ${categoriesVm.categoriesState.value.error}")

                categoriesVm.categoryDeleteState.value.id > 0 -> ConfirmCategoryDelete(
                    onDismiss = { categoriesVm.setDeletableCategoryId(0) },
                    onConfirm = {categoriesVm.deleteCategory()})

                else -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(categoriesVm.categoriesState.value.list) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier

                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                ItemImage()
                                Text(it.categoryName, style = MaterialTheme.typography.headlineLarge)

                            }
                            Row(
                                modifier = Modifier

                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { categoriesVm.setDeletableCategoryId(it.categoryId) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete"
                                    )
                                }
                                IconButton(onClick = { gotoCategoryEdit(it) }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}