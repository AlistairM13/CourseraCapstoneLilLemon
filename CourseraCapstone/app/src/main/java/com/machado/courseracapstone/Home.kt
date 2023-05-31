package com.machado.courseracapstone

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavHostController,
    menuItemsFromDb: List<MenuItemEntity>
) {
    var searchQuery by remember { mutableStateOf("") }
    var sortByTitle by remember { mutableStateOf(false) }
    var menuItems by remember { mutableStateOf(menuItemsFromDb) }
    menuItems = if (sortByTitle) menuItemsFromDb.sortedBy { it.title } else menuItemsFromDb

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(text = "Search") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) })
        Row(modifier = Modifier.fillMaxWidth()) {
            FilterHelper.FoodCategory.values().forEach {
                Button(onClick = {
                    menuItems = FilterHelper.filterBy(it, menuItemsFromDb)
                }) {
                    Text(text = it.name)
                }
            }
        }
        if(searchQuery.isNotBlank()){
            menuItems =
                menuItemsFromDb.filter { it.title.contains(searchQuery, ignoreCase = true) }
        }

        LazyColumn() {
            Log.i("MYTAG", "$menuItems")
            items(menuItems) { menuItem ->
                GlideImage(model = menuItem.image, contentDescription = "${menuItem.title} image")
                Text(text = menuItem.title)
                Text(text = menuItem.description)
                Text(text = menuItem.price)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }


    }
}


