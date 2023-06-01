package com.machado.courseracapstone

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController, sharedPreferences: SharedPreferences) {
    val firstName = sharedPreferences.getString("sharedPref.FIRST_NAME", "") ?: ""
    val lastName = sharedPreferences.getString("sharedPref.LAST_NAME", "") ?: ""
    val email = sharedPreferences.getString("sharedPref.EMAIL", "") ?: ""
//    Column {
//        Image(
//            painter = painterResource(id = R.drawable.logo),
//            contentDescription = "Little Lemon Logo"
//        )
//        Text(text = "Profile Information")
//        TextField(value = firstName, onValueChange = {}, enabled = false)
//        TextField(value = lastName, onValueChange = {}, enabled = false)
//        TextField(value = email, onValueChange = {}, enabled = false)
//        Button(onClick = {
//            navigateToOnBoarding(sharedPreferences, navController)
//        }) {
//            Text(text = "Log out")
//        }
//    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(100.dp)
                .width(200.dp)
        )
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
        ) {
            Text(
                text = "Personal Information",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Text(text = "First Name")
            OutlinedTextField(
                value = firstName,
                onValueChange = {},
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                enabled = false
            )

            Text(text = "Last Name")
            OutlinedTextField(
                value = lastName,
                onValueChange = { },
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                enabled = false
            )

            Text(text = "Email")
            OutlinedTextField(
                value = email,
                onValueChange = { },
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )
        }

        Button(
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                navigateToOnBoarding(sharedPreferences, navController)
            }
        ) {
            Text(text = "Log out", color = MaterialTheme.colorScheme.onSecondary)
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