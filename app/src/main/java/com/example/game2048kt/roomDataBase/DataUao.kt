package com.example.game2048kt.roomDataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataUao {
    @Query("SELECT * FROM rank")
    fun getAll(): List<Rank>

    @Query("SELECT * FROM rank WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Rank>

    @Query("SELECT * FROM rank WHERE score LIKE :first AND score LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Rank

    @Insert
    fun insertAll(vararg users: Rank)

    @Delete
    fun delete(user: Rank)
}