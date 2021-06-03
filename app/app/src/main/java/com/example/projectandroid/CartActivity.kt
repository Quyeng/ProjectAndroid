package com.example.projectandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    private lateinit var tvType: TextView
    private lateinit var imgBack: ImageView
    private lateinit var btnCart: Button
    private lateinit var btnMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_product)
        findViewById()
        tvType.text = "Your Cart"
        manager = GridLayoutManager(this,2)
        val username = intent.getStringExtra("user_name")
        if (username != null) {
            getAllCart(username)
        }
        imgBack.setOnClickListener{
            val intent = Intent(this@CartActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun findViewById(){
        tvType = findViewById(R.id.tv_type)
        btnCart = findViewById(R.id.btn_cart)
        btnMenu = findViewById(R.id.btn_menu)
        imgBack = findViewById(R.id.img_back)
    }

    private fun getAllCart(username: String){
        Api.retrofitService.getCart(username).enqueue(object: Callback<List<Cart>> {
            override fun onResponse(
                call: Call<List<Cart>>,
                response: Response<List<Cart>>
            ) {
                if(response.isSuccessful){
                    recyclerView = findViewById<RecyclerView>(R.id.recycle_view).apply{
                        myAdapter = CartAdapter(response.body()!!)
                        layoutManager = manager
                        adapter = myAdapter
                    }
                    Log.d("-----Pretty Printed JSON------ :", response.toString())
                }
            }
            override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}