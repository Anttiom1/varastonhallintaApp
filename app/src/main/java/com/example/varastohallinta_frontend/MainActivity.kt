package com.example.varastohallinta_frontend

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.varastohallinta_frontend.ui.theme.Varastohallinta_frontendTheme
import com.example.varastohallinta_frontend.viewmodel.LoginViewModel
import com.example.varastohallinta_frontend.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settingsViewModel : SettingsViewModel = viewModel()
            Varastohallinta_frontendTheme(darkTheme = settingsViewModel.userSettingsState.value.darkMode) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()

                    ModalNavigationDrawer(
                        gesturesEnabled = true,
                        drawerState = drawerState,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(16.dp))
                                NavigationDrawerItem(
                                    label = { Text(text = "Home") },
                                    selected = false,
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
                                    selected = false,
                                    onClick = { navController.navigate("categoriesScreen")
                                              scope.launch { drawerState.close() }},
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.List,
                                            contentDescription = "Categories Icon")
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                NavigationDrawerItem(
                                    label = { Text(text = "Settings") },
                                    selected = false,
                                    onClick = { navController.navigate("settingsScreen")
                                        scope.launch { drawerState.close() }},
                                    icon = {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.baseline_settings_24),
                                            contentDescription = "Settings icon")
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                NavigationDrawerItem(
                                    label = { Text(text = "Logout") },
                                    selected = false,
                                    onClick = { navController.navigate("loginScreen")
                                              scope.launch { drawerState.close() }},
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Lock,
                                            contentDescription = "Login Icon")
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }) {

                        //Navhost
                        NavHost(navController = navController, startDestination = "loginScreen"){
                            composable(route="loginScreen"){
                                LoginScreen(onLoginClick = {navController.navigate("categoriesScreen")})
                            }
                            composable(route="settingsScreen", ){
                                SettingsScreen(
                                    settingsViewModel = settingsViewModel,
                                    onMenuClick = { scope.launch { drawerState.open() } },
                                    //Toggle dark mode
                                    onDarkModeClicked = {settingsViewModel.setDarkMode(it)},
                                    onTestClick = {settingsViewModel.setTestMode(it)})
                            }
                            composable("categoriesScreen") {
                                CategoriesScreen(onMenuClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }, gotoCategoryEdit = {
                                    navController.navigate("categoryEditScreen/${it.categoryId}")
                                })
                            }
                            composable("categoryEditScreen/{categoryId}") {
                                CategoryEditScreen(goToCategories = {
                                    navController.navigate("categoriesScreen")
                                }, goBack = {
                                    navController.navigateUp()
                                })
                            }
                        }
                    }
                }
                }
            }
        }
    }