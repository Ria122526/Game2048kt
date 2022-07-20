package com.example.game2048kt.game

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.game2048kt.R
import com.example.game2048kt.TheModeEnum
import com.example.game2048kt.TheModeEnum.Companion.getEnum
import com.example.game2048kt.game.GameSizeMoveData.gameSize
import com.example.game2048kt.roomDataBase.RankData
import com.example.game2048kt.roomDataBase.RankDataBase
import com.example.game2048kt.tools.ConvertToPixel
import java.util.*
import kotlin.math.abs

private const val MAR = 10
private const val THREE = 3
private const val FOUR = 4
private const val FIVE = 5
private const val SIX = 6
private const val EIGHT = 8

class GameActivity : AppCompatActivity(), View.OnTouchListener {

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

    // 與手勢相關，mPos起始位置、gesture手勢結果
    private var mPosX: Float = 0f
    private var mPosY: Float = 0f
    private var gestureX: Float = 0f
    private var gestureY: Float = 0f

    // todo 計分(全域?)
    private var moveScore = 0

    private var cardSize = 0f
    private var textSize = 0f

    private lateinit var newCardAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        modeSetting()
        arraySizeSetting()
        initView()
        initClicks()
        gameCardsViewAdding()
        updateGameViews()

        saveRankData()
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
            restart()
        })
        ivUndo.setOnClickListener(View.OnClickListener {

        })
        ivShare.setOnClickListener(View.OnClickListener {
            share()
        })
    }

    // 重新刷新所有資料
    private fun restart() {
        // 將所有資料全部歸零
        gameSaveData.coorsArr = Array(gameSize) { Array(gameSize) { 0 } }
        // 分數歸零
        gameSaveData.score = 0
        // 顯示的分數歸零
        tvScore.text = "${gameSaveData.score}"
        // 顯示的重新按鈕變回原本的樣式
        ivRestart.setImageResource(R.drawable.ic_baseline_replay_24)
    }

    // 分享文本至其他應用程式
    private fun share() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT,"喜歡這個遊戲嗎? https://沒有網址")
        shareIntent.type = "text/plain"
        startActivity(shareIntent)
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
        gameSize = size
        this.cardSize = cardSize
        this.textSize = textSize
    }

    // 一些會用到的陣列給予大小
    private fun arraySizeSetting() {
        gameSaveData.coorsArr = Array(gameSize) { Array(gameSize) { 0 } }
        gameSaveData.moveArr = Array(gameSize) { Array(gameSize) { 0 } }
        lastStep = Array(gameSize) { Array(gameSize) { 0 } }
        cardBg = Array(gameSize) { Array(gameSize) { TextView(this) } }
    }

    // 動態生成遊戲畫面每一格的卡片
    private fun gameCardsViewAdding() {

        val gameGridLayout: GridLayout = findViewById(R.id.game_gl)

        for (i in 0 until gameSize) {
            for (j in 0 until gameSize) {

                // 使用GridView LayoutParams 來設定尺寸與格數
                val gridLayoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(j, GridLayout.CENTER),
                    GridLayout.spec(i, GridLayout.CENTER)
                )

                cardBg[i][j].setBackgroundResource(R.drawable.game_card_bg)
                cardBg[i][j].gravity = Gravity.CENTER
                cardBg[i][j].textSize = textSize

                gridLayoutParams.height =
                    (ConvertToPixel.convertDpToPixel(cardSize, this@GameActivity)).toInt()
                gridLayoutParams.width =
                    ConvertToPixel.convertDpToPixel(cardSize, this@GameActivity).toInt()

                println(gridLayoutParams.height)
                println(gridLayoutParams.width)

                gridLayoutParams.setMargins(MAR, MAR, MAR, MAR)

                gameGridLayout.addView(cardBg[i][j], gridLayoutParams)
            }
        }
    }

    // 從資料中取得每一格的數字，賦予顏色、數字後，刷新遊戲格子的畫面
    private fun updateGameViews() {
        for (i in 0 until gameSize) {
            for (j in 0 until gameSize) {

                val tvCardBg = cardBg[i][j]
                // 從資料中獲取該格子為甚麼資料並設定文字
                tvCardBg.text = (gameSaveData.coorsArr[i][j]).toString()

                when (gameSaveData.coorsArr[i][j]) {
                    2 -> tvCardBg.setBackgroundResource(R.color.cards_number2_EFE5DB)
                    4 -> tvCardBg.setBackgroundResource(R.color.cards_number4_EDE1C9)
                    8 -> tvCardBg.setBackgroundResource(R.color.cards_number8_F4B17A)
                    16 -> tvCardBg.setBackgroundResource(R.color.cards_number16_F69463)
                    32 -> tvCardBg.setBackgroundResource(R.color.cards_number32_F57C5F)
                    64 -> tvCardBg.setBackgroundResource(R.color.cards_number64_F75E3E)
                    128 -> tvCardBg.setBackgroundResource(R.color.cards_number128_EECF72)
                    258 -> tvCardBg.setBackgroundResource(R.color.cards_number256_ECC850)
                    0 -> {
                        tvCardBg.text = ""
                        tvCardBg.setBackgroundResource(R.color.cards_empty_D6CDC4)
                    }

                    else -> tvCardBg.setBackgroundResource(R.color.cards_numberDefault_EDC53F)
                }
            }
        }
    }


    override fun onPause() {
        super.onPause()
    }

    private fun saveRankData() {
        // 建立資料庫物件
        val dataBase =
            Room.databaseBuilder(applicationContext, RankDataBase::class.java, "Rank").build()

        // 建立DAO
        val rankDao = dataBase.dataDao()
        Thread {
            var inputId = "HAPPY666"
            var isFinding = false
            for (i in rankDao.getAll()) {
                if (i.id == inputId) {
                    isFinding = true
                    break
                }
            }

            if (isFinding) {
                rankDao.update(RankData("HAPPY666", 88888))
            } else {
                rankDao.insert(RankData("HAPPY666", 666))
            }

        }.start()
    }

    private fun writeData() {}

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            // 偵測第一個觸發點按下
            MotionEvent.ACTION_DOWN -> {
                gestureX = 0f
                gestureY = 0f

                mPosX = event.x
                mPosY = event.y

            }

            MotionEvent.ACTION_MOVE -> {
                if (abs(gestureX) != 0f || abs(gestureY) != 0f) return false

                saveLastStep()
                // 判斷手勢方向 & 資料移動的方法

                // 刷新分數的方法
            }
        }

        return true
    }

    // 移動前需要暫存，保存作為上一步
    private fun saveLastStep() {

        // 取得原始分數
        moveScore = gameSaveData.score

        for (i in 0 until (gameSaveData.moveArr.size)) {
            val coors = gameSaveData.coorsArr[i]
            gameSaveData.moveArr[i].copyOf(coors.size)
        }
    }

    private fun gestureToWhere(event: MotionEvent) {

    }
}