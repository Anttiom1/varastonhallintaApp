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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalItemScreen(onBackArrowClick: () -> Unit,
                     gotoRentalItemAdd: (Int) -> Unit
){
    val rentalItemViewModel : RentalItemViewModel = viewModel()

    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(onClick = { gotoRentalItemAdd(rentalItemViewModel.categoryId); Log.d("jyy", rentalItemViewModel.categoryId.toString())}) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "add") }

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
                    error = rentalItemViewModel.rentalItemDeleteState.value.error,
                    onDismiss = { rentalItemViewModel.setDeletableItemId(0) },
                    onConfirm = { rentalItemViewModel.deleteItem() })

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
                                IconButton(onClick = { rentalItemViewModel.setDeletableItemId(it.rentalItemId) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = stringResource(id = R.string.delete)
                                    )
                                }
                                IconButton(onClick = {/*TODO muokkaa rental item. gotoCategoryEdit(it) */}) {
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

@Composable
fun ConfirmItemDelete(error: String?, onDismiss: () -> Unit, onConfirm: () -> Unit){
    val context = LocalContext.current
    LaunchedEffect(key1 = error){
        error?.let {
            Log.d("opop", error.toString())
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
    AlertDialog(
        onDismissRequest = { },
        confirmButton = { TextButton(
            onClick = { onConfirm()})
        {
            Text(text = "Confirm")
        } },
        dismissButton = { TextButton(
            onClick = { onDismiss() })
        {
            Text(text = "Cancel")
        } },
        icon =  {Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete)
        ) }, 
        text = { Text(text = "Are you sure you want to delete this item?")},
        title = { Text(text = "Delete item")}
        )
}
