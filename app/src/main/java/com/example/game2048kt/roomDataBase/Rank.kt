package com.example.game2048kt.roomDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rank")
data class Rank(
    @PrimaryKey val id: String = "NoName",
    @ColumnInfo(name = "score") val score: Int = 0
)