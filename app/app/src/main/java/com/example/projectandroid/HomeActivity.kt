package com.example.projectandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class HomeActivity : AppCompatActivity() {
    private lateinit var tvUsername : TextView
    private lateinit var btnCart : Button
    private lateinit var btnMenu : Button
    private lateinit var imgShirt : ImageView
    private lateinit var imgPant : ImageView
    private lateinit var imgShoes : ImageView
    private lateinit var imgWatch : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById()
        val username = intent.getStringExtra("user_name")
        val id = intent.getStringExtra("_id")
        tvUsername.text = username
        btnMenu.setOnClickListener{
            showPopupMenu()
        }
        btnCart.setOnClickListener{
            val intent = Intent(this@HomeActivity, CartActivity::class.java)
            intent.putExtra("user_name", username)
            startActivity(intent)
        }
        imgShirt.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "shirts")
            intent.putExtra("user_name", username)
            intent.putExtra("_id", id)
            startActivity(intent)
        }
        imgPant.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "pants")
            intent.putExtra("user_name", username)
            intent.putExtra("_id", id)
            startActivity(intent)
        }
        imgShoes.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "shoes")
            intent.putExtra("user_name", username)
            intent.putExtra("_id", id)
            startActivity(intent)
        }
        imgWatch.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "accessories")
            intent.putExtra("user_name", username)
            intent.putExtra("_id", id)
            startActivity(intent)
        }
    }

    private fun findViewById(){
        tvUsername = findViewById(R.id.tv_username)
        btnCart = findViewById(R.id.btn_cart)
        btnMenu = findViewById(R.id.btn_menu)
        imgShirt = findViewById(R.id.img_shirt)
        imgPant = findViewById(R.id.img_pant)
        imgShoes = findViewById(R.id.img_shoes)
        imgWatch = findViewById(R.id.img_watch)
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(this, btnMenu)
        popupMenu.inflate(R.menu.menu)
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem ->

            when (item.itemId) {
                R.id.profile -> {
                    Toast.makeText(this,"Profile", Toast.LENGTH_SHORT).show()
                }
                R.id.shirt -> {
                    Toast.makeText(this, "Shirt", Toast.LENGTH_SHORT).show()
                }
                R.id.pant -> {
                    Toast.makeText(this, "Pant", Toast.LENGTH_SHORT).show()
                }
                R.id.shoes  -> {
                    Toast.makeText(this, "Shoes", Toast.LENGTH_SHORT).show()
                }
                R.id.watch -> {
                    Toast.makeText(this, "Watch", Toast.LENGTH_SHORT).show()
                }
            }
            true
        })

    }

}