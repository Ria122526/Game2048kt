package com.example.game2048kt.roomDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rank")
data class RankData(
    @PrimaryKey val id: String = "NoName",
    @ColumnInfo(name = "THREE_THREE") val score3: Int = 0,
    @ColumnInfo(name = "FOUR_FOUR") val score4: Int = 0,
    @ColumnInfo(name = "FIVE_FIVE") val score5: Int = 0,
    @ColumnInfo(name = "SIX_SIX") val score6: Int = 0,
    @ColumnInfo(name = "EIGHT_EIGHT") val score8: Int = 0,
)

fun createRankData(mode: Int, id: String, score: Int): RankData {
    return when (mode) {
        3 -> RankData(id, score3 = score)
        4 -> RankData(id, score4 = score)
        5 -> RankData(id, score5 = score)
        6 -> RankData(id, score6 = score)
        8 -> RankData(id, score8 = score)
        else -> RankData("NoName", score)
    }
}