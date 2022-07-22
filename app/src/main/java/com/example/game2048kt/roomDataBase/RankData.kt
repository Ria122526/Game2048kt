package com.example.game2048kt.roomDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "THREE_THREE")
data class RankData3x3(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "score") val score: Int = 0,
)

@Entity(tableName = "FOUR_FOUR")
data class RankData4x4(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "score") val score: Int = 0,
)

@Entity(tableName = "FIVE_FIVE")
data class RankData5x5(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "score") val score: Int = 0,
)

@Entity(tableName = "SIX_SIX")
data class RankData6x6(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "score") val score: Int = 0,
)

@Entity(tableName = "EIGHT_EIGHT")
data class RankData8x8(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "score") val score: Int = 0,
)