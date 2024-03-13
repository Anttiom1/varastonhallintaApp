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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.varastohallinta_frontend.viewmodel.CategoryAddViewModel
import com.example.varastohallinta_frontend.viewmodel.RentalItemAddToCategoryViewModel

@Composable
fun RentalItemAddScreen(goBack: () -> Unit,
                      goToCategories: () -> Unit){
    val vm: RentalItemAddToCategoryViewModel = viewModel()

    LaunchedEffect(key1 = vm.rentalItemState.value.done) {
        if (vm.rentalItemState.value.done) {
            vm.setDone(false)
            goToCategories()
        }
    }

    Scaffold {
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
                OutlinedTextField(value = vm.rentalItemState.value.rentalItemName,
                    onValueChange = { vm.setName(it) })
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = {
                        vm.addItemToCategory(/*TODO: Get the category Id and store it all the way here to replace that 3 with the real categoryID*/ 3)

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
