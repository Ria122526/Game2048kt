package com.example.game2048kt.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Database
import androidx.room.Room
import com.example.game2048kt.R
import com.example.game2048kt.TheModeEnum
import com.example.game2048kt.TheModeEnum.Companion.getEnum
import com.example.game2048kt.rank.RankIdScoreData
import com.example.game2048kt.roomDataBase.DataBase

private const val MAR = 10
private const val THREE = 3
private const val FOUR = 4
private const val FIVE = 5
private const val SIX = 6
private const val EIGHT = 8

class GameActivity : AppCompatActivity() {

    private lateinit var tvScore: TextView
    private lateinit var tvHighScore: TextView
    private lateinit var ivShare: ImageView
    private lateinit var ivUndo: ImageView
    private lateinit var ivRestart: ImageView

    // 常用的Enum
    var mode = getEnum("")

    // 遊戲畫面每一格要生成的View
    private lateinit var cardBg: Array<Array<TextView>>

    // 上一步資料
    private lateinit var lastStep: Array<Array<Int>>

    // 儲存的資料
    private var gameSaveData = GameSaveData()

    // 不儲存的資料
    private var gameData = GameData()

    // 遊戲結束時彈出對話框的資料
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

    private lateinit var newCard: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        modeSetting()
        arraySizeSetting()
        initView()
        initClicks()

        val db = Room.databaseBuilder(
            applicationContext,
            DataBase::class.java, "database-name"
        ).build()

        db.userDao().

    }

    // 載入view
    private fun initView() {
        tvScore = findViewById(R.id.game_tv_score)
        tvHighScore = findViewById(R.id.game_tv_high_score)
        ivShare = findViewById(R.id.game_iv_share)
        ivUndo = findViewById(R.id.game_iv_undo)
        ivRestart = findViewById(R.id.game_iv_restart)
    }

    private fun initClicks() {
        ivRestart.setOnClickListener(View.OnClickListener {

        })
        ivUndo.setOnClickListener(View.OnClickListener {

        })
        ivShare.setOnClickListener(View.OnClickListener {

        })
    }

    // 接收外面傳入的模式、設定長度
    private fun modeSetting() {

        val intentMode = intent.getStringExtra("mode")

        mode = if (intentMode == null) getEnum("3x3") else getEnum(intentMode)

        // todo Auto Fix Size
        when (mode) {
            TheModeEnum.THREE_THREE -> viewSizeSetting(THREE, 113, 46)
            TheModeEnum.FOUR_FOUR -> viewSizeSetting(FOUR, 83, 34)
            TheModeEnum.FIVE_FIVE -> viewSizeSetting(FIVE, 65, 30)
            TheModeEnum.SIX_SIX -> viewSizeSetting(SIX, 53, 24)
            TheModeEnum.EIGHT_EIGHT -> viewSizeSetting(EIGHT, 38, 20)
        }
    }

    // 接收到模式後依照模式設定對應的大小
    private fun viewSizeSetting(size: Int, cardSize: Int, textSize: Int) {
        gameData.size = size
        this.cardSize = cardSize
        this.textSize = textSize
    }

    private fun arraySizeSetting() {
        gameSaveData.coorsArr = Array(gameData.size) { Array(gameData.size) { 0 } }
        gameSaveData.moveArr = Array(gameData.size) { Array(gameData.size) { 0 } }
        lastStep = Array(gameData.size) { Array(gameData.size) { 0 } }
        cardBg = Array(gameData.size) { Array(gameData.size) { TextView(this) } }
    }

    fun saveData() {

    }

    override fun onPause() {
        super.onPause()
    }


}