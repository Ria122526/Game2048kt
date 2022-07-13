package com.example.test.data

// 常被用到的模式資源
enum class TheModeEnum(key: String) {
    // KEY
    THREE_THREE("3x3"),
    FOUR_FOUR("4x4"),
    FIVE_FIVE("5x5"),
    SIX_SIX("6x6"),
    EIGHT_EIGHT("8x8");

    var key: String = ""

    companion object {
        fun getEnum(key: String): TheModeEnum? {
            for (theMode: TheModeEnum in values()) {
                if (theMode.key == key) {
                    return theMode
                }
            }
            return null
        }
    }
}