package com.example.game2048kt.rank

//import com.example.game2048kt.roomDataBase.RankData
//import com.example.game2048kt.roomDataBase.RankDataBase
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.game2048kt.R
import com.example.game2048kt.TheModeEnum

class RankDetailDialog(context: Context) :
    Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen) {

    // 最上面的表頭
    private lateinit var ibBack: ImageButton
    private lateinit var ifvMyHeadInform: ImageFilterView
    private lateinit var ifvGameInform: ImageFilterView
    private lateinit var tvGameTitle: TextView
    private lateinit var swAllSwitchFriend: Switch
    // 我的排行榜

    // 排行榜本體
    private lateinit var rvDetailRanker: RecyclerView

    //排行榜資料
    private var rankerArr = ArrayList<RankerArrData>()
    private val rankDetailRankerAdapter = RankDetailRankerAdapter(rankerArr)

    init {
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_rank_detail, null))

        initViews()
        initClicks()
    }

    override fun show() {
        // show發生以前需要先定義動畫
        window?.setWindowAnimations(R.style.DialogCustomInAni)

        super.show()
    }

    private fun initViews() {
        ibBack = findViewById(R.id.rank_detail_iv_back)
        ifvMyHeadInform = findViewById(R.id.rank_detail_iv_me)
        ifvGameInform = findViewById(R.id.rank_detail_iv_game)
        tvGameTitle = findViewById(R.id.rank_detail_tv_game_title)
        swAllSwitchFriend = findViewById(R.id.rank_detail_sw)


        // 設定排行榜的recyclerView
        rvDetailRanker = findViewById(R.id.rank_detail_rv)
        rvDetailRanker.setHasFixedSize(true)
        rvDetailRanker.layoutManager = LinearLayoutManager(this@RankDetailDialog.context)
        rvDetailRanker.adapter = rankDetailRankerAdapter
    }

    private fun initClicks() {
        ibBack.setOnClickListener(View.OnClickListener {
            cancel()
        })
    }

    fun modeReceived(mode: TheModeEnum) {
        writeRankData()
        tvGameTitle.text = mode.key
    }

    private fun writeRankData() {
        // 建立資料庫物件
//        val rankDataBase = Room.databaseBuilder(
//            this@RankDetailDialog.context,
//            RankDataBase::class.java,
//            "Rank"
//        ).build()

        // 取得DAO
//        val rankDao = rankDataBase.dataDao()

//        Thread {
//
//            for (i in rankDao.getAll()) {
//                val data = RankerArrData()
//
//                data.rankerId = i.id
//                data.rankerScore = i.score
//
//                rankerArr.add(data)
//            }
//
//        }.start()
    }
}