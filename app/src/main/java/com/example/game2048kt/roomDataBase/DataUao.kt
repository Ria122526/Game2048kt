package com.example.game2048kt.roomDataBase

import androidx.room.*

// 定義資料操作方式
@Dao
interface DataUao {
    // 取得所有Rank資料紀錄，依照分數作排序
    @Query("SELECT * FROM rank order by score desc")
    fun getAll(): List<Rank>

    @Query("SELECT * FROM rank WHERE id = 'Me'")
    fun getMe(): List<Rank>

//    @Query("SELECT * FROM rank WHERE id = sameId")
//    fun getSame(item: String): List<Rank>

    @Insert
    fun insert(item: Rank)

    @Update
    fun update(item: Rank)

    @Delete
    fun delete(item: Rank)
}