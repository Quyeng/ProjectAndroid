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
import com.example.projectandroid.adapter.PantsAdapter
import com.example.projectandroid.model.Product
import com.example.projectandroid.ultil.CheckConnection
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class PantsActivity : AppCompatActivity() {
    lateinit var listViewTrousers: ListView
    var pantsAdapter: PantsAdapter? = null
    var arrayListTrousers: ArrayList<Product>? = null
    var getArrayListTrousersFilter: ArrayList<Product>? = null
    var idPants = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pants)
        listViewTrousers = findViewById(R.id.listViewTrousers)
        arrayListTrousers = ArrayList()
        getArrayListTrousersFilter = ArrayList()
        GetIdProductType()
        GetDataPhone()
        pantsAdapter = PantsAdapter(this, R.layout.item_phone, getArrayListTrousersFilter!!)
        listViewTrousers.setAdapter(pantsAdapter)
        listViewTrousers.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@PantsActivity, ProductDetailActivity::class.java)
            intent.putExtra("information", arrayListTrousers!![position])
            startActivity(intent)
        })
    }

    private fun GetDataPhone() {
        val requestQueue = Volley.newRequestQueue(this)
        val pathPants = "http://192.168.1.6:8080/genX/getsanpham.php"
        val stringRequest = StringRequest(
            Request.Method.GET, pathPants,
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
                        arrayListTrousers!!.add(
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
                for (i in arrayListTrousers!!.indices) {
                    if (arrayListTrousers!![i].idProduct == 4) {
                        getArrayListTrousersFilter!!.add(
                            Product(
                                arrayListTrousers!![i].id,
                                arrayListTrousers!![i].nameProduct,
                                arrayListTrousers!![i].priceProduct,
                                arrayListTrousers!![i].imageProduct,
                                arrayListTrousers!![i].descriptionProduct,
                                arrayListTrousers!![i].idProduct,
                                    arrayListTrousers!![i].id_thuonghieu,
                                    arrayListTrousers!![i].sosanphamdaban,
                                    arrayListTrousers!![i].sosanphamcontonkho,
                                    arrayListTrousers!![i].diemdanhgia
                            )
                        )
                    }
                }
                Toast.makeText(
                    this@PantsActivity,
                    getArrayListTrousersFilter!!.size.toString() + "",
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) { error -> CheckConnection.ShowToast_Short(applicationContext, error.toString()) }
        requestQueue.add(stringRequest)
    }

    private fun GetIdProductType() {
        idPants = intent.getIntExtra("idProductType", -1)
        Log.d("giatrsp", idPants.toString() + "")
    }
}