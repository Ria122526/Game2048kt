package com.example.game2048kt.tools

import android.content.Context

object ConvertToDp {

    /**
     * Covert dp to px
     * @param dp
     * @param context
     * @return pixel
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * getDensity(context)
    }

    /**
     * 取得螢幕密度
     * 120dpi = 0.75
     * 160dpi = 1 (default)
     * 240dpi = 1.5
     * @param context
     * @return
     */
    private fun getDensity(context: Context): Float {
        val metrics = context.resources.displayMetrics
        return metrics.density
    }
}