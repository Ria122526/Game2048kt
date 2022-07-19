package com.example.game2048kt.roomDataBase

import androidx.room.*

// 定義資料操作方式
@Dao
interface RankDataUao {
    // 取得所有Rank資料紀錄，依照分數作排序
    @Query("SELECT * FROM rank order by score desc")
    fun getAll(): List<RankData>

    @Query("SELECT * FROM rank WHERE id = 'Me'")
    fun getMe(): List<RankData>

    @Insert
    fun insert(item: RankData)

    @Update
    fun update(item: RankData)

    @Delete
    fun delete(item: RankData)
}