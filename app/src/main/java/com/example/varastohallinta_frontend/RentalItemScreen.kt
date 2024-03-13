package com.example.varastohallinta_frontend

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.varastohallinta_frontend.viewmodel.RentalItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalItemScreen(onMenuClick: () -> Unit,
                     gotoRentalItemAdd: () -> Unit
){
    val rentalItemsVm : RentalItemViewModel = viewModel()

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = { gotoRentalItemAdd()}) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "add") }

        },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onMenuClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(id = R.string.menu)
                        )
                    }
                },
                title = { Text(text = "" )})
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                rentalItemsVm.rentalItemsState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                rentalItemsVm.rentalItemsState.value.error != null ->
                    Text(text = stringResource(id = R.string.error) + ": ${rentalItemsVm.rentalItemsState.value.error}")

                //TODO poista rentalitem
                //Opens the delete screen prompt when selected id is not 0
                /*categoriesVm.categoryDeleteState.value.id > 0 -> ConfirmCategoryDelete(
                    error = categoriesVm.categoryDeleteState.value.error,
                    onDismiss = { categoriesVm.setDeletableCategoryId(0) },
                    onConfirm = { categoriesVm.deleteCategory() })*/

                else -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(rentalItemsVm.rentalItemsState.value.list) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),

                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                //ItemImage()
                                Text(
                                    it.rentalItemName,
                                    style = MaterialTheme.typography.headlineLarge
                                )

                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { /*categoriesVm.setDeletableCategoryId(it.categoryId) */}) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = stringResource(id = R.string.delete)
                                    )
                                }
                                IconButton(onClick = {/*TODO muokkaa rental item gotoCategoryEdit(it) */}) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = stringResource(id = R.string.add_item)
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
