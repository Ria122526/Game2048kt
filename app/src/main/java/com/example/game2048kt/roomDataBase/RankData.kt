package com.example.game2048kt.roomDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.game2048kt.TheModeEnum

@Entity(tableName = "Rank")
class RankData(
    @PrimaryKey val id: String = "NoName",
    @ColumnInfo(name = "THREE_THREE") val score3: Int = 0,
    @ColumnInfo(name = "FOUR_FOUR") val score4: Int = 0,
    @ColumnInfo(name = "FIVE_FIVE") val score5: Int = 0,
    @ColumnInfo(name = "SIX_SIX") val score6: Int = 0,
    @ColumnInfo(name = "EIGHT_EIGHT") val score8: Int = 0
)

fun chooseRankData(mode: TheModeEnum?, id: String, score: Int): RankData {
    return when (mode) {
        TheModeEnum.THREE_THREE -> RankData(id, score3 = score)
        TheModeEnum.FOUR_FOUR -> RankData(id, score4 = score)
        TheModeEnum.FIVE_FIVE -> RankData(id, score5 = score)
        TheModeEnum.SIX_SIX -> RankData(id, score6 = score)
        TheModeEnum.EIGHT_EIGHT -> RankData(id, score8 = score)
        else -> RankData("NoName", score)
    }
}
