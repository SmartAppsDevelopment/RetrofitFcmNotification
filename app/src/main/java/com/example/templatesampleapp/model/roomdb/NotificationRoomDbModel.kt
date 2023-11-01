package com.example.templatesampleapp.model.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class NotificationRoomDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val topic: String,
    val title: String,
    val body: String,
    val apiresponse: String,
    val responseCode: String
)