package com.machado.courseracapstone

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController, sharedPreferences: SharedPreferences) {
    val firstName = sharedPreferences.getString("sharedPref.FIRST_NAME", "") ?: ""
    val lastName = sharedPreferences.getString("sharedPref.LAST_NAME", "") ?: ""
    val email = sharedPreferences.getString("sharedPref.EMAIL", "") ?: ""
    Column {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo"
        )
        Text(text = "Profile Information")
        TextField(value = firstName, onValueChange = {}, enabled = false)
        TextField(value = lastName, onValueChange = {}, enabled = false)
        TextField(value = email, onValueChange = {}, enabled = false)
        Button(onClick = {
            navigateToOnBoarding(sharedPreferences, navController)
        }) {
            Text(text = "Log out")
        }
    }
}

private fun navigateToOnBoarding(
    sharedPreferences: SharedPreferences,
    navController: NavHostController
) {
    navController.navigate(OnBoarding.route) {
        popUpTo(Home.route) { inclusive = true }
    }
    sharedPreferences.edit().clear().apply()
}