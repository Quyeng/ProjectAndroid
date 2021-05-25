package com.example.projectandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    private lateinit var tvUsername : TextView
    private lateinit var btnCart : Button
    private lateinit var btnMenu : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById()
        val results = intent.getStringExtra("user_name")
        tvUsername.text = results

    }

    private fun findViewById(){
        tvUsername = findViewById(R.id.tv_username)
        btnCart = findViewById(R.id.btn_cart)
        btnMenu = findViewById(R.id.btn_menu)
    }
}