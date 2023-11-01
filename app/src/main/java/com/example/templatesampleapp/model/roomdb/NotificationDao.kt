package com.example.templatesampleapp.model.roomdb

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase

/**
 * @author Umer Bilal
 * Created 10/28/2023 at 8:12 PM
 */
@Dao
interface NotificationDao {

    @Query("SELECT * FROM NotificationRoomDbModel")
    fun getAll(): List<NotificationRoomDbModel>

    @Insert
    fun insertAll(vararg users: NotificationRoomDbModel)
}


@Database(entities = [NotificationRoomDbModel::class], version = 1)
abstract class NotificationModelDatabase : RoomDatabase() {
    abstract fun userDao(): NotificationDao
}