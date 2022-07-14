package com.example.game2048kt.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.widget.TextView
import com.example.game2048kt.R
import com.example.game2048kt.TheModeEnum
import com.example.game2048kt.TheModeEnum.Companion.getEnum
import com.example.game2048kt.rank.RankIdScoreData

class GameActivity : AppCompatActivity() {

//    Array(0) { Array(0) { 0 } }

    val MAR = 10
    val THREE = 3
    val FOUR = 4
    val FIVE = 5
    val SIX = 5
    val EIGHT = 5

    // 常用的Enum
    private var mode = TheModeEnum

    // 遊戲畫面每一格要生成的View
    private var cardBg = emptyArray<TextView?>()

    // 儲存的資料的類別
    private var gameSaveData = GameSaveData()

    // 不儲存的資料類別
    private var gameData = GameData()

    // 遊戲結束時彈出的對話框資料類別
    private val rankIdSaveData = RankIdScoreData();

    // 與手勢相關，mPos起始位置、gesture手勢結果
    private var mPosX: Float = 0f
    private var mPosY: Float = 0f
    private var gestureX: Float = 0f
    private var gestureY: Float = 0f

    // todo 計分(全域?)
    private var moveGrade = 0

    private var cardSize = 0
    private var textSize = 0

    private var newCard: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        println("GAME: " + intent.getStringExtra("mode"))
//        println(TheModeEnum.THREE_THREE)

//        modeSetting()

    }

    // 接收外面傳入的模式、設定長度
    private fun modeSetting() {
//        val intentMode = intent.getStringExtra("mode")
//        mode = getEnum(intentMode)
//
//        // todo Auto Fix Size
//        when (mode) {
//            TheModeEnum.THREE_THREE -> sizeSetting(THREE, 113, 46)
//            TheModeEnum.FOUR_FOUR -> sizeSetting(FOUR, 83, 34)
//            TheModeEnum.FIVE_FIVE -> sizeSetting(FIVE, 65, 30)
//            TheModeEnum.SIX_SIX -> sizeSetting(SIX, 53, 24)
//            TheModeEnum.EIGHT_EIGHT -> sizeSetting(EIGHT, 38, 20)
//        }
    }

    // 接收到模式後依照模式設定對應的大小
    private fun sizeSetting(size: Int, cardSize: Int, textSize: Int) {
        gameData.size = size
        this.cardSize = cardSize
        this.textSize = textSize
    }


}