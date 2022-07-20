package com.example.game2048kt.game

import com.example.game2048kt.game.GameSizeMoveData.gameSize
import com.example.game2048kt.game.GameSizeMoveData.isMoved


class GameToWhere(
    gameSaveData: GameSaveData, coorsArr: Array<Array<Int>>
) {

    val R = "RIGHT"
    val D = "DOWN"
    val L = "LEFT"
    val U = "UP"

    var gameSaveData = gameSaveData
    var coorsArr = coorsArr

    private fun rightData(y: Int) {
        // 倒取倒放
        // 初始化資料
        val takeX = IntArray(gameSize)
        val addIndex = gameSize - 1
        var returnIndex = gameSize - 1

        // 倒取
        for (i in 0 until gameSize) {
            // 遇0跳過不取
            if (coorsArr[i][y] == 0) continue

            // 倒著取
            takeX[addIndex] = coorsArr[i][y]
            coorsArr[i][y] = 0

            // 當不同步時表示可以且有發生移動
            if (addIndex != i) isMoved = true
        }

        // 檢查是否需要合併
        for (i in (gameSize - 1) downTo 0) {
            if (returnIndex < 0 || takeX[returnIndex] == 0) break
            else if (returnIndex == 0 || takeX[returnIndex] != takeX[returnIndex - 1]) {
                coorsArr[i][y] = takeX[returnIndex]
            } else {
                isMoved = true
                coorsArr[i][y] = takeX[returnIndex] + takeX[returnIndex - 1]
                gameSaveData.score = gameSaveData.score + coorsArr[i][y]
                returnIndex--
            }
            returnIndex--
        }
    }

    private fun leftData(y: Int) {
        val takeX = IntArray(gameSize)
        var addIndex = 0
        var returnIndex = 0

        // 正取正放
        for (i in 0 until gameSize) {
            if (coorsArr[i][y] == 0) continue

            takeX[addIndex] = coorsArr[i][y]
            coorsArr[i][y] = 0

            if (addIndex != i) isMoved = true

            addIndex++
        }

        for (i in 0 until gameSize) {
            if (returnIndex >= gameSize || takeX[returnIndex] == 0) break
            else if (returnIndex + 1 < gameSize && takeX[returnIndex] == takeX[returnIndex + 1]) {
                isMoved = true
                coorsArr[i][y] = takeX[returnIndex] + takeX[returnIndex + 1]
                gameSaveData.score = gameSaveData.score + coorsArr[i][y]
                returnIndex++
            } else {
                coorsArr[i][y] = takeX[returnIndex]
            }
            returnIndex++
        }
    }

    private fun downData(x: Int) {
        val takeY = IntArray(gameSize)
        var addIndex = gameSize - 1
        var returnIndex = gameSize - 1

        for (i in 0 until gameSize) {
            if (coorsArr[x][i] == 0) continue

            takeY[addIndex] = coorsArr[x][i]
            coorsArr[x][i] = 0

            if (addIndex != i) isMoved = true

            addIndex--
        }

        for (i in (gameSize - 1) downTo 0) {

            if (returnIndex < 0 || takeY[returnIndex] == 0) break
            else if (returnIndex == 0 || takeY[returnIndex] != takeY[returnIndex - 1]) {
                coorsArr[x][i] = takeY[returnIndex]
            } else {
                isMoved = true
                coorsArr[x][i] = takeY[returnIndex] + takeY[returnIndex - 1]
                gameSaveData.score = gameSaveData.score + coorsArr[x][i]
                returnIndex--
            }
            returnIndex--
        }
    }

    private fun upData(x: Int) {
        val takeX = IntArray(gameSize)
        var addIndex = 0
        var returnIndex = 0

        // 正取正放
        for (i in 0 until gameSize) {
            if (coorsArr[x][i] == 0) continue

            takeX[addIndex] = coorsArr[x][i]
            coorsArr[x][i] = 0

            if (addIndex != i) isMoved = true

            addIndex++
        }

        for (i in 0 until gameSize) {
            if (returnIndex >= gameSize || takeX[returnIndex] == 0) break
            else if (returnIndex + 1 < gameSize && takeX[returnIndex] == takeX[returnIndex + 1]) {
                isMoved = true
                coorsArr[x][i] = takeX[returnIndex] + takeX[returnIndex + 1]
                gameSaveData.score = gameSaveData.score + coorsArr[x][i]
                returnIndex++
            } else {
                coorsArr[x][i] = takeX[returnIndex]
            }
            returnIndex++
        }
    }

    fun slide(way: String) {
        when (way) {
            R -> {
                for (i in 0 until gameSize) {
                    rightData(i)
                }
            }
            L -> for (i in 0 until gameSize) leftData(i)
            D -> {
                for (i in 0 until gameSize) {
                    downData(i)
                }
            }
            U -> for (i in 0 until gameSize) upData(i)
        }
    }
}
