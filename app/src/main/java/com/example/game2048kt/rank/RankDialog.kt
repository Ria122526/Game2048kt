package com.example.game2048kt.rank

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.game2048kt.R
import com.example.game2048kt.tools.ConvertToPixel

class RankDialog(context: Context) : Dialog(context, R.style.RankDialogCustom),
    View.OnClickListener, View.OnTouchListener {

    lateinit var llItem3x3: LinearLayout
    lateinit var llItem4x4: LinearLayout
    lateinit var llItem5x5: LinearLayout
    lateinit var llItem6x6: LinearLayout
    lateinit var llItem8x8: LinearLayout
    lateinit var rlDialog: RelativeLayout

    var gestureStartY = 0f
    var gestureMoveY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_rank, null))

        // 設計縮回動畫
        window?.setWindowAnimations(R.style.DialogCustomOutAni)

        initViews()
    }

    private fun initViews() {
        llItem3x3 = findViewById(R.id.dg_ll_three)
        llItem4x4 = findViewById(R.id.dg_ll_four)
        llItem5x5 = findViewById(R.id.dg_ll_five)
        llItem6x6 = findViewById(R.id.dg_ll_six)
        llItem8x8 = findViewById(R.id.dg_ll_eight)

        rlDialog = findViewById(R.id.dg_rl)

        llItem3x3.setOnClickListener(this)
        llItem4x4.setOnClickListener(this)
        llItem5x5.setOnClickListener(this)
        llItem6x6.setOnClickListener(this)
        llItem8x8.setOnClickListener(this)
        rlDialog.setOnTouchListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
//            R.id.dg_ll_three ->
//            R.id.dg_ll_four ->
//            R.id.dg_ll_five ->
//            R.id.dg_ll_six ->
//            R.id.dg_ll_eight ->
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> gestureStartY = event.y
            MotionEvent.ACTION_MOVE -> {
                gestureMoveY = event.y - gestureStartY
                window?.decorView?.scrollBy(0, -(gestureMoveY.toInt()))
                gestureStartY = event.y
            }
            MotionEvent.ACTION_UP -> {
                if (gestureMoveY > 0)
                    cancel()
                else if (gestureMoveY < 0)
                    rlDialog.translationY =
                        (ConvertToPixel.convertDpToPixel(200f, this@RankDialog.context))
            }
        }
        return true
    }
}