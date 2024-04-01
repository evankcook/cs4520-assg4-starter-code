package com.cs4520.assignment4

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cs4520.assignment4.view.LoginScreen
import com.cs4520.assignment4.view.ProductListScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier, navController: NavHostController, startDestination:
String = NavigationItem.Login.route) {

    NavHost(modifier = modifier, navController = navController, startDestination = startDestination) {
        composable(NavigationItem.Login.route) {
            LoginScreen(navController)
        }
        composable(NavigationItem.ProductList.route) {
            ProductListScreen(navController)
        }
    }
}

enum class Screen {
    LOGIN, PRODUCT_LIST
}

sealed class NavigationItem(val route: String) {
    object Login : NavigationItem(Screen.LOGIN.name)
    object ProductList : NavigationItem(Screen.PRODUCT_LIST.name)
}
