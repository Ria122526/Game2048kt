package com.example.test.data

// 紀錄遊戲中需要被儲存的資料
class GameSaveData {

    // 用來儲存每一組ID與分數資料
    var rankScore = ArrayList<RankIdScoreData>()

    // 遊戲中成績，當前分數與最高分
    var score: Int = 0;

    var highScore: Int = 0;

    // 遊戲畫面資料，以座標表示該位置的值
    var coorsArr = Array(0) { Array(0) { 0 } }
}