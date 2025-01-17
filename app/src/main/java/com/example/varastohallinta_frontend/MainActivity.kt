package com.example.varastohallinta_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.varastohallinta_frontend.ui.theme.Varastohallinta_frontendTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkMode by remember { mutableStateOf(false) }
            Varastohallinta_frontendTheme(darkTheme = darkMode) {
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
                                    label = { Text(text = stringResource(id = R.string.categories_title)) },
                                    selected = false,
                                    onClick = { navController.navigate("categoriesScreen")
                                              scope.launch { drawerState.close() }},
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.List,
                                            contentDescription = stringResource(R.string.categories))
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                NavigationDrawerItem(
                                    label = { Text(text = stringResource(id = R.string.settings)) },
                                    selected = false,
                                    onClick = { navController.navigate("settingsScreen")
                                        scope.launch { drawerState.close() }},
                                    icon = {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.baseline_settings_24),
                                            contentDescription = stringResource(R.string.settings))
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                NavigationDrawerItem(
                                    label = { Text(stringResource(R.string.logout)) },
                                    selected = false,
                                    onClick = { navController.navigate("logoutScreen")
                                              scope.launch { drawerState.close() }},
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Filled.Lock,
                                            contentDescription = stringResource(R.string.login))
                                    }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }) {

                        //Navhost
                        NavHost(navController = navController, startDestination = "loginScreen"){
                            composable(route="loginScreen"){
                                LoginScreen(goToLandingScreen = {navController.navigate("categoriesScreen")},
                                    goToCreateAccount = {navController.navigate("createAccountScreen")})
                            }
                            composable(route="settingsScreen" ){
                                SettingsScreen(
                                    darkMode = darkMode,
                                    onMenuClick = { scope.launch { drawerState.open() } },
                                    //Toggle dark mode
                                    onDarkModeClicked = {darkMode = !darkMode})
                            }
                            composable("categoriesScreen") {
                                CategoriesScreen(onMenuClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }, gotoCategoryEdit = {
                                    navController.navigate("categoryEditScreen/${it.categoryId}")
                                }, gotoCategoryAdd = {
                                    navController.navigate("categoryAddScreen")
                                },
                                    gotoRentalItemScreen = {
                                        navController.navigate("rentalItemScreen/${it.categoryId}/items")
                                    })
                            }
                            composable("categoryEditScreen/{categoryId}") {
                                CategoryEditScreen(goToCategories = {
                                    navController.navigate("categoriesScreen")
                                }, goBack = {
                                    navController.navigateUp()
                                })
                            }
                            composable("categoryAddScreen"){
                                CategoryAddScreen(goBack = {
                                    navController.navigateUp()
                                }, goToCategories = {
                                    navController.navigate("categoriesScreen")
                                })
                            }
                            composable("rentalItemScreen/{categoryId}/items"){
                                RentalItemScreen(onBackArrowClick = {
                                    navController.navigate("categoriesScreen")
                                }, gotoRentalItemAdd = {
                                    navController.navigate("rentalItemAddScreen/${it}")
                                }, goToRentalItemEdit = {
                                    navController.navigate("rentalEditScreen/${it.rentalItemId}")},)
                            }
                            composable("rentalItemAddScreen/{categoryId}"){
                                RentalItemAddScreen(goBack = {
                                    navController.navigateUp()
                                }, goToRentalItemScreen = {
                                    navController.navigate("rentalItemScreen/${it}/items")
                                })
                            }
                            composable("logoutScreen"){
                                LogoutScreen(
                                    goToLoginScreen = { navController.navigate("loginScreen"){
                                        popUpTo("loginScreen"){
                                            inclusive = true
                                        }
                                    }
                                    }
                                )
                            }
                            composable("createAccountScreen"){
                                CreateAccountScreen(goBack = {navController.navigateUp()})
                            }
                            composable("rentalEditScreen/{rentalItemId}"){
                                RentalItemEditScreen(goBack = { navController.navigateUp() },
                                    gotoRentalItemScreen = {
                                        navController.navigate("rentalItemScreen/${it}/items")}
                                    )
                            }
                        }
                    }
                }
                }
            }
        }
    }