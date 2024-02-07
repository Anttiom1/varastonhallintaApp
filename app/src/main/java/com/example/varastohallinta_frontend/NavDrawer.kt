package com.example.varastohallinta_frontend

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NavBar(){
    ModalNavigationDrawer(
        drawerState = DrawerState(initialValue = DrawerValue.Open),
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                NavigationDrawerItem(
                    label = { Text(text = "Home") },
                    selected = true,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home Icon")
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                NavigationDrawerItem(
                    label = { Text(text = "Categories") },
                    selected = true,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "Categories Icon")
                    }
                )
            }
        }) {}
}