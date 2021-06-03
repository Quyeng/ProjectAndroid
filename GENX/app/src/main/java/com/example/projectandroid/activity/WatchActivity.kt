package com.example.projectandroid.activity
/*
Team 10
 */
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectandroid.R
import com.example.projectandroid.adapter.WatchAdapter
import com.example.projectandroid.model.Product
import com.example.projectandroid.ultil.CheckConnection
import com.example.projectandroid.ultil.GenX
import org.json.JSONArray
import org.json.JSONException
import java.util.*


class WatchActivity : AppCompatActivity() {
    lateinit var listViewWatch: ListView
    var WatchAdapter: WatchAdapter? = null
    var arrayListWatch: ArrayList<Product>? = null
    var arrayListWatchFilter: ArrayList<Product>? = null
    var idWatch = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch)
        listViewWatch = findViewById(R.id.listViewWatch)
        arrayListWatch = ArrayList()
        arrayListWatchFilter = ArrayList()
        GetIdProductType()
        GetDataPhone()
        WatchAdapter = WatchAdapter(this, R.layout.item_phone, arrayListWatchFilter!!)
        listViewWatch.setAdapter(WatchAdapter)
        listViewWatch.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@WatchActivity, ProductDetailActivity::class.java)
            intent.putExtra("information", arrayListWatch!![position])
            startActivity(intent)
        })
    }

    private fun GetDataPhone() {
        val requestQueue = Volley.newRequestQueue(this)
        val pathWatch = GenX.pathPhone
        val stringRequest = StringRequest(
            Request.Method.GET, pathWatch,
            { response ->
                var idPhone = 0
                var namePhone = ""
                var pricePhone = 0
                var imagePhone = ""
                var descriptionPhone = ""
                var idProductPhone = 0
                val IdProduct = 0
                var idthuonghieu = 0
                var sosanphamdaban = 0
                var sosanphamtonkho = 0
                var diemdanhgia = ""
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        idPhone = jsonObject.getInt("id")
                        namePhone = jsonObject.getString("tensp")
                        pricePhone = jsonObject.getInt("giasp")
                        imagePhone = jsonObject.getString("hinhanhsp")
                        descriptionPhone = jsonObject.getString("motasp")
                        idProductPhone = jsonObject.getInt("idsanpham")
                        idthuonghieu = jsonObject.getInt("id_thuonghieu")
                        sosanphamtonkho = jsonObject.getInt("sosanphamtonkho")
                        sosanphamdaban = jsonObject.getInt("sosanphamdaban")
                        diemdanhgia = jsonObject.getString("diemdanhgia")
                        arrayListWatch!!.add(
                            Product(
                                idPhone,
                                namePhone,
                                pricePhone,
                                imagePhone,
                                descriptionPhone,
                                idProductPhone,
                                idthuonghieu,
                                sosanphamdaban,
                                sosanphamtonkho,
                                diemdanhgia
                            )
                        )
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                for (i in arrayListWatch!!.indices) {
                    if (arrayListWatch!![i].idProduct == 6) {
                        arrayListWatchFilter!!.add(
                            Product(
                                arrayListWatch!![i].id,
                                arrayListWatch!![i].nameProduct,
                                arrayListWatch!![i].priceProduct,
                                arrayListWatch!![i].imageProduct,
                                arrayListWatch!![i].descriptionProduct,
                                arrayListWatch!![i].idProduct,
                                    arrayListWatch!![i].id_thuonghieu,
                                    arrayListWatch!![i].sosanphamdaban,
                                    arrayListWatch!![i].sosanphamcontonkho,
                                    arrayListWatch!![i].diemdanhgia
                            )
                        )
                    }
                }
                Toast.makeText(
                    this@WatchActivity,
                    arrayListWatchFilter!!.size.toString() + "",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) { error -> CheckConnection.ShowToast_Short(applicationContext, error.toString()) }
        requestQueue.add(stringRequest)
    }
    private fun GetIdProductType() {
        idWatch = intent.getIntExtra("idProductType", -1)
        Log.d("giatrsp", idWatch.toString() + "")
    }
}