package com.example.projectandroid.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectandroid.R
import com.example.projectandroid.model.Cart
import com.example.projectandroid.ultil.CheckConnection
import org.json.JSONException
import java.util.*

class ConfirmCart : AppCompatActivity() {
    var btnConfirm: Button? = null
    var btnHuy: Button? = null
    var textViewTongtien: TextView? = null
    var idUser = 0
    var tongtien = 0
    var cartArrayList: ArrayList<Cart>? = null
    var edtDiaChi: EditText? = null
    var cartArrayListFilter: ArrayList<Cart>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_cart)
        textViewTongtien = findViewById(R.id.textviewTongtien)
        btnConfirm = findViewById(R.id.btnConfirm)
        btnHuy = findViewById(R.id.btnHuy)
        edtDiaChi = findViewById(R.id.textDiachi)
        cartArrayList = ArrayList<Cart>()
        cartArrayListFilter = ArrayList<Cart>()
        val intent = intent
        idUser = intent.getIntExtra("idUser", 123)
        tongtien = intent.getIntExtra("tongtienphaitra", 123)
        textViewTongtien.setText(tongtien.toString() + "")
        Toast.makeText(this, "id usser$idUser", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "tongtien : $tongtien", Toast.LENGTH_SHORT).show()
        btnConfirm.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@ConfirmCart, "Mua thanh cong", Toast.LENGTH_SHORT).show()
            GetDataCart()
        })
        btnHuy.setOnClickListener(View.OnClickListener { finish() })
    }

    fun themdonhangchitiet(cart: Cart) {
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, "http://" + "@string/localhost" + "/server/insertDonHangChiTiet.php",
            Response.Listener { response ->
                if (response.trim { it <= ' ' } == "success") {
                    Toast.makeText(
                        this@ConfirmCart,
                        "Them vào đơn hàng chi tiết thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                    deleteDonHang(cart.getId())
                } else {
                    Toast.makeText(this@ConfirmCart, "Lỗi thêm", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@ConfirmCart, "Da xay ra loi", Toast.LENGTH_SHORT).show()
                Log.d("vpq", "Loi!\n$error")
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["id_user"] = cart.getIdUser().toString() + ""
                params["tensanpham"] = cart.getTensanpham().toString() + ""
                params["soluong"] = cart.getSoluong().toString() + ""
                params["ngaymuahang"] = cart.getNgaymuahang()
                params["diachigiaohang"] = edtDiaChi!!.text.toString()
                params["giasanpham"] = cart.getGiasanpham().toString() + ""
                params["hinhanhsanpham"] = cart.getImageSanPham()
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    fun GetDataCart() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val jsonArrayRequest = JsonArrayRequest("http://" + "@string/localhost" + "/server/getDonHang.php",
            { response ->
                cartArrayList!!.clear()
                cartArrayListFilter!!.clear()
                if (response != null) {
                    for (i in 0 until response.length()) {
                        try {
                            var id = 0
                            var idUser = 0
                            var tensanpham = ""
                            var soluong = 0
                            var ngaymuahang = ""
                            var giasanpham = 0
                            var hinhanhsanpham = ""
                            val jsonObject = response.getJSONObject(i)
                            id = jsonObject.getInt("id")
                            idUser = jsonObject.getInt("idUser")
                            tensanpham = jsonObject.getString("tensanpham")
                            soluong = jsonObject.getInt("soluong")
                            ngaymuahang = jsonObject.getString("ngaymuahang")
                            giasanpham = jsonObject.getInt("giasanpham")
                            hinhanhsanpham = jsonObject.getString("hinhanhsanpham")
                            cartArrayList!!.add(
                                Cart(
                                    id,
                                    idUser,
                                    tensanpham,
                                    soluong,
                                    ngaymuahang,
                                    giasanpham,
                                    hinhanhsanpham
                                )
                            )
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    Toast.makeText(
                        this@ConfirmCart,
                        "carArraylist là: " + cartArrayList!!.size,
                        Toast.LENGTH_SHORT
                    ).show()
                    for (j in cartArrayList!!.indices) {
                        if (idUser == cartArrayList!![j].getIdUser()) {
                            val cart: Cart = cartArrayList!![j]
                            cartArrayListFilter!!.add(
                                Cart(
                                    cart.getId(),
                                    cart.getIdUser(),
                                    cart.getTensanpham(),
                                    cart.getSoluong(),
                                    cart.getNgaymuahang(),
                                    cart.getGiasanpham(),
                                    cart.getImageSanPham()
                                )
                            )
                        }
                    }
                    for (i in cartArrayListFilter!!.indices) {
                        val cart: Cart = cartArrayListFilter!![i]
                        themdonhangchitiet(cart)
                    }
                    Toast.makeText(
                        this@ConfirmCart,
                        "carArraylistFilter là: " + cartArrayListFilter!!.size,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) { error -> CheckConnection.ShowToast_Short(applicationContext, error.toString()) }
        requestQueue.add(jsonArrayRequest)
    }

    private fun deleteDonHang(idDonhangne: Int) {
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, "http://" + "@string/localhost" + "/server/deleteDonHang.php",
            Response.Listener { response ->
                if (response.trim { it <= ' ' } == "success") {
                    Toast.makeText(this@ConfirmCart, "Xoa thanh cong", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@ConfirmCart, "Loi Xoa", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@ConfirmCart, "Da xay ra loi", Toast.LENGTH_SHORT).show()
                Log.d("vpq", "Loi!\n$error")
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["iddonhang"] = idDonhangne.toString()
                return params
            }
        }
        requestQueue.add(stringRequest)
    }
}