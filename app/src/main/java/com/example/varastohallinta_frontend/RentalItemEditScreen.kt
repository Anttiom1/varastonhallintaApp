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
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.varastohallinta_frontend.viewmodel.RentalItemEditViewModel
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalItemEditScreen(goBack:()-> Unit,
                         gotoRentalItemScreen: (Int)-> Unit){
    val vm: RentalItemEditViewModel = viewModel()
    LaunchedEffect(key1 = vm.rentalItemState.value.done){
        if(vm.rentalItemState.value.done){
            vm.setDone(false)
            gotoRentalItemScreen(vm.rentalItemEditRes.category.categoryId)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "")
            })
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                vm.rentalItemState.value.loading -> CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )

                vm.rentalItemState.value.error != null -> Text(text = stringResource(id = R.string.error) +": ${vm.rentalItemState.value.error}")
                else -> Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = vm.rentalItemState.value.rentalItemName,
                        onValueChange = { name ->
                            vm.setName(name)
                        },
                        placeholder = { Text(stringResource(R.string.edit_item))})
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Button(onClick = {
                            vm.editRentalItem()
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