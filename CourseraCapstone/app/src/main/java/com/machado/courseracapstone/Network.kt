package com.machado.courseracapstone

import kotlinx.serialization.Serializable

@Serializable
data class MenuNetworkData(
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: String,
    val title: String
){
    fun toMenuItemEntity():MenuItemEntity{
        return MenuItemEntity(
            category = category,
            description = description,
            id = id,
            image = image,
            price = price,
            title = title
        )
    }
}

const val BASE_URL = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"