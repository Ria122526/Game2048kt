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

class MainActivity : AppCompatActivity(), View.OnClickListener {

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

        position = getSharedPreferences("Main", MODE_PRIVATE).getInt("mode", 0)

        initViews()
        modeAdding()
        // 第一次載入時透過position設定
        changMode()
    }

    override fun onStop() {
        var sharedPreferences: SharedPreferences = getSharedPreferences("Main", MODE_PRIVATE)
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

    private fun modeAdding() {
        modeDataList.add(ModeTitleImgData(TheModeEnum.THREE_THREE, R.mipmap.logo_3))
        modeDataList.add(ModeTitleImgData(TheModeEnum.FOUR_FOUR, R.mipmap.logo_4))
        modeDataList.add(ModeTitleImgData(TheModeEnum.FIVE_FIVE, R.mipmap.logo_5))
        modeDataList.add(ModeTitleImgData(TheModeEnum.SIX_SIX, R.mipmap.logo_6))
        modeDataList.add(ModeTitleImgData(TheModeEnum.EIGHT_EIGHT, R.mipmap.logo_8))
    }

    private fun changMode() {
        if (position < 0) {
            position = 4
        } else if (position > 4) {
            position = 0
        }

        val modeTitleImgData: ModeTitleImgData = modeDataList.get(position)

        tvSize.setText(modeTitleImgData.title.key)
        ivGameTitle.setBackgroundResource(modeTitleImgData.imgResource)

    }

    private fun gameIntent() {
        val intent = Intent()
        intent.setClass(this@MainActivity, GameActivity::class.java)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            tvStart -> print("happy")

        }
    }
}