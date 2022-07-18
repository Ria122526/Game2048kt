package com.example.game2048kt.game

// 紀錄遊戲中需要被儲存的資料
class GameSaveData {

    // 遊戲中成績，當前分數與最高分
    var score: Int = 0;

    var highScore: Int = 0;

    // 遊戲畫面資料，以座標表示該位置的值
    lateinit var coorsArr: Array<Array<Int>>

    // 上一步資料
    lateinit var moveArr: Array<Array<Int>>
}