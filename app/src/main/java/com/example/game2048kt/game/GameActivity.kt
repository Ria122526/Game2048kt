package com.example.game2048kt.game

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.room.Room
import com.example.game2048kt.GameEndDialogSendId
import com.example.game2048kt.R
import com.example.game2048kt.TheModeEnum
import com.example.game2048kt.TheModeEnum.Companion.getEnum
import com.example.game2048kt.game.GameSizeMoveData.gameSize
import com.example.game2048kt.game.GameSizeMoveData.isMoved
import com.example.game2048kt.roomDataBase.RankDataBase
import com.example.game2048kt.roomDataBase.RankDataUao
import com.example.game2048kt.roomDataBase.chooseRankData
import com.example.game2048kt.tools.ConvertToPixel
import com.google.gson.Gson
import java.util.*
import kotlin.math.abs

private const val MAR = 10
private const val THREE = 3
private const val FOUR = 4
private const val FIVE = 5
private const val SIX = 6
private const val EIGHT = 8

class GameActivity : AppCompatActivity(), View.OnTouchListener {

    private lateinit var clGame: ConstraintLayout
    private lateinit var tvScore: TextView
    private lateinit var tvHighScore: TextView
    private lateinit var tvEnd: TextView
    private lateinit var ivShare: ImageView
    private lateinit var ivUndo: ImageView
    private lateinit var ivRestart: ImageView

    // 常用的Enum
    private var mode = getEnum("")

    // 移動時用的方法
    private lateinit var to: GameToWhere

    // 遊戲畫面每一格要生成的View
    private lateinit var cardBg: Array<Array<TextView>>

    // 上一步資料
    private lateinit var lastStep: Array<Array<Int>>

    // 儲存的資料
    private var gameSaveData = GameSaveData()

    // 建立排行榜資料庫物件
    private var dataBase: RankDataBase? = null

    // 排行榜資料庫DAO
    private var rankDao: RankDataUao? = null

    // 與手勢相關，mPos起始位置、gesture手勢結果
    private var mPosX: Float = 0f
    private var mPosY: Float = 0f
    private var gestureX: Float = 0f
    private var gestureY: Float = 0f

    // 移動前保存的分術
    private var lastMoveScore = 0
    private var lastMoveHighScore = 0

    private var cardSize = 0f
    private var textSize = 0f

    // 座標位置
    private var randX: Int = 0
    private var randY: Int = 0

    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        dataBase =
            Room.databaseBuilder(applicationContext, RankDataBase::class.java, "Rank").build()
        rankDao = dataBase?.dataDao()

        modeSetting()
        arraySizeSetting()
        initView()
        initClicks()
        gameCardsViewAdding()

//        restart()
        getSaveGameBack()
    }

    // 載入view
    private fun initView() {
        clGame = findViewById(R.id.game_cl)
        tvScore = findViewById(R.id.game_tv_score)
        tvHighScore = findViewById(R.id.game_tv_high_score)
        ivShare = findViewById(R.id.game_iv_share)
        ivUndo = findViewById(R.id.game_iv_undo)
        ivRestart = findViewById(R.id.game_iv_restart)

        tvEnd = findViewById(R.id.game_end)

        clGame.setOnTouchListener(this)
    }

    // 設定點擊監聽
    private fun initClicks() {
        ivRestart.setOnClickListener(View.OnClickListener {
            restart()
        })
        ivUndo.setOnClickListener(View.OnClickListener {
            undo()
            updateGameViews()
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
        ivRestart.setBackgroundResource(R.drawable.ib_inform_cards)

        // 恢復可動
        ivUndo.isEnabled = true
        clGame.isEnabled = true

        // 遮片隱藏
        tvEnd.visibility = View.INVISIBLE
        gameSaveData.endGameTvVisibility = View.INVISIBLE

        randAddNewNumber()
        randAddNewNumber()
        updateGameViews()
    }

    // 重回上一步
    private fun undo() {

        // lastStep代表上次儲存的陣列
        for (i in 0 until gameSize) {
            gameSaveData.coorsArr[i] = lastStep[i].copyOf()
        }

        gameSaveData.score = lastMoveScore
        gameSaveData.highScore = lastMoveHighScore

        tvScore.text = "${gameSaveData.score}"
        tvHighScore.text = "${gameSaveData.highScore}"
    }

    // 分享文本至其他應用程式
    private fun share() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, "喜歡這個遊戲嗎? https://沒有網址")
        shareIntent.type = "text/plain"
        startActivity(shareIntent)
    }

    // 隨機找到可生成的位置並給予數字2、4
    private fun randAddNewNumber() {

        // 當其中有0的空格就可以生成
        while (checkZeroLocation()) {
            // 隨機生成座標
            randX = (Math.random() * gameSize).toInt()
            randY = (Math.random() * gameSize).toInt()

            if (gameSaveData.coorsArr[randX][randY] == 0) break
        }

        // 設定生成2、4出現的機率
        val randomWeight = (Math.random() * 100).toInt()
        if (randomWeight < 95) gameSaveData.coorsArr[randX][randY] = 2
        else gameSaveData.coorsArr[randX][randY] = 2

//        // 禁止上一步
//        ivUndo.isEnabled = false
    }

    // 每次生成的動畫
    private fun addNewNumberAnimations() {
        cardBg[randX][randY].startAnimation(
            loadAnimation(this, R.anim.card_out)
        )
    }

    // 尋找是否有為0的數字
    private fun checkZeroLocation(): Boolean {

        for (i in 0 until gameSize) {
            for (j in 0 until gameSize) {
                if (gameSaveData.coorsArr[i][j] == 0) {
                    return true
                }
            }
        }
        return false
    }

    // 從資料中取得每一格的數字，賦予顏色、數字後，刷新遊戲格子的畫面
    private fun updateGameViews() {
        for (i in 0 until gameSize) {
            for (j in 0 until gameSize) {

                val tvCardBg = cardBg[i][j]
                tvCardBg.text = "${gameSaveData.coorsArr[i][j]}"

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
                cardBg[i][j].setTextColor(applicationContext.resources.getColor(R.color.title_tx_787469))

                gridLayoutParams.height =
                    (ConvertToPixel.convertDpToPixel(cardSize, this@GameActivity)).toInt()
                gridLayoutParams.width =
                    ConvertToPixel.convertDpToPixel(cardSize, this@GameActivity).toInt()

                gridLayoutParams.setMargins(MAR, MAR, MAR, MAR)

                gameGridLayout.addView(cardBg[i][j], gridLayoutParams)
            }
        }
    }

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
                gestureToWhere(event)

                // 如果手勢後發生了移動
                if (isMoved) {

                    for (i in 0 until gameSize) {
                        lastStep[i] = gameSaveData.moveArr[i].copyOf()
                    }

                    isMoved = false

                    updateScore()
                    randAddNewNumber()
                    addNewNumberAnimations()
                    updateGameViews()
                }

                // 如果滿格且沒有辦法發生合併(遊戲結束)
                if (!checkZeroLocation() && checkGameOver()) {
                    endGameSetting()
                }
            }
        }
        return true
    }

    // 移動前需要暫存，保存上一步
    private fun saveLastStep() {

//        // 保存後即可以做上一步
//        ivUndo.isEnabled = true

        // 取得移動前的分數與高分
        lastMoveScore = gameSaveData.score
        lastMoveHighScore = gameSaveData.highScore

        // 取得移動前的遊戲資料
        for (i in 0 until gameSize) {
            gameSaveData.moveArr[i] = gameSaveData.coorsArr[i].copyOf()
        }
    }

    // 判斷手勢方向 & 資料移動
    private fun gestureToWhere(event: MotionEvent) {
        to = GameToWhere(gameSaveData, gameSaveData.coorsArr)

        gestureX = event.x - mPosX
        gestureY = event.y - mPosY

        if (abs(gestureX) > abs(gestureY)) {
            if (gestureX > 5) {
                to.slide(to.R)
            } else if (gestureX < -5) {
                to.slide(to.L)
            }
        } else if (abs(gestureX) < abs(gestureY)) {
            if (gestureY > 5) {
                to.slide(to.D)
            } else if (gestureY < -5) {
                to.slide(to.U)
            }
        }
    }

    // 刷新分數
    private fun updateScore() {
        // 判斷是否為最高分
        if (gameSaveData.score > gameSaveData.highScore) gameSaveData.highScore = gameSaveData.score

        // 其餘的更新分數
        tvScore.text = "${gameSaveData.score}"
        tvHighScore.text = "${gameSaveData.highScore}"
    }

    // 判斷遊戲是否結束
    private fun checkGameOver(): Boolean {

        // 先模擬合併，一旦有機會合併，回傳false
        for (j in 0 until gameSize) {
            for (i in 0 until gameSize) {
                if (i + 1 != gameSize && gameSaveData.coorsArr[i][j] == gameSaveData.coorsArr[i + 1][j]) {
                    return false;
                } else if (j + 1 != gameSize && gameSaveData.coorsArr[i][j] == gameSaveData.coorsArr[i][j + 1]) {
                    return false;
                }
            }
        }
        return true
    }

    // 遊戲結束時的設定
    private fun endGameSetting() {
        ivRestart.setBackgroundResource(R.drawable.ib_inform_end_cards)
        ivUndo.isEnabled = false
        clGame.isEnabled = false

        // todo 遊戲結束時的遮片
        tvEnd.visibility = View.VISIBLE
        gameSaveData.endGameTvVisibility = View.VISIBLE

        gameEndRecord()
    }

    // 遊戲結束時彈出輸入ID的視窗
    private fun gameEndRecord() {
        GameEndDialog(this@GameActivity).also {
            it.show()
            it.sendId = object : GameEndDialogSendId {
                override fun send(id: String) {
                    saveRankData(id, gameSaveData.score)
                    it.cancel()
                }
            }
        }
    }

    // 儲存成績資料
    private fun saveRankData(inputId: String, inputScore: Int) {
        Thread {

            var isFinding = false
            for (i in rankDao!!.getAll()) {
                if (i.id == inputId) {
                    isFinding = true
                    break
                }
            }

            if (isFinding) {
                rankDao?.update(chooseRankData(mode, inputId, inputScore))
            } else {
                rankDao?.insert(chooseRankData(mode, inputId, inputScore))
            }
        }.start()
    }

    override fun onPause() {
        saveGameData()
        super.onPause()
    }

    // 使用簡單的sharePreference紀錄遊戲畫面
    private fun saveGameData() {
        val sharedPreferences = getSharedPreferences("2048", MODE_PRIVATE)
        val saveString = gson.toJson(gameSaveData)

        sharedPreferences.edit().putString(mode?.key, saveString).apply()
    }

    // 從sharePreference取回遊戲畫面
    private fun getSaveGameBack() {
        val sharedPreferences = getSharedPreferences("2048", MODE_PRIVATE)
        val returnString = sharedPreferences.getString(mode?.key, "0")

        if (returnString.equals("0")) {
            restart()
        } else {
            gameSaveData = gson.fromJson(returnString, GameSaveData::class.java)
            updateGameViews()
        }

        // 復原分數
        tvScore.text = gameSaveData.score.toString()
        tvHighScore.text = gameSaveData.highScore.toString()
        // 復原遮片
        tvEnd.visibility = gameSaveData.endGameTvVisibility
    }
}