package com.example.game2048kt.rank

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.game2048kt.R
import com.example.game2048kt.TheModeEnum
import com.example.game2048kt.tools.ConvertToPixel
import java.util.*

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

        initViews()
//        window?.setWindowAnimations(R.style.DialogCustomOutAni)
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
        window?.decorView?.setOnTouchListener(this)
    }

    override fun onClick(v: View?) {
        val rankDetailDialog = RankDetailDialog(this@RankDialog.context)
        when (v?.id) {
            R.id.dg_ll_three -> rankDetailDialog.modeReceived(TheModeEnum.THREE_THREE)
            R.id.dg_ll_four -> rankDetailDialog.modeReceived(TheModeEnum.FOUR_FOUR)
            R.id.dg_ll_five -> rankDetailDialog.modeReceived(TheModeEnum.FIVE_FIVE)
            R.id.dg_ll_six -> rankDetailDialog.modeReceived(TheModeEnum.SIX_SIX)
            R.id.dg_ll_eight -> rankDetailDialog.modeReceived(TheModeEnum.EIGHT_EIGHT)
        }
        rankDetailDialog.show()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        val height = window?.decorView?.height

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> if (event.y > (height!! * 0.3f)) gestureStartY = event.y
            MotionEvent.ACTION_MOVE -> {

                rlDialog.translationY = event.y

                gestureMoveY = gestureStartY - event.y
            }
            MotionEvent.ACTION_UP -> {

                if (gestureMoveY < 0) {

                    // 設計縮回動畫
                    rlDialog.startAnimation(
                        AnimationUtils.loadAnimation(
                            this@RankDialog.context,
                            R.anim.dialog_out
                        )
                    )

                    rlDialog.postDelayed({
                        cancel()
                    }, 400)

//                    Timer("SettingUp", false).schedule(300) {
//                        cancel()
//                    }.scheduledExecutionTime()

                } else if (gestureMoveY > 0)
                    rlDialog.translationY =
                        (ConvertToPixel.convertDpToPixel(0f, this@RankDialog.context))
            }
        }
        return true
    }
}