package com.example.game2048kt.roomDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.game2048kt.rank.RankIdScoreData

@Entity(tableName = "Rank")
class Rank {
    @PrimaryKey val id: String? = null
    @ColumnInfo(name = "score") val score: Int = 0

}