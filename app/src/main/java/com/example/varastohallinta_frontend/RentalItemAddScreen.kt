package com.example.varastohallinta_frontend

import android.util.Log
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
import com.example.varastohallinta_frontend.viewmodel.RentalItemAddViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalItemAddScreen(
                    goBack: () -> Unit,
                    goToRentalItemScreen: (Int) -> Unit, ){
    val rentalItemAddViewModel: RentalItemAddViewModel = viewModel()

    LaunchedEffect(key1 = rentalItemAddViewModel.rentalItemState.value.done) {
        if (rentalItemAddViewModel.rentalItemState.value.done) {
            rentalItemAddViewModel.setDone(false)
            goToRentalItemScreen(rentalItemAddViewModel.categoryId)
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.add_item)) })}) {
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
                OutlinedTextField(value = rentalItemAddViewModel.rentalItemState.value.rentalItemName,
                    onValueChange = { rentalItemAddViewModel.setName(it) },
                    placeholder = { Text(stringResource(R.string.item_name))})
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = {
                        rentalItemAddViewModel.addItemToCategory()
                    }) {
                        Text(text = stringResource(id = R.string.add_item))
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
