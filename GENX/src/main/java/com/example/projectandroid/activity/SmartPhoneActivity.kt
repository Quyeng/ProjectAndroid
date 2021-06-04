package com.example.projectandroid.activity
/*
Team 10
 */
import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectandroid.adapter.PhoneAdapter
import com.example.projectandroid.model.Product
import com.example.projectandroid.ultil.CheckConnection
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class SmartPhoneActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null
    lateinit var listViewPhone: ListView
    var phoneAdapter: PhoneAdapter? = null
    var arrayListPhone: ArrayList<Product>? = null
    var arrayListPhoneFilter: ArrayList<Product>? = null
    var idPhone = 0
    var page = 1
    var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.projectandroid.R.layout.activity_smart_phone)
        listViewPhone = findViewById(com.example.projectandroid.R.id.listViewPhone)
        arrayListPhone = ArrayList<Product>()
        arrayListPhoneFilter = ArrayList<Product>()
        GetIdProductType()
        //            ActionToolBar();
        GetDataPhone()
        phoneAdapter = PhoneAdapter(this, com.example.projectandroid.R.layout.item_phone, arrayListPhoneFilter!!)
        listViewPhone.setAdapter(phoneAdapter)
        listViewPhone.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@SmartPhoneActivity, ProductDetailActivity::class.java)
            intent.putExtra("information", arrayListPhone!![position])
            startActivity(intent)
        })
    }

    private fun ActionBart() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar!!.setNavigationIcon(R.drawable.ic_menu_sort_by_size)
        toolbar!!.setNavigationOnClickListener { drawerLayout!!.openDrawer(GravityCompat.START) }
    }

    private fun GetDataPhone() {
        val requestQueue = Volley.newRequestQueue(this)
        val pathPhone: String = "http://192.168.1.6:8080/server/getsanpham.php"
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
                    if (arrayListPhone!![i].idProduct == 1) {
                        arrayListPhoneFilter!!.add(
                            Product(
                                arrayListPhone!![i].id,
                                arrayListPhone!![i].nameProduct,
                                arrayListPhone!![i].priceProduct,
                                arrayListPhone!![i].imageProduct,
                                arrayListPhone!![i].descriptionProduct,
                                arrayListPhone!![i].idProduct,
                                arrayListPhone!![i].id_thuonghieu,
                                arrayListPhone!![i].sosanphamdaban,
                                arrayListPhone!![i].sosanphamcontonkho,
                                arrayListPhone!![i].diemdanhgia
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