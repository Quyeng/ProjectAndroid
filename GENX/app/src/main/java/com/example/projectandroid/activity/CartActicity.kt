package com.example.projectandroid.activity
/*
Team 10
 */
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectandroid.R
import com.example.projectandroid.adapter.CartApdapter
import com.example.projectandroid.model.Cart
import com.example.projectandroid.ultil.CheckConnection
import com.example.projectandroid.ultil.GenX
import org.json.JSONException
import java.util.*

class CartActicity : AppCompatActivity() {
    var checkBoxTatCa: CheckBox? = null
    var btnDiscount: Button? = null
    var btnMuaHang: Button? = null
    var imgLogo: ImageView? = null
    var iconvanchuyen: ImageView? = null
    var imageIconxetaine: ImageView? = null
    var Tongtiensanpham: TextView? = null
    var mangidDiscount: ArrayList<Int>? = null
    var cartArrayList: ArrayList<Cart>? = null
    lateinit var cartArrayListFiler: ArrayList<Cart>
    lateinit var listViewCart: ListView
    var cartApdapter: CartApdapter? = null
    var REQUEST_CODE_DISCOUNT = 123
    var k = 0
    var idUsers = 0
    var tongtienphaitra = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_acticity)
        mangidDiscount = ArrayList()
        val intent = intent
        idUsers = intent.getIntExtra("idUser", 123)
        AnhXa()
        checkBoxTatCa!!.setOnClickListener {
            if (checkBoxTatCa!!.isChecked) {
                checkCheckBoxTatCaIsTrue()
                GetDataCart()
            } else {
                checkCheckBoxTatCaIsFalse()
                GetDataCart()
            }
        }
        btnDiscount!!.setOnClickListener {
            val tongtien = Tongtiensanpham!!.text.toString().trim { it <= ' ' }
            Log.e("vpq", "Tổng tiền là : $tongtien")
            val tien = tongtien.toInt()
            if (tien != 0) {
                val intent1 = Intent(this@CartActicity, DiscountActivity::class.java)
                startActivityForResult(intent1, REQUEST_CODE_DISCOUNT)
            } else if (cartArrayListFiler!!.size == 0) {
                Toast.makeText(this@CartActicity, "Giỏ hàng của bạn rỗng ", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@CartActicity, "Bạn hãy kiểm tra món hàng", Toast.LENGTH_SHORT).show()
            }
        }
        btnMuaHang!!.setOnClickListener {
            val a = Tongtiensanpham!!.text.toString()
            val tongtien = a.toInt()
            if (cartArrayListFiler!!.size == 0) {
                Toast.makeText(this@CartActicity, "Giỏ hàng của bạn rỗng ", Toast.LENGTH_SHORT).show()
            } else if (tongtien == 0) {
                Toast.makeText(this@CartActicity, "Bạn hãy kiểm tra món hàng", Toast.LENGTH_SHORT).show()
            } else {
                muaHang()
            }
        }
    }

    private fun muaHang() {
        val tien = Tongtiensanpham!!.text.toString()
        val tongtien = tien.toInt()
        val intent = Intent(this@CartActicity, ConfirmCart::class.java)
        intent.putExtra("idUser", idUsers)
        intent.putExtra("tongtienphaitra", tongtien)
        startActivity(intent)
        finish()
    }

    val isFalseTatCa: Unit
        get() {
            checkBoxTatCa!!.isChecked = false
        }

    fun isTrueTatCa(x: Int) {
        k = k + x
        if (cartArrayListFiler!!.size == k) {
            checkBoxTatCa!!.isChecked = true
            k = 0
        }
    }

    private fun AnhXa() {
        imgLogo = findViewById(R.id.imageLogo)
        iconvanchuyen = findViewById(R.id.imageIconxetai)
        imageIconxetaine = findViewById(R.id.imageIconxetaine)
        listViewCart = findViewById(R.id.listviewCart)
        Tongtiensanpham = findViewById(R.id.Tongtiensanpham)
        checkBoxTatCa = findViewById(R.id.checkboxTatCa)
        cartArrayList = ArrayList<Cart>()
        cartArrayListFiler = ArrayList<Cart>()
        GetDataCart()
        cartApdapter = CartApdapter(this, R.layout.dong_cart, cartArrayListFiler)
        listViewCart.setAdapter(cartApdapter)
        btnDiscount = findViewById(R.id.btnDiscount)
        btnMuaHang = findViewById(R.id.btnMuaHang)
    }

    fun checkCheckBoxTatCaIsTrue(): Boolean {
        if (checkBoxTatCa!!.isChecked) {
            var x = 0
            for (i in cartArrayListFiler!!.indices) {
                x += cartArrayListFiler!![i].giasanpham * cartArrayListFiler!![i].soluong
            }
            Tongtiensanpham!!.text = x.toString() + ""
            return true
        }
        return false
    }

    fun checkCheckBoxTatCaIsFalse(): Boolean {
        if (!checkBoxTatCa!!.isChecked) {
            val a = Tongtiensanpham!!.text.toString()
            var x = a.toInt()
            for (i in cartArrayListFiler!!.indices) {
                x = 0
            }
            Tongtiensanpham!!.text = x.toString() + ""
            return true
        }
        return false
    }

    fun TongTien(cart: Cart?, cong: Int, tru: Int) {
        val a = Tongtiensanpham!!.text.toString()
        var tongtien = a.toInt()
        if (cong != 0) {
            tongtien = tongtien + cong
            tongtienphaitra = tongtien
            Tongtiensanpham!!.text = "" + tongtien
        } else if (tru != 0) {
            tongtien = tongtien - tru
            tongtienphaitra = tongtien
            Tongtiensanpham!!.text = "" + tongtien
        }
        if (cart != null) {
            tongtien = tongtien + cart.giasanpham * cart.soluong
            tongtienphaitra = tongtien
            Tongtiensanpham!!.text = "" + tongtien
        }
    }

    fun deleteMonHang(idDonhang: Int, giasanpham: Int) {
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(Method.POST, GenX.pathXoaDonHang,
                Response.Listener { response ->
                    if (response.trim { it <= ' ' } == "success") {
                        Toast.makeText(this@CartActicity, "Xóa thành công", Toast.LENGTH_SHORT).show()
                        GetDataCart()
                    } else {
                        Toast.makeText(this@CartActicity, "Lỗi xóa", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this@CartActicity, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show()
                    Log.d("vpq", "Lỗi!\n$error")
                }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["iddonhang"] = idDonhang.toString()
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    fun CapNhatDonHang(iddonhang: Int, soluongitem: Int) {
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(Method.POST, GenX.pathCapNhatDonHang,
                Response.Listener { response ->
                    if (response.trim { it <= ' ' } == "success") {
                        Toast.makeText(this@CartActicity, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@CartActicity, "Lỗi cập nhật", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this@CartActicity, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show()
                    Log.d("vpq", "Loi!\n$error")
                }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                Log.e("vpq", "id don hang la$iddonhang")
                Log.e("vpq", "soluong don hang la$soluongitem")
                val params: MutableMap<String, String> = HashMap()
                params["iddonhang"] = iddonhang.toString()
                params["soluong"] = soluongitem.toString()
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    fun GetDataCart() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val jsonArrayRequest = JsonArrayRequest(GenX.pathDonHang, { response ->
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
                        var tenthuonghieu = ""
                        var sosanphamtonkho = 0
                        var hinhanhsanpham = ""
                        val jsonObject = response.getJSONObject(i)
                        id = jsonObject.getInt("id")
                        idUser = jsonObject.getInt("idUser")
                        tensanpham = jsonObject.getString("tensanpham")
                        soluong = jsonObject.getInt("soluong")
                        ngaymuahang = jsonObject.getString("ngaymuahang")
                        giasanpham = jsonObject.getInt("giasanpham")
                        tenthuonghieu = jsonObject.getString("tenthuonghieu")
                        sosanphamtonkho = jsonObject.getInt("sosanphamtonkho")
                        hinhanhsanpham = jsonObject.getString("hinhanhsanpham")
                        cartArrayList!!.add(Cart(id, idUser, tensanpham, soluong, ngaymuahang, giasanpham, tenthuonghieu, sosanphamtonkho, hinhanhsanpham))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                for (j in cartArrayList!!.indices) {
                    if (idUsers == cartArrayList!![j].idUser) {
                        val cart: Cart = cartArrayList!![j]
                        cartArrayListFiler!!.add(Cart(cart.id, cart.idUser, cart.tensanpham, cart.soluong, cart.ngaymuahang, cart.giasanpham, cart.tenhuonghieu,
                                cart.sosanphamtonkho, cart.imageSanPham))
                    }
                }
                var x = 0
                for (i in 0 until cartArrayListFiler!!.size - 1) {
                    x = cartArrayListFiler!![i].soluong
                    for (j in cartArrayListFiler!!.size - 1 downTo i + 1) {
                        if (cartArrayListFiler!![i].tensanpham.equals(cartArrayListFiler!![j].tensanpham)) {
                            x = x + cartArrayListFiler!![j].soluong
                            cartArrayListFiler!![i].soluong
                            cartArrayListFiler!!.removeAt(j)
                        }
                    }
                }
                cartApdapter?.notifyDataSetChanged()
            }
        }) { error -> CheckConnection.ShowToast_Short(applicationContext, error.toString()) }
        requestQueue.add(jsonArrayRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DISCOUNT && resultCode == RESULT_OK && data != null) {
            val discount = data.getIntExtra("discount", 1234)
            val tongtien = Tongtiensanpham!!.text.toString()
            var tongtienphaitra = Integer.valueOf(tongtien)
            tongtienphaitra = tongtienphaitra - discount
            Tongtiensanpham!!.text = tongtienphaitra.toString() + ""
        }
    }

    companion object {
        var a = ""
    }
}
