package com.example.projectandroid.activity

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
import com.example.projectandroid.adapter.ShoesAdapter
import com.example.projectandroid.model.Product
import com.example.projectandroid.ultil.CheckConnection
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class ShoesActivity : AppCompatActivity() {
    var toolbarPhone: Toolbar? = null
    var listViewShoes: ListView? = null
    var shoesAdapter: ShoesAdapter? = null
    var arrayShoesShirt: ArrayList<Product>? = null
    var arrayListShoesFilter: ArrayList<Product>? = null
    var idShirt = 0
    var page = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoes)
        listViewShoes = findViewById(R.id.listViewShoes)
        arrayShoesShirt = ArrayList<Product>()
        arrayListShoesFilter = ArrayList<Product>()
        GetIdProductType()
        //            ActionToolBar();
        GetDataPhone()
        shoesAdapter = ShoesAdapter(this, R.layout.item_phone, arrayListShoesFilter!!)
        listViewShoes.setAdapter(shoesAdapter)
        listViewShoes.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@ShoesActivity, ProductDetailActivity::class.java)
            intent.putExtra("information", arrayShoesShirt!![position])
            startActivity(intent)
        })
    }

    private fun GetDataPhone() {
        val requestQueue = Volley.newRequestQueue(this)
        val pathPhone: String = "http://" + "@string/localhost" + "/server/getsanpham.php"
        val stringRequest = StringRequest(
            Request.Method.GET, pathPhone,
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
                        arrayShoesShirt!!.add(
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
                for (i in arrayShoesShirt!!.indices) {
                    if (arrayShoesShirt!![i].getIdProduct() === idShirt) {
                        arrayListShoesFilter!!.add(
                            Product(
                                arrayShoesShirt!![i].getId(),
                                arrayShoesShirt!![i].getNameProduct(),
                                arrayShoesShirt!![i].getPriceProduct(),
                                arrayShoesShirt!![i].getImageProduct(),
                                arrayShoesShirt!![i].getDescriptionProduct(),
                                arrayShoesShirt!![i].getIdProduct()
                            )
                        )
                    }
                }
                Toast.makeText(
                    this@ShoesActivity,
                    arrayListShoesFilter!!.size.toString() + "",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) { error -> CheckConnection.ShowToast_Short(applicationContext, error.toString()) }
        requestQueue.add(stringRequest)
    }

    private fun GetIdProductType() {
        idShirt = intent.getIntExtra("idProductType", -1)
        Log.d("giatrsp", idShirt.toString() + "")
    }

    private fun anhxa() {}
}