package com.example.varastohallinta_frontend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onMenuClick: () -> Unit) {

    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { onMenuClick() }){
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu")
                }
            },
            title = { Text(text = "Categories") },)
    }){
        Surface(
            modifier = Modifier.fillMaxSize()
                .padding(it),
            color = MaterialTheme.colorScheme.background
        ) {
            MultipleSwitchesColumn()
        }
    }


}



@Composable
fun MultipleSwitchesColumn() {
    // Create a list of switch items
    val switchItems = listOf(
        SwitchItem("Dark Mode"),
        SwitchItem("Esim 2"),

        // Add more items as needed
    )

    // Create a column with switches
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        switchItems.forEach { switchItem ->
            SwitchItemRow(switchItem)
        }
    }
}

@Composable
fun SwitchItemRow(switchItem: SwitchItem) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Text
        Text(
            text = switchItem.title,
            modifier = Modifier.weight(1f)
        )

        // Switch
        Switch(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

data class SwitchItem(val title: String)