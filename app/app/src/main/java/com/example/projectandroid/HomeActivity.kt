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
        val results = intent.getStringExtra("user_name")
        tvUsername.text = results
        btnMenu.setOnClickListener{
            showPopupMenu()
        }
        imgShirt.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "shirts")
            startActivity(intent)
        }
        imgPant.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "pants")
            startActivity(intent)
        }
        imgShoes.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "shoes")
            startActivity(intent)
        }
        imgWatch.setOnClickListener {
            val intent = Intent(this@HomeActivity, ProductActivity::class.java)
            intent.putExtra("type", "accessories")
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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.profile -> {
//                newGame()
                true
            }
            R.id.shirt -> {

                true
            }
            R.id.pant -> {

                true
            }
            R.id.shoes -> {

                true
            }
            R.id.watch -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getMethod(typeProduct: String) {

        // Create Retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl("http://genxshopping.herokuapp.com")
                .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            // Do the GET request and get response
            val response = service.getProducts(typeProduct)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                            JsonParser.parseString(
                                    response.body()
                                            ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                            )
                    )
                    Log.d("Pretty Printed JSON :", prettyJson)

//                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
//                    intent.putExtra("json_results", prettyJson)
//                    this@MainActivity.startActivity(intent)

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }


}