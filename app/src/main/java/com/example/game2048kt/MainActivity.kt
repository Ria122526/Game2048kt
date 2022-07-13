package com.example.game2048kt

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.test.data.ModeTitleImgData
import com.example.test.data.TheModeEnum

class MainActivity : AppCompatActivity() {

    private lateinit var tvStart: TextView
    private lateinit var tvRank: TextView
    private lateinit var tvSize: TextView
    private lateinit var ivGameTitle: ImageView
    private lateinit var ivSelectL: ImageView
    private lateinit var ivSelectR: ImageView
    private lateinit var ivCart: ImageView

    var modeDataList = ArrayList<ModeTitleImgData>()
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 取得結束時存入的position
        position = getSharedPreferences("Main", MODE_PRIVATE).getInt("mode", 0)

        initViews()
        modeAdd()
        changMode()
        clicking()
    }

    override fun onStop() {
        var sharedPreferences = getSharedPreferences("Main", MODE_PRIVATE)
        sharedPreferences.edit().putInt("mode", position).apply()
        super.onStop()
    }

    private fun initViews() {
        tvStart = findViewById(R.id.main_tv_start)
        tvRank = findViewById(R.id.main_tv_rank)
        tvSize = findViewById(R.id.main_tv_size)
        ivGameTitle = findViewById(R.id.main_iv_game)
        ivSelectL = findViewById(R.id.main_iv_select_left)
        ivSelectR = findViewById(R.id.main_iv_select_right)
        ivCart = findViewById(R.id.main_iv_cart)
    }

    // 點擊事件的設置
    private fun clicking() {
        // 開始遊戲
        tvStart.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@MainActivity, GameActivity::class.java)
            intent.putExtra("mode", modeDataList.get(position).title.key)
            startActivity(intent)
        }
        // 排行榜
        tvRank.setOnClickListener {
            // 這裡要設置Dialog
        }
        // 選單往左
        ivSelectL.setOnClickListener {
            position--
            changMode()
        }
        // 選單往右
        ivSelectR.setOnClickListener {
            position++
            changMode()
        }
        // 購物車
        ivCart.setOnClickListener {
            // 這裡要設置Dialog
        }
    }

    // 將模式加入集合中 (嘗試使用新學到的apply)
    private fun modeAdd() {
        modeDataList.apply {
            add(ModeTitleImgData(TheModeEnum.THREE_THREE, R.mipmap.logo_3))
            add(ModeTitleImgData(TheModeEnum.FOUR_FOUR, R.mipmap.logo_4))
            add(ModeTitleImgData(TheModeEnum.FIVE_FIVE, R.mipmap.logo_5))
            add(ModeTitleImgData(TheModeEnum.SIX_SIX, R.mipmap.logo_6))
            add(ModeTitleImgData(TheModeEnum.EIGHT_EIGHT, R.mipmap.logo_8))
        }
    }

    // 模式變換的設置
    private fun changMode() {
        if (position < 0) {
            position = 4
        } else if (position > 4) {
            position = 0
        }

        val modeTitleImgData = modeDataList[position]

        tvSize.text = modeTitleImgData.title.key
        ivGameTitle.setBackgroundResource(modeTitleImgData.imgResource)

    }
}