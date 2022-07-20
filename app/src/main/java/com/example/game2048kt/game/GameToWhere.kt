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

    // 取得去零後的的陣列
    var takeIntArr = IntArray(gameSize)

    // 由右至左、下至上合併
    private fun moveAscData(isX: Boolean, loop: Int) {
        var returnIndex = 0

        if (isX) {
            for (i in 0 until gameSize) {
                if (returnIndex >= gameSize || takeIntArr[returnIndex] == 0) break
                else if (returnIndex + 1 < gameSize && takeIntArr[returnIndex] == takeIntArr[returnIndex + 1]) {
                    isMoved = true
                    coorsArr[i][loop] = takeIntArr[returnIndex] + takeIntArr[returnIndex + 1]
                    returnIndex++
                }
            }
        } else {

        }


    }

    // 由左至右、上至下合併
    private fun movedDescData() {

    }


    private fun take(way: String) {

        when (way) {
            RIGHT, LEFT -> {
                for (loop in 0 until gameSize) {
                    getArray(true, loop)
                }

                // 堆疊回去
                if (way == RIGHT) {

                } else {

                }
            }

            UP, DOWN -> {
                for (loop in 0 until gameSize) {
                    getArray(false, loop)
                }
            }
        }
    }

    // 取出資料
    private fun getArray(isX: Boolean, loop: Int) {
        var addIndex = 0

        for (i in 0 until gameSize) {
            if (isX) {
                // 遇到0不儲存
                if (coorsArr[i][loop] == 0) continue

                // 取出資料暫存
                takeIntArr[addIndex] = coorsArr[i][loop]

                // 原始資料歸零
                coorsArr[i][loop] = 0
            } else {
                // 遇到0不儲存
                if (coorsArr[loop][i] == 0) continue

                // 取出資料暫存
                takeIntArr[addIndex] = coorsArr[i][loop]

                // 原始資料歸零
                coorsArr[loop][i] = 0
            }

            // 當已經發生addIndex與i遇0不儲存跳過i，代表資料曾經動過
            if (addIndex != i) GameSizeMoveData.isMoved = true

            addIndex++
        }
    }
}
