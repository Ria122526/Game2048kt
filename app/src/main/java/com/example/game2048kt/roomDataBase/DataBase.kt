package com.example.game2048kt.roomDataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Rank::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun userDao(): DataUao
}