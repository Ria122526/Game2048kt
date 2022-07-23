package com.example.game2048kt.game

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.game2048kt.GameEndDialogSendId
import com.example.game2048kt.R

class GameEndDialog(context: Context) : Dialog(context) {

    lateinit var tvInputHint: TextView
    lateinit var etIdInput: EditText
    lateinit var btnOk: Button

    private var inputId = "NoName"

    var sendId: GameEndDialogSendId? = null

    init {
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_game_end_record, null))
        setCanceledOnTouchOutside(false)
    }

    override fun create() {
        super.create()
        initViews()
        initClicks()
    }

    private fun initViews() {
        tvInputHint = findViewById(R.id.game_end_record_dialog_tv_hint)
        etIdInput = findViewById(R.id.game_end_record_dialog_et)
        btnOk = findViewById(R.id.game_end_record_dialog_btn)
    }

    private fun initClicks() {
        btnOk.setOnClickListener(View.OnClickListener {
            checkOk()
            sendId?.send(inputId)

        })
    }

    private fun checkOk() {
        if (etIdInput.text.equals("")) tvInputHint.visibility = View.VISIBLE
        else inputId = etIdInput.text.toString()
    }

    override fun onBackPressed() {
        sendId?.send(inputId)
        super.onBackPressed()
    }
}