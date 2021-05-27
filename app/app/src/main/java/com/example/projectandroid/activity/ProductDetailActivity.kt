package com.example.projectandroid.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectandroid.R
import com.example.projectandroid.adapter.CommentAdapter
import com.example.projectandroid.dangnhap
import com.example.projectandroid.model.Comment
import com.example.projectandroid.model.Product
import com.example.projectandroid.model.User
import com.example.projectandroid.ultil.CheckConnection
import com.squareup.picasso.Picasso
import org.json.JSONException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ProductDetailActivity : AppCompatActivity() {
    var btnToolbar: Toolbar? = null
    var imageViewProductDetail: ImageView? = null
    var imghinhne: ImageView? = null
    var imageViewMuiTen: ImageView? = null
    var imagegiohang: ImageView? = null
    var imgbinhluanUser: ImageView? = null
    var textViewNameProductDetail: TextView? = null
    var textViewPriceProductDetail: TextView? = null
    var textViewDescriptionProductDetail: TextView? = null
    var txtsoluong: TextView? = null
    var txtdaban: TextView? = null
    var txttonkho: TextView? = null
    var txtdiemdanhgia: TextView? = null
    var textviewChitietsanpham: TextView? = null
    var REQUEST_CODE_USER = 123
    var buttonAddCartProductDetail: Button? = null
    var btnTru: Button? = null
    var btnCong: Button? = null
    var btndangnhapchitiet: Button? = null
    var btnShowUser: Button? = null
    var btnExitUser: Button? = null
    var commentAdapter: CommentAdapter? = null
    var commentArrayList: ArrayList<Comment>? = null
    var commentArrayListFilter: ArrayList<Comment>? = null
    var userArrayList: ArrayList<User>? = null
    var listViewComment: ListView? = null
    var binhluanUser: EditText? = null
    var user: User? = null
    var id = 0
    var name = ""
    var price = 0
    var image = ""
    var description = ""
    var idProduct = 0
    var idthuonghieu = 0
    var sosanphamdaban = 0
    var sosanphamtonkho = 0
    var diemdanhgia = ""

    //comment
    var idcm = 0
    var username = ""
    var content = ""
    var tensanpham = ""
    var tensanphambinhluan = ""

    //user
    var idUser = 0
    var usernameuser = ""
    var emailUser = ""
    var addressUser = ""
    var idUserDangNhap = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        init()
        GetDataUsers()
        GetDataProductDetail()
        imageViewMuiTen!!.setImageResource(R.drawable.muiten)
        imagegiohang!!.setImageResource(R.drawable.giohang)
        GetDataComment(tensanphambinhluan)
        Log.e("phong", " binhluan la:$tensanphambinhluan")
        btnCong!!.setOnClickListener {
            val soluong = txtsoluong!!.text.toString()
            var soluongdadoi = soluong.toInt()
            soluongdadoi = soluongdadoi + 1
            txtsoluong!!.text = "" + soluongdadoi
        }
        btnTru!!.setOnClickListener {
            val soluong = txtsoluong!!.text.toString()
            var soluongdadoi = soluong.toInt()
            if (soluongdadoi != 0) {
                soluongdadoi = soluongdadoi - 1
                txtsoluong!!.text = "" + soluongdadoi
            }
        }
        if (btnShowUser!!.text.length != 0) {
            binhluanUser!!.isEnabled = true
        }
        btndangnhapchitiet!!.setOnClickListener {
            val intent = Intent(this@ProductDetailActivity, dangnhap::class.java)
            intent.putExtra("idProductType", 1)
            startActivityForResult(intent, REQUEST_CODE_USER)
        }
        buttonAddCartProductDetail!!.setOnClickListener {
            val chuoi = txtsoluong!!.text.toString()
            val so = chuoi.toInt()
            if (btnShowUser!!.text.toString().length != 0 && btnShowUser!!.text.toString() != null) {
                if (so == 0) {
                    Toast.makeText(this@ProductDetailActivity, "Bạn phải thêm số lượng sản phẩm", Toast.LENGTH_SHORT).show()
                } else {
                    themGioHang()
                }
            } else {
                Toast.makeText(this@ProductDetailActivity, "Bạn chưa đăng nhập ,vui lòng đăng nhập", Toast.LENGTH_SHORT).show()
            }
        }
        imagegiohang!!.setOnClickListener {
            if (btnShowUser!!.text.toString().length != 0 && btnShowUser!!.text.toString() != null) {
                val intent = Intent(this@ProductDetailActivity, CartActicity::class.java)
                intent.putExtra("idUser", idUserDangNhap)
                startActivity(intent)
            } else {
                Toast.makeText(this@ProductDetailActivity, "Bạn chưa đăng nhập ,vui lòng đăng nhập", Toast.LENGTH_SHORT).show()
            }
        }
        imageViewMuiTen!!.setOnClickListener { commentUser() }
        btnExitUser!!.setOnClickListener {
            btnShowUser!!.visibility = View.GONE
            btnExitUser!!.visibility = View.GONE
            binhluanUser!!.isEnabled = false
            btndangnhapchitiet!!.visibility = View.VISIBLE
        }
        btnShowUser!!.setOnClickListener {
            val intent = Intent(this@ProductDetailActivity, InformationUserActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }

    private fun commentUser() {
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(Method.POST, "http://" + "@string/localhost" + "/server/insertComment.php",
                Response.Listener { response ->
                    if (response.trim { it <= ' ' } == "success") {
                        Toast.makeText(this@ProductDetailActivity, "Comment thành công", Toast.LENGTH_SHORT).show()
                        GetDataComment(tensanphambinhluan)
                        binhluanUser!!.setText("")
                    } else {
                        Toast.makeText(this@ProductDetailActivity, "Lỗi comment", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this@ProductDetailActivity, "Da xay ra loi", Toast.LENGTH_SHORT).show()
                    Log.d("aaa", "Loi!\n$error")
                }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["id_user"] = idUserDangNhap.toString() + ""
                params["idsanpham"] = id.toString() + ""
                params["content"] = binhluanUser!!.text.toString()
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                params["ngaycomment"] = sdf.format(Date())
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun themGioHang() {
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(Method.POST, "http://" + "@string/localhost" + "/server/insertSanPham.php",
                Response.Listener { response ->
                    if (response.trim { it <= ' ' } == "success") {
                        Toast.makeText(this@ProductDetailActivity, "Them vào giỏ hang thành công", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ProductDetailActivity, "Lỗi thêm", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this@ProductDetailActivity, "Da xay ra loi", Toast.LENGTH_SHORT).show()
                    Log.d("aaa", "Loi!\n$error")
                }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["id_user"] = idUserDangNhap.toString() + ""
                params["idsanpham"] = id.toString() + ""
                params["soluong"] = txtsoluong!!.text.toString() + ""
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                params["ngaymuahang"] = sdf.format(Date())
                params["price"] = price.toString() + ""
                params["thuonghieu"] = idthuonghieu.toString() + ""
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    fun GetDataComment(tensanphamcobinhluan: String) {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val jsonArrayRequest = JsonArrayRequest("http://" + "@string/localhost" + "/server/getcomment.php", { response ->
            commentArrayList!!.clear()
            commentArrayListFilter!!.clear()
            if (response != null) {
                for (i in 0 until response.length()) {
                    try {
                        val jsonObject = response.getJSONObject(i)
                        idcm = jsonObject.getInt("id")
                        username = jsonObject.getString("username")
                        tensanpham = jsonObject.getString("tensanpham")
                        content = jsonObject.getString("content")
                        commentArrayList!!.add(Comment(id, username, tensanpham, content))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                for (j in commentArrayList!!.indices) {
                    if (tensanphamcobinhluan == commentArrayList!![j].tensanpham) {
                        val idcme: Int = commentArrayList!![j].id
                        val usernamee: String = commentArrayList!![j].username
                        val tensanphame: String = commentArrayList!![j].tensanpham
                        val contente: String = commentArrayList!![j].content
                        commentArrayListFilter!!.add(Comment(idcme, usernamee, tensanphame, contente))
                        commentAdapter?.notifyDataSetChanged()
                    }
                }
            }
        }) { error -> CheckConnection.ShowToast_Short(applicationContext, error.toString()) }
        requestQueue.add(jsonArrayRequest)
    }

    fun GetDataUsers() {
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val jsonArrayRequest = JsonArrayRequest("http://" + "@string/localhost" + "/server/getUser.php", { response ->
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
        }) { error -> CheckConnection.ShowToast_Short(applicationContext, error.toString()) }
        requestQueue.add(jsonArrayRequest)
    }

    private fun GetDataProductDetail() {
        val product: Product? = intent.getSerializableExtra("information") as Product?
        if (product != null) {
            id = product.id
        }
        if (product != null) {
            name = product.nameProduct.toString()
        }
        tensanphambinhluan = name
        if (product != null) {
            price = product.priceProduct!!
        }
        if (product != null) {
            image = product.imageProduct.toString()
        }
        if (product != null) {
            description = product.descriptionProduct.toString()
        }
        if (product != null) {
            idProduct = product.idProduct
        }
        if (product != null) {
            sosanphamdaban = product.sosanphamdaban
        }
        if (product != null) {
            sosanphamtonkho = product.sosanphamcontonkho
        }
        if (product != null) {
            idthuonghieu = product.id_thuonghieu
        }
        if (product != null) {
            diemdanhgia = product.diemdanhgia.toString()
        }
        textViewNameProductDetail!!.text = name
        val decimalFormat = DecimalFormat("###,###,###")
        textViewPriceProductDetail!!.text = "Giá : " + decimalFormat.format(price.toLong()) + " VND "
        textViewDescriptionProductDetail!!.text = description
        Picasso.get().load(image).into(imageViewProductDetail)
        txtdaban!!.text = "Số sản phẩm đã bán : $sosanphamdaban"
        txttonkho!!.text = "Số sản phẩm tồn kho : $sosanphamtonkho"
        txtdiemdanhgia!!.text = diemdanhgia + ""
        imghinhne!!.setImageResource(R.drawable.caybut)
        imgbinhluanUser!!.setImageResource(R.drawable.comment)
    }

    private fun init() {
        btnToolbar = findViewById(R.id.btnToolbar)
        imageViewProductDetail = findViewById(R.id.imageViewProductDetail)
        textViewNameProductDetail = findViewById(R.id.textViewNameProductDetail)
        textViewPriceProductDetail = findViewById(R.id.textViewPriceProductDetail)
        textViewDescriptionProductDetail = findViewById(R.id.textViewDescriptionProductDetail)
        buttonAddCartProductDetail = findViewById(R.id.buttonAddCart)
        imageViewMuiTen = findViewById(R.id.imageMuiTen)
        imgbinhluanUser = findViewById(R.id.iconbinhluan)
        imagegiohang = findViewById(R.id.imagegiohang)
        btndangnhapchitiet = findViewById(R.id.btndangnhapchitiet)
        btnShowUser = findViewById(R.id.showUserChiTiet)
        binhluanUser = findViewById(R.id.binhluanUser)
        txtdaban = findViewById(R.id.textviewSosanphamdaban)
        txttonkho = findViewById(R.id.textviewSosanphamtonkho)
        txtsoluong = findViewById(R.id.textviewSoLuong)
        txtdiemdanhgia = findViewById(R.id.textviewDiemdanhgia)
        imghinhne = findViewById(R.id.imageHinhne)
        btnCong = findViewById(R.id.btnCong)
        btnTru = findViewById(R.id.btnTru)
        listViewComment = findViewById(R.id.listviewComment)
        commentArrayList = ArrayList<Comment>()
        commentArrayListFilter = ArrayList<Comment>()
        commentAdapter = CommentAdapter(this, R.layout.comment, commentArrayListFilter!!)
        with(listViewComment) { this?.setAdapter(commentAdapter) }
        userArrayList = ArrayList<User>()
        btnExitUser = findViewById(R.id.showUserExit)
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
                    btnExitUser!!.visibility = View.VISIBLE
                    btnExitUser!!.text = "Thoát"
                    binhluanUser!!.isEnabled = true
                    btndangnhapchitiet!!.visibility = View.GONE
                    buttonAddCartProductDetail!!.isEnabled = true
                    imagegiohang!!.isEnabled = true
                }
            }
        }
    }
}