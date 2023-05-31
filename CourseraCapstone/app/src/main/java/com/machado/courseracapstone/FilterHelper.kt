package com.machado.courseracapstone

object FilterHelper {

    fun filterBy(
        foodCategory: FoodCategory,
        menuItems: List<MenuItemEntity>
    ): List<MenuItemEntity> {
        return when (foodCategory) {
            FoodCategory.STARTERS -> menuItems.filter { it.category.contains("starters", ignoreCase = true)  }
            FoodCategory.MAINS -> menuItems.filter { it.category.contains("mains", ignoreCase = true) }
            FoodCategory.DESSERTS -> menuItems.filter { it.category.contains("desserts", ignoreCase = true) }
            FoodCategory.DRINKS -> menuItems.filter { it.category.contains("drinks", ignoreCase = true) }
        }
    }

    enum class FoodCategory {
        STARTERS,
        MAINS,
        DESSERTS,
        DRINKS
    }
}