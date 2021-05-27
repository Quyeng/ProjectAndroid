package com.example.projectandroid.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.projectandroid.R
import com.example.projectandroid.adapter.CartApdapter
import com.example.projectandroid.adapter.DetailCartAdapter
import com.example.projectandroid.dangnhap
import com.example.projectandroid.model.DetailCart
import com.example.projectandroid.model.User
import com.example.projectandroid.ultil.CheckConnection
import org.json.JSONException
import java.util.*

class HistoryActivity : AppCompatActivity() {
    var btndangnhap: Button? = null
    var btnShowUser: Button? = null
    var btnExit: Button? = null
    var textViewDonhang: TextView? = null
    var REQUEST_CODE_USER = 123
    var userArrayList: ArrayList<User>? = null
    var user: User? = null

    //user
    var idUser = 0
    var usernameuser = ""
    var emailUser = ""
    var addressUser = ""
    var idUserDangNhap = 0
    var listViewCart: ListView? = null
    var detailCartAdapter: DetailCartAdapter? = null
    var cartArrayList: ArrayList<DetailCart>? = null
    var cartArrayListFiler: ArrayList<DetailCart>? = null
    var cartApdapter: CartApdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        btndangnhap = findViewById(R.id.btnlogin)
        btnShowUser = findViewById(R.id.btnShowUserneem)
        textViewDonhang = findViewById(R.id.texviewThongtindonhang)
        btnExit = findViewById(R.id.showUserExitne)
        userArrayList = ArrayList<User>()
        cartArrayList = ArrayList<DetailCart>()
        cartArrayListFiler = ArrayList<DetailCart>()
        GetDataUsers()
        listViewCart = findViewById(R.id.listviewDonhangchitiet)
        detailCartAdapter =
            DetailCartAdapter(this, R.layout.dong_don_hang_chi_tiet, cartArrayListFiler!!)
        listViewCart?.setAdapter(detailCartAdapter)
        btndangnhap?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@HistoryActivity, dangnhap::class.java)
            intent.putExtra("idProductType", 1)
            startActivityForResult(intent, REQUEST_CODE_USER)
        })
        btnShowUser?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@HistoryActivity, InformationUserActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        })
        btnExit?.setOnClickListener(View.OnClickListener {
            btnShowUser?.setVisibility(View.GONE)
            btnExit?.setVisibility(View.GONE)
            btndangnhap?.setVisibility(View.VISIBLE)
            textViewDonhang?.setText("Bạn chưa đăng nhập")
            listViewCart?.setVisibility(View.GONE)
        })
    }

    fun GetDataUsers() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val jsonArrayRequest = JsonArrayRequest(
            "http://" + "@string/localhost" + "/server/getUser.php",
            { response ->
                userArrayList!!.clear()
                if (response != null) {
                    for (i in 0 until response.length()) {
                        try {
                            val jsonObject = response.getJSONObject(i)
                            idUser = jsonObject.getInt("id")
                            usernameuser = jsonObject.getString("username")
                            emailUser = jsonObject.getString("email")
                            addressUser = jsonObject.getString("address")
                            userArrayList!!.add(User(idUser, usernameuser, emailUser, addressUser))
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        ) { error -> CheckConnection.ShowToast_Short(applicationContext, error.toString()) }
        requestQueue.add(jsonArrayRequest)
    }

    fun GetDataCart() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val jsonArrayRequest = JsonArrayRequest("http://" + "@string/localhost" + "/server/getDonHangChiTiet.php",
            { response ->
                cartArrayList!!.clear()
                cartArrayListFiler!!.clear()
                if (response != null) {
                    for (i in 0 until response.length()) {
                        try {
                            var id = 0
                            var idUser = 0
                            var tensanpham = ""
                            var soluong = 0
                            var ngaymuahang = ""
                            var giasanpham = 0
                            var diachigiaohang = ""
                            var hinhanhsanpham = ""
                            val jsonObject = response.getJSONObject(i)
                            id = jsonObject.getInt("id")
                            idUser = jsonObject.getInt("id_user")
                            tensanpham = jsonObject.getString("tensanpham")
                            soluong = jsonObject.getInt("soluong")
                            ngaymuahang = jsonObject.getString("ngaymuahang")
                            giasanpham = jsonObject.getInt("giasanpham")
                            diachigiaohang = jsonObject.getString("diachigiaohang")
                            hinhanhsanpham = jsonObject.getString("hinhanhsanpham")
                            cartArrayList!!.add(
                                DetailCart(
                                    id,
                                    idUser,
                                    tensanpham,
                                    giasanpham,
                                    soluong,
                                    ngaymuahang,
                                    diachigiaohang,
                                    hinhanhsanpham
                                )
                            )
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    Toast.makeText(
                        this@HistoryActivity,
                        "you have :" + cartArrayList!!.size,
                        Toast.LENGTH_SHORT
                    ).show()
                    for (j in cartArrayList!!.indices) {
                        if (idUserDangNhap == cartArrayList!![j].idUser) {
                            val cart: DetailCart = cartArrayList!![j]
                            cartArrayListFiler!!.add(
                                DetailCart(
                                    cart.id,
                                    cart.idUser,
                                    cart.tensanpham,
                                    cart.giasanpham,
                                    cart.soluong,
                                    cart.ngaymuahang,
                                    cart.diachigiaohang,
                                    cart.hinhanhsanpham
                                )
                            )
                        }
                    }
                    Toast.makeText(
                        this@HistoryActivity,
                        "you have :" + cartArrayListFiler!!.size,
                        Toast.LENGTH_SHORT
                    ).show()
                    var x = 0
                    for (i in 0 until cartArrayListFiler!!.size - 1) {
                        x = cartArrayListFiler!![i].soluong
                        for (j in cartArrayListFiler!!.size - 1 downTo i + 1) {
                            if (cartArrayListFiler!![i].tensanpham.equals(
                                    cartArrayListFiler!![j].tensanpham
                                )
                            ) {
                                x = x + cartArrayListFiler!![j].soluong
                                cartArrayListFiler!![i].soluong
                                cartArrayListFiler!!.removeAt(j)
                            }
                        }
                    }
                    Toast.makeText(
                        this@HistoryActivity,
                        "you have :" + cartArrayListFiler!!.size,
                        Toast.LENGTH_SHORT
                    ).show()
                    detailCartAdapter?.notifyDataSetChanged()
                }
            }
        ) { error -> CheckConnection.ShowToast_Short(applicationContext, error.toString()) }
        requestQueue.add(jsonArrayRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_USER && resultCode == RESULT_OK && data != null) {
            val email = data.getStringExtra("email")
            Log.e("vpq", "mang có bao nhieu phan tu" + userArrayList!!.size)
            for (i in userArrayList!!.indices) {
                if (email == userArrayList!![i].email) {
                    user = userArrayList!![i]
                    idUserDangNhap = userArrayList!![i].id
                    btnShowUser!!.text = "Hello: " + userArrayList!![i].username
                    btnShowUser!!.visibility = View.VISIBLE
                    btnExit!!.visibility = View.VISIBLE
                    btnExit!!.text = "Thoát"
                    btndangnhap!!.visibility = View.GONE
                    textViewDonhang!!.text = "Đơn hàng đã mua"
                    GetDataCart()
                    listViewCart!!.visibility = View.VISIBLE
                }
            }
        }
    }
}