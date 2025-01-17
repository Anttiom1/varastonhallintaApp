package com.example.varastohallinta_frontend

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.varastohallinta_frontend.model.RentalItem
import com.example.varastohallinta_frontend.viewmodel.RentalItemViewModel

@Composable
fun ConfirmItemDelete(error: String?, onDismiss: ()-> Unit, onConfirm: ()-> Unit, rentalItemName: String){
    val context = LocalContext.current
    LaunchedEffect(key1 = error){
        error?.let {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
    AlertDialog(
        onDismissRequest = { },
        confirmButton = { TextButton(onClick = { onConfirm() }) { Text(stringResource(R.string.confirm)) }},
        dismissButton = { TextButton(onClick = { onDismiss() }) { Text(stringResource(R.string.cancel)) }},
        icon =  {Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete)
        ) },
        text = { Text(stringResource(R.string.delete_item_confirm))},
        title = { Text(text = rentalItemName)}
    )
}

@Composable
fun ConfirmItemRent(error: String?, onDismiss:()-> Unit, onConfirm:()-> Unit, rentalItemName: String ){
    val context = LocalContext.current
    LaunchedEffect(key1 = error){
        error?.let {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
    AlertDialog(
        onDismissRequest = { },
        confirmButton = { TextButton(onClick = { onConfirm() }) { Text(stringResource(R.string.confirm)) }},
        dismissButton = { TextButton(onClick = { onDismiss() }) { Text(stringResource(R.string.cancel)) }},
        icon =  {Icon(
            imageVector = Icons.Default.Check,
            contentDescription = stringResource(R.string.confirm))},
        text = { Text(stringResource(R.string.rent_item_confirm))},
        title = { Text(rentalItemName)}
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalItemScreen(onBackArrowClick: () -> Unit,
                     gotoRentalItemAdd: (Int) -> Unit,
                     goToRentalItemEdit: (RentalItem)-> Unit
){
    val rentalItemViewModel : RentalItemViewModel = viewModel()

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = { gotoRentalItemAdd(rentalItemViewModel.categoryId);}) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_category)) }

        },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        onBackArrowClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.menu)
                        )
                    }
                },
                title = { Text(text = rentalItemViewModel.categoryName)})
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                rentalItemViewModel.rentalItemsState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                rentalItemViewModel.rentalItemsState.value.error != null ->
                    Text(text = stringResource(id = R.string.error) + ": ${rentalItemViewModel.rentalItemsState.value.error}")

                //Opens the delete screen prompt when selected id is not 0
                rentalItemViewModel.rentalItemDeleteState.value.id > 0 -> ConfirmItemDelete(
                    rentalItemName = rentalItemViewModel.rentalItemDeleteState.value.rentalItemName,
                    error = rentalItemViewModel.rentalItemDeleteState.value.error,
                    onDismiss = { rentalItemViewModel.setDeletableItemId(0, "") },
                    onConfirm = { rentalItemViewModel.deleteItem() })

                //Opens the rent screen prompt when selected id is not 0
                rentalItemViewModel.rentalItemRentState.value.id > 0 -> ConfirmItemRent(
                    rentalItemName = rentalItemViewModel.rentalItemRentState.value.rentalItemName,
                    error = rentalItemViewModel.rentalItemRentState.value.error,
                    onDismiss = { rentalItemViewModel.setRentItemId(0, "") },
                    onConfirm = { rentalItemViewModel.rentItem() })

                else -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(rentalItemViewModel.rentalItemsState.value.list) {
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
                                IconButton(onClick = { rentalItemViewModel.setDeletableItemId(it.rentalItemId, it.rentalItemName) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = stringResource(id = R.string.delete)
                                    )
                                }
                                IconButton(onClick = { goToRentalItemEdit(it) }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = stringResource(id = R.string.add_item)
                                    )
                                }
                                IconButton(onClick = { rentalItemViewModel.setRentItemId(it.rentalItemId, it.rentalItemName) }) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
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

