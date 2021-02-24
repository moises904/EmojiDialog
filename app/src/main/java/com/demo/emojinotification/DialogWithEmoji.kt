package com.demo.emojinotification

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.emoji_dialog.*


class DialogWithEmoji(context: Context, messageEmoji: MessageEmoji) : Dialog(context) {

    private var localMessageEmoji:MessageEmoji? = null

    init {
        setCancelable(false)
        this.localMessageEmoji = messageEmoji
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        initializeView()
        loadMessageData()
    }

    private fun initializeView() {
        setContentView(R.layout.emoji_dialog)
        imgClose.setOnClickListener { dismiss() }

    }

    private fun loadMessageData() {
        localMessageEmoji?.let {
            etvType.text = it.type
            etvTitulo.text = it.title
            etvDescription.text = it.description
        }

    }

    override fun onStart() {
        super.onStart()
        this.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
    }

}