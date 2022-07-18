package com.example.game2048kt.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room
import com.example.game2048kt.R
import com.example.game2048kt.TheModeEnum
import com.example.game2048kt.TheModeEnum.Companion.getEnum
import com.example.game2048kt.roomDataBase.DataBase
import com.example.game2048kt.roomDataBase.Rank
import com.example.game2048kt.tools.ConvertToDp

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
    private var mode = getEnum("")

    // 遊戲畫面每一格要生成的View
    private lateinit var cardBg: Array<Array<TextView>>

    // 上一步資料
    private lateinit var lastStep: Array<Array<Int>>

    // 儲存的資料
    private var gameSaveData = GameSaveData()

    // 不儲存的資料
    private var gameData = GameData()

    // 與手勢相關，mPos起始位置、gesture手勢結果
    private var mPosX: Float = 0f
    private var mPosY: Float = 0f
    private var gestureX: Float = 0f
    private var gestureY: Float = 0f

    // todo 計分(全域?)
    private var moveGrade = 0

    private var cardSize = 0f
    private var textSize = 0f

    private lateinit var newCard: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        modeSetting()
        arraySizeSetting()
        initView()
        initClicks()
        gameCardsViewAdding()
    }

    // 載入view
    private fun initView() {
        tvScore = findViewById(R.id.game_tv_score)
        tvHighScore = findViewById(R.id.game_tv_high_score)
        ivShare = findViewById(R.id.game_iv_share)
        ivUndo = findViewById(R.id.game_iv_undo)
        ivRestart = findViewById(R.id.game_iv_restart)
    }

    // 設定點擊監聽
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
            TheModeEnum.THREE_THREE -> viewSizeSetting(THREE, 113f, 46f)
            TheModeEnum.FOUR_FOUR -> viewSizeSetting(FOUR, 83f, 34f)
            TheModeEnum.FIVE_FIVE -> viewSizeSetting(FIVE, 65f, 30f)
            TheModeEnum.SIX_SIX -> viewSizeSetting(SIX, 53f, 24f)
            TheModeEnum.EIGHT_EIGHT -> viewSizeSetting(EIGHT, 38f, 20f)
        }
    }

    // 接收到模式後依照模式設定對應的大小
    private fun viewSizeSetting(size: Int, cardSize: Float, textSize: Float) {
        gameData.size = size
        this.cardSize = cardSize
        this.textSize = textSize
    }

    // 一些會用到的陣列給予大小
    private fun arraySizeSetting() {
        gameSaveData.coorsArr = Array(gameData.size) { Array(gameData.size) { 0 } }
        gameSaveData.moveArr = Array(gameData.size) { Array(gameData.size) { 0 } }
        lastStep = Array(gameData.size) { Array(gameData.size) { 0 } }
        cardBg = Array(gameData.size) { Array(gameData.size) { TextView(this) } }
    }

    // 動態生成遊戲畫面每一格的卡片
    private fun gameCardsViewAdding() {

        val gameGridLayout = GridLayout(this@GameActivity)

        for (i in 0 until gameData.size) {
            for (j in 0 until gameData.size) {

                // 使用GridView LayoutParams 來設定尺寸與格數
                val gridLayoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(j, GridLayout.CENTER),
                    GridLayout.spec(i, GridLayout.CENTER)
                )

                cardBg[i][j].setBackgroundResource(R.drawable.game_card_bg)
                cardBg[i][j].gravity = Gravity.CENTER
                cardBg[i][j].textSize = textSize

                gridLayoutParams.height =
                    (ConvertToDp.convertPixelToDp(cardSize, this@GameActivity)) as Int
                gridLayoutParams.width =
                    ConvertToDp.convertPixelToDp(cardSize, this@GameActivity) as Int

                gridLayoutParams.setMargins(MAR, MAR, MAR, MAR)

                gameGridLayout.addView(cardBg[i][j], gridLayoutParams)
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

//    private fun saveData() {
//        // 建立資料庫物件
//        val dataBase =
//            Room.databaseBuilder(applicationContext, DataBase::class.java, "Rank").build()
//
//        // 建立DAO
//        val rankDao = dataBase.dataDao()
//        Thread {
//            rankDao.insert(Rank("HAPPY666", 666))
//            println(rankDao.getAll()[0])
//        }.start()
//    }
}