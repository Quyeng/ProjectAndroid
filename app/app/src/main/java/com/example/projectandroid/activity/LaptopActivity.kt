package com.example.projectandroid.activity

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectandroid.R
import com.example.projectandroid.adapter.LaptopAdapter
import com.example.projectandroid.model.Product
import com.example.projectandroid.ultil.CheckConnection
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class LaptopActivity : AppCompatActivity() {
    var toolbarPhone: Toolbar? = null
    var listViewPhone: ListView? = null
    var phoneAdapter: LaptopAdapter? = null
    var arrayListPhone: ArrayList<Product>? = null
    var arrayListPhoneFilter: ArrayList<Product>? = null
    var idPhone = 0
    var page = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laptop)
        listViewPhone = findViewById(R.id.listViewLaptop)
        arrayListPhone = ArrayList<Product>()
        arrayListPhoneFilter = ArrayList<Product>()
        GetIdProductType()
        //            ActionToolBar();
        GetDataPhone()
        phoneAdapter = LaptopAdapter(this, R.layout.item_phone, arrayListPhoneFilter!!)
        with(listViewPhone) { this?.setAdapter(phoneAdapter) }
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
                        arrayListPhone!!.add(
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
                for (i in arrayListPhone!!.indices) {
                    if (arrayListPhone!![i].idProduct === 2) {
                        arrayListPhoneFilter!!.add(
                            Product(
                                arrayListPhone!![i].id,
                                arrayListPhone!![i].nameProduct,
                                arrayListPhone!![i].priceProduct,
                                arrayListPhone!![i].imageProduct,
                                arrayListPhone!![i].descriptionProduct,
                                arrayListPhone!![i].idProduct
                            )
                        )
                    }
                }
            }
        ) { error -> CheckConnection.ShowToast_Short(applicationContext, error.toString()) }
        requestQueue.add(stringRequest)
    }

    private fun GetIdProductType() {
        idPhone = intent.getIntExtra("idProductType", -1)
        Log.d("giatrsp", idPhone.toString() + "")
    }

    private fun anhxa() {}
}