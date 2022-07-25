package com.example.game2048kt.roomDataBase

import androidx.room.*

// 定義資料操作方式
@Dao
interface RankDataUao {

    @Query("SELECT * FROM rank")
    fun getAll(): List<RankData>

    @Insert
    fun insert(item: RankData)

    @Update
    fun update(item: RankData)

    @Delete
    fun delete(item: RankData)
}