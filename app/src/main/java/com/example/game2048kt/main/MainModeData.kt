package com.example.game2048kt.main

import com.example.game2048kt.TheModeEnum

// 存放模式所需要的文字標題與圖片資料
class MainModeData(title: TheModeEnum, imgResource: Int) {

    // 該模式對應的標題
    var title: TheModeEnum = title

    // 該模式對應的圖片資源
    var imgResource: Int = imgResource

}