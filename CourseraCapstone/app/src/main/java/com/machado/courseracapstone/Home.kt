package com.machado.courseracapstone


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import java.util.Locale

@Composable
fun Home(
    navController: NavHostController,
    menuItemsFromDb: List<MenuItemEntity>
) {
    var searchQuery by remember { mutableStateOf("") }
    var menuItems by remember { mutableStateOf(menuItemsFromDb) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(navController)

        TopSection(searchQuery) {
            searchQuery = it
        }

        MenuFilter {
            menuItems = FilterHelper.filterBy(it, menuItemsFromDb)
        }

        menuItems = if (searchQuery.isNotBlank()) {
            menuItemsFromDb.filter { it.title.contains(searchQuery, ignoreCase = true) }
        }else{
            menuItemsFromDb
        }

        LazyColumn {
            items(menuItems) { menuItem ->
                MenuItem(menuItem = menuItem)
                if (menuItems.last() != menuItem) {
                    Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp))
                }
            }
        }

    }
}

@Composable
fun Header(navController: NavHostController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 8.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .weight(2f)
                .height(75.dp)
                .width(150.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile picture",
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .width(50.dp)
                .clip(CircleShape)
                .clickable {
                    navController.navigate(Profile.route)
                }

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSection(searchQuery: String, onValueChange: (String) -> Unit) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Text(
            text = "Little Lemon", fontSize = 48.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.Top)
            ) {
                Text(
                    text = "Chicago",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    color = Color.White
                )
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Main image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .height(200.dp)
                    .width(200.dp)
            )
        }
        TextField(
            value = searchQuery,
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(text = "Search Menu")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
fun MenuFilter(itemOnClick: (foodCategory: FilterHelper.FoodCategory) -> Unit) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = "ORDER FOR DELIVERY!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            FilterHelper.FoodCategory.values().forEach {
                Button(
                    colors = ButtonDefaults.buttonColors(Color.White),
                    onClick = {
                        itemOnClick(it)
                    },
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = it.name.lowercase(Locale.ROOT).capitalize(Locale.ROOT),
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Divider(modifier = Modifier.padding(horizontal = 8.dp))
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(menuItem: MenuItemEntity) {
    Column(modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp)) {
        Text(text = menuItem.title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(120.dp)
            ) {
                Text(text = menuItem.description, fontWeight = FontWeight.Light)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$${menuItem.price}", fontWeight = FontWeight.SemiBold)
            }
            GlideImage(
                model = menuItem.image,
                contentDescription = "${menuItem.title} image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(90.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}
