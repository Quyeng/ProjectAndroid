package com.example.projectandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProductActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_product)
        manager = GridLayoutManager(this,2)
        val results = intent.getStringExtra("type")
        if (results != null) {
            Log.d("-----Pretty Printed JSON------ :",results)
        }
        if (results != null) {
            getAllData(results)
        }
    }

    private fun getMethod(type: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://genxshopping.herokuapp.com")
                .build()

        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getProducts(type)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                            JsonParser.parseString(
                                    response.body()
                                            ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                            )
                    )
                    Log.d("Pretty Printed JSON :", prettyJson)
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }
    }

    fun getAllData(type: String){
        Log.d("-----Pretty Printed JSON------ :","abc")
        Api.retrofitService.getProduct(type).enqueue(object: Callback<List<Product>> {
            override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
            ) {
                Log.d("-----Pretty Printed JSON------ :", response.toString())
                if(response.isSuccessful){
                    recyclerView = findViewById<RecyclerView>(R.id.recycle_view).apply{
                        myAdapter = ProductAdapter(response.body()!!)
                        layoutManager = manager
                        adapter = myAdapter
                    }
                    Log.d("-----Pretty Printed JSON------ :", response.toString())
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}