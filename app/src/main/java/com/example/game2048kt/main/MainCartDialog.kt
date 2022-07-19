package com.example.game2048kt.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.game2048kt.R

class MainCartDialog(context: Context) : Dialog(context) {

    lateinit var btnYes: Button
    lateinit var btnNo: Button
    lateinit var tvDialogTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(LayoutInflater.from(context).inflate(R.layout.cart_dialog, null))
        setCanceledOnTouchOutside(true)


        viewInit()
        initClicks()
    }

    private fun viewInit() {
        tvDialogTitle = findViewById(R.id.cart_dialog_tv_title)
        btnYes = findViewById(R.id.cart_dialog_bt_yes)
        btnNo = findViewById(R.id.cart_dialog_bt_no)
    }

    private fun initClicks() {
        btnYes.setOnClickListener(View.OnClickListener {
            tvDialogTitle.text = "無廣告"
        })

        btnNo.setOnClickListener(View.OnClickListener {
            tvDialogTitle.text = "無廣告"
        })
    }
}