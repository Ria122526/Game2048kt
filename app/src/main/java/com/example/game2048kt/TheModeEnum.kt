package com.example.game2048kt

// 常被用到的模式資源
enum class TheModeEnum(key: String) {

    // KEY
    THREE_THREE("3x3"),
    FOUR_FOUR("4x4"),
    FIVE_FIVE("5x5"),
    SIX_SIX("6x6"),
    EIGHT_EIGHT("8x8");

    var key = key

    companion object {
        fun getEnum(key: String): TheModeEnum? {
            for (m in values()) {
                if (m.key == key) {
                    return m
                }
            }
            return null
        }
    }
}