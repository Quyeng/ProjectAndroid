package com.example.projectandroid.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.projectandroid.R
import com.example.projectandroid.adapter.DiscountAdapter
import com.example.projectandroid.model.Discount
import com.example.projectandroid.ultil.CheckConnection
import org.json.JSONException
import java.util.*

class DiscountActivity : AppCompatActivity() {
    var discountArrayList: ArrayList<Discount>? = null
    var discountAdapter: DiscountAdapter? = null
    var listViewDiscount: ListView? = null
    var btnXacnhangiamgia: Button? = null
    var btnhuyxacnhan: Button? = null
    var discounts = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discount)
        discountArrayList = ArrayList<Discount>()
        btnXacnhangiamgia = findViewById(R.id.btnxacnhangiamgia)
        btnhuyxacnhan = findViewById(R.id.btnhuyxacnhangiamgia)
        listViewDiscount = findViewById(R.id.listviewgiamgia)
        discountAdapter = DiscountAdapter(this, R.layout.dong_discount, discountArrayList!!)
        listViewDiscount.setAdapter(discountAdapter)
        GetDataDiscount()
        btnXacnhangiamgia.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.putExtra("discount", discounts)
            setResult(RESULT_OK, intent)
            finish()
        })
    }

    fun GetDataDiscount() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val jsonArrayRequest = JsonArrayRequest(
            "http://" + "@string/localhost" + "/server/getDiscount.php",
            { response ->
                if (response != null) {
                    for (i in 0 until response.length()) {
                        try {
                            var id = 0
                            var ten = ""
                            var tiengiamgia = 0
                            val jsonObject = response.getJSONObject(i)
                            id = jsonObject.getInt("id")
                            ten = jsonObject.getString("ten")
                            tiengiamgia = jsonObject.getInt("giagiam")
                            discountArrayList!!.add(Discount(id, ten, tiengiamgia))
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    discountAdapter?.notifyDataSetChanged()
                }
            }
        ) { error -> CheckConnection.ShowToast_Short(applicationContext, error.toString()) }
        requestQueue.add(jsonArrayRequest)
    }

    fun discountSanpham(discount: Int) {
        discounts = discount
    }
}