package com.example.game2048kt.roomDataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rank")
data class RankData(
    @PrimaryKey val id: String = "NoName",
//    @ColumnInfo(name = "THREE_THREE") val score3: String = "0",
//    @ColumnInfo(name = "FOUR_FOUR") val score4: String = "0",
//    @ColumnInfo(name = "FIVE_FIVE") val score5: String = "0",
//    @ColumnInfo(name = "SIX_SIX") val score6: String = "0",
//    @ColumnInfo(name = "EIGHT_EIGHT") val score8: String = "0"
)

//fun createRankData(mode: TheModeEnum?, id: String, score: String): RankData {
//    return when (mode) {
//        TheModeEnum.THREE_THREE -> RankData(id, score3 = score)
//        TheModeEnum.FOUR_FOUR -> RankData(id, score4 = score)
//        TheModeEnum.FIVE_FIVE -> RankData(id, score5 = score)
//        TheModeEnum.SIX_SIX -> RankData(id, score6 = score)
//        TheModeEnum.EIGHT_EIGHT -> RankData(id, score8 = score)
//        else -> RankData("NoName", score)
//    }
//}