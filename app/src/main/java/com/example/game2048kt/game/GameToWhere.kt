package com.example.game2048kt.game

import com.example.game2048kt.game.GameSizeMoveData.gameSize
import com.example.game2048kt.game.GameSizeMoveData.isMoved


private const val RIGHT = "RIGHT"
private const val DOWN = "DOWN"
private const val LEFT = "LEFT"
private const val UP = "UP"

class GameToWhere(
    gameSaveData: GameSaveData,
    coorsArr: Array<Array<Int>>
) {

    // 獲取傳入的值
    private val coorsArr = coorsArr
    private val gameSaveData = gameSaveData

    // 簡化版座標
    var intArr = IntArray(gameSize)

    // 由左至右、上至下堆疊
    private fun movedAscData() {

    }

    // 由右至左、下至上堆疊
    private fun moveDescData() {

    }

    private fun slide(way: String) {

        for (i in 0 until gameSize) {
//            updateIntArr(way, i)
//            when (way) {
//
//                RIGHT->
//                    LEFT ->
//                UP->
//                DOWN ->
//            }
        }
    }

    private fun updateIntArr(way: String, loop: Int) {

        when (way) {
            RIGHT, LEFT -> getArrayX(loop)
            UP, DOWN -> getArrayY(loop)
        }
    }

    // 取出X軸，統一使用取出的的資料
    private fun getArrayX(loop: Int) {
        var takeIntArr = IntArray(gameSize)
        var addIndex = 0

        for (i in 0 until gameSize) {
            // 遇到0不儲存
            if (coorsArr[i][loop] == 0) continue

            // 取出資料暫存
            takeIntArr[addIndex] = coorsArr[i][loop]
            // 原始資料歸零
            coorsArr[i][loop] = 0

            // 當已經發生addIndex與i遇0不儲存跳過i，代表資料曾經動過
            if (addIndex != i) isMoved = true

            addIndex++
        }

        intArr = takeIntArr
    }

    // 取出Y軸，統一使用取出的的資料
    private fun getArrayY(loop: Int) {
        var takeIntArr = IntArray(gameSize)
        var addIndex = 0

        for (i in 0 until gameSize) {
            // 遇到0不儲存
            if (coorsArr[loop][i] == 0) continue

            // 取出資料暫存
            takeIntArr[addIndex] = coorsArr[i][loop]
            // 原始資料歸零
            coorsArr[loop][i] = 0

            // 當已經發生addIndex與i遇0不儲存跳過i，代表資料曾經動過
            if (addIndex != i) isMoved = true

            addIndex++
        }

        intArr = takeIntArr
    }
}
