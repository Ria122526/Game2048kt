package com.example.game2048kt.roomDataBase

import androidx.room.Database

import androidx.room.RoomDatabase

//定義這個 database 內含有什麼 table
@Database(entities = [Rank::class], version = 1)
abstract class DataBase : RoomDatabase() {
     abstract fun dataDao(): DataUao
}