package com.machado.courseracapstone

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class MenuItemEntity(
    val category: String,
    val description: String,
    @PrimaryKey val id: Int,
    val image: String,
    val price: String,
    val title: String
)

@Dao
interface MenuItemDao {
    @Insert
    fun insertAll(vararg menuItems: MenuItemEntity)

    @Query("SELECT * FROM MenuItemEntity")
    fun getAll(): LiveData<List<MenuItemEntity>>

    @Query("SELECT (SELECT COUNT(*) FROM MenuItemEntity) == 0")
    fun isEmpty(): Boolean
}

@Database(entities = [MenuItemEntity::class], version = 1)
abstract class MenuItemDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}