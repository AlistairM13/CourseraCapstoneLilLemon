package com.machado.courseracapstone

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationComposable(navController: NavHostController, sharedPreferences: SharedPreferences, menuItems:List<MenuItemEntity>) {
    val startDestination =
        if (sharedPreferences.contains("sharedPref.FIRST_NAME")) Home.route else OnBoarding.route
    NavHost(navController = navController, startDestination = startDestination) {
        composable(OnBoarding.route) {
            OnBoarding(navController, sharedPreferences)
        }
        composable(Home.route) {
            Home(navController,menuItems)
        }
        composable(Profile.route) {
            Profile(navController, sharedPreferences)
        }

    }
}