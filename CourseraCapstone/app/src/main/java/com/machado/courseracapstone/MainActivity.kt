package com.machado.courseracapstone

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.machado.courseracapstone.ui.theme.CourseraCapstoneTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }
    private val database by lazy {
        Room.databaseBuilder(applicationContext, MenuItemDatabase::class.java, "menu_database").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                saveMenuToDatabase(fetchMenu())
            }
        }

        setContent {
            CourseraCapstoneTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val sharedPreferences =
                    getSharedPreferences("LIL_LEMON_SHARED_PREF", Context.MODE_PRIVATE)
                val dbMenuItems =
                    database.menuItemDao().getAll().observeAsState(emptyList()).value

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationComposable(
                        navController = navController,
                        sharedPreferences = sharedPreferences,
                        menuItems = dbMenuItems
                    )
                }
            }
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        return client
            .get(BASE_URL)
            .body<MenuNetworkData>().menu ?: emptyList()
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsEntity = menuItemsNetwork.map { it.toMenuItemEntity() }
        database.menuItemDao().insertAll(*menuItemsEntity.toTypedArray())
    }
}

