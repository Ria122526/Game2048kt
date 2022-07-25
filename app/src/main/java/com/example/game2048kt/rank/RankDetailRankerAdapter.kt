package com.example.game2048kt.rank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView
import com.example.game2048kt.R
import java.text.DecimalFormat

class RankDetailRankerAdapter(rankerArr: ArrayList<RankArrData>) :
    RecyclerView.Adapter<RankDetailRankerAdapter.ViewHolder>() {

    private var rankerArr = rankerArr

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ifvRankerHead: ImageFilterView =
            itemView.findViewById(R.id.single_ranker_head)
        var tvRankerId: TextView = itemView.findViewById(R.id.single_ranker_id)
        var tvRankerScore: TextView = itemView.findViewById(R.id.single_ranker_score)
        var tvRankerRank: TextView = itemView.findViewById(R.id.single_ranker_rank)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_ranker, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rank = position + 1
        holder.tvRankerId.text = rankerArr[position].id
        holder.tvRankerScore.text = "分數：${formatScore(rankerArr[position].score)}"
        holder.tvRankerRank.text = "第 $rank 名"
    }

    override fun getItemCount(): Int {
        return rankerArr.size
    }

    fun formatScore(inputScore: Int): String {
        return DecimalFormat(",##0").format(inputScore)
    }
}