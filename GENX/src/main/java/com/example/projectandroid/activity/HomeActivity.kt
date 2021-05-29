package com.example.projectandroid.activity
/*
Team 10
 */
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.ViewFlipper
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.projectandroid.R
import com.example.projectandroid.activity.*
import com.example.projectandroid.adapter.ProductAdapter
import com.example.projectandroid.adapter.ProductTypeAdapter
import com.example.projectandroid.adapter.ProductTypeAdapter1
import com.example.projectandroid.model.Product
import com.example.projectandroid.model.ProductType
import com.example.projectandroid.ultil.CheckConnection
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import java.util.ArrayList


class HomeActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    var toolbar: Toolbar? = null
    var viewFlipper: ViewFlipper? = null
    lateinit var listViewProduct: ListView
    var navigationView: NavigationView? = null
    lateinit var listViewmanhinhchinh: ListView
    var drawerLayout: DrawerLayout? = null
    var arrayListProductType: ArrayList<ProductType>? = null
    var productTypeAdapter: ProductTypeAdapter? = null
    var arrayListProductType1: ArrayList<ProductType>? = null
    var productTypeAdapter1: ProductTypeAdapter1? = null
    var id = 0
    var namePT = ""
    var imagePT = ""
    var arrayListProduct: ArrayList<Product>? = null
    var productAdapter: ProductAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        AnhXa()
        if (CheckConnection.haveNetworkConnection(applicationContext)) {
            ActionBart()
            ActionViewFliper()
            GetDataProductType()
            GetDataProductNew()
            OnClickItemListView()
        } else {
            CheckConnection.ShowToast_Short(
                applicationContext,
                "Please check the connection again!"
            )
            finish()
        }
    }

    private fun OnClickItemListView() {
        listViewmanhinhchinh!!.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                when (position) {
                    0 -> {
                        if (CheckConnection.haveNetworkConnection(application)) {
                            val intent = Intent(this@HomeActivity, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            CheckConnection.ShowToast_Short(
                                applicationContext,
                                "Please check the connection again!"
                            )
                        }
                        drawerLayout!!.closeDrawer(GravityCompat.START)
                    }
                    1 -> {
                        if (CheckConnection.haveNetworkConnection(application)) {
                            val intent = Intent(this@HomeActivity, SmartPhoneActivity::class.java)
                            intent.putExtra("idProductType", 1)
                            startActivity(intent)
                        } else {
                            CheckConnection.ShowToast_Short(
                                applicationContext,
                                "Please check the connection again!"
                            )
                        }
                        drawerLayout!!.closeDrawer(GravityCompat.START)
                    }
                    2 -> {
                        if (CheckConnection.haveNetworkConnection(application)) {
                            val intent = Intent(this@HomeActivity, LaptopActivity::class.java)
                            intent.putExtra("idProductType", 2)
                            startActivity(intent)
                        } else {
                            CheckConnection.ShowToast_Short(
                                applicationContext,
                                "Please check the connection again!"
                            )
                        }
                        drawerLayout!!.closeDrawer(GravityCompat.START)
                    }
                    3 -> {
                        if (CheckConnection.haveNetworkConnection(application)) {
                            val intent = Intent(this@HomeActivity, ShirtActivity::class.java)
                            intent.putExtra("idProductType", 3)
                            startActivity(intent)
                        } else {
                            CheckConnection.ShowToast_Short(
                                applicationContext,
                                "Please check the connection again!"
                            )
                        }
                        drawerLayout!!.closeDrawer(GravityCompat.START)
                    }
                    4 -> {
                        if (CheckConnection.haveNetworkConnection(application)) {
                            val intent = Intent(this@HomeActivity, TrousersActivity::class.java)
                            intent.putExtra("idProductType", 4)
                            startActivity(intent)
                        } else {
                            CheckConnection.ShowToast_Short(
                                applicationContext,
                                "Please check the connection again!"
                            )
                        }
                        drawerLayout!!.closeDrawer(GravityCompat.START)
                    }
                    5 -> {
                        if (CheckConnection.haveNetworkConnection(application)) {
                            val intent = Intent(this@HomeActivity, ShoesActivity::class.java)
                            intent.putExtra("idProductType", 5)
                            startActivity(intent)
                        } else {
                            CheckConnection.ShowToast_Short(
                                applicationContext,
                                "Please check the connection again!"
                            )
                        }
                        drawerLayout!!.closeDrawer(GravityCompat.START)
                    }
                    6 -> {
                        if (CheckConnection.haveNetworkConnection(application)) {
                            val intent = Intent(this@HomeActivity, WatchActivity::class.java)
                            intent.putExtra("idProductType", 6)
                            startActivity(intent)
                        } else {
                            CheckConnection.ShowToast_Short(
                                applicationContext,
                                "Please check the connection again!"
                            )
                        }
                        drawerLayout!!.closeDrawer(GravityCompat.START)
                    }
                    7 -> {
                        if (CheckConnection.haveNetworkConnection(application)) {
                            val intent = Intent(this@HomeActivity, InformationTeam::class.java)
                            intent.putExtra("idProductType", 7)
                            startActivity(intent)
                        } else {
                            CheckConnection.ShowToast_Short(
                                applicationContext,
                                "Please check the connection again!"
                            )
                        }
                        drawerLayout!!.closeDrawer(GravityCompat.START)
                    }
                    8 -> {
                        if (CheckConnection.haveNetworkConnection(application)) {
                            val intent = Intent(this@HomeActivity, HistoryActivity::class.java)
                            startActivity(intent)
                        } else {
                            CheckConnection.ShowToast_Short(
                                applicationContext,
                                "Please check the connection again!"
                            )
                        }
                        drawerLayout!!.closeDrawer(GravityCompat.START)
                    }
                }
            }
    }

    private fun GetDataProductNew() {
        val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        val jsonArrayRequest = JsonArrayRequest("http://192.168.1.6:8080/server/getsanphammoinhat.php", object :
            Response.Listener<JSONArray?> {
            override fun onResponse(response: JSONArray?) {
                if (response != null) {
                    var Id = 0
                    var nameProduct = ""
                    var priceProduct = 0
                    var imageProduct = ""
                    var descriptionProduct = ""
                    var IdProduct = 0
                    var idthuonghieu = 0
                    var sosanphamdaban = 0
                    var sosanphamtonkho = 0
                    var diemdanhgia = ""
                    for (i in 0 until response.length()) {
                        try {
                            val jsonObject = response.getJSONObject(i)
                            Id = jsonObject.getInt("id")
                            nameProduct = jsonObject.getString("tensp")
                            priceProduct = jsonObject.getInt("giasp")
                            imageProduct = jsonObject.getString("hinhanhsp")
                            descriptionProduct = jsonObject.getString("motasp")
                            IdProduct = jsonObject.getInt("idsanpham")
                            idthuonghieu = jsonObject.getInt("id_thuonghieu")
                            sosanphamtonkho = jsonObject.getInt("sosanphamtonkho")
                            sosanphamdaban = jsonObject.getInt("sosanphamdaban")
                            diemdanhgia = jsonObject.getString("diemdanhgia")
                            arrayListProduct!!.add(
                                Product(
                                    Id,
                                    nameProduct,
                                    priceProduct,
                                    imageProduct,
                                    descriptionProduct,
                                    IdProduct,
                                    idthuonghieu,
                                    sosanphamdaban,
                                    sosanphamtonkho,
                                    diemdanhgia
                                )
                            )
                            productAdapter?.notifyDataSetChanged()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                CheckConnection.ShowToast_Short(applicationContext, error.toString())
            }
        })
        requestQueue.add(jsonArrayRequest)
    }

    private fun GetDataProductType() {
        val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        val jsonArrayRequest = JsonArrayRequest("http://192.168.1.6:8080/server/getloaisp.php", object :
            Response.Listener<JSONArray?> {
            override fun onResponse(response: JSONArray?) {
                if (response != null) {
                    for (i in 0 until response.length()) {
                        try {
                            val jsonObject = response.getJSONObject(i)
                            id = jsonObject.getInt("id")
                            namePT = jsonObject.getString("tenloaisp")
                            imagePT = jsonObject.getString("hinhanhloaisanpham")
                            arrayListProductType!!.add(ProductType(id, namePT, imagePT))
                            productTypeAdapter?.notifyDataSetChanged()
                            arrayListProductType1!!.add(ProductType(id, namePT, imagePT))
                            productTypeAdapter1?.notifyDataSetChanged()
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    arrayListProductType!!.add(
                        ProductType(
                            7,
                            "Liên Hệ Shop",
                            "https://cdn1.iconfinder.com/data/icons/mix-color-3/502/Untitled-12-512.png"
                        )
                    )
                    arrayListProductType!!.add(
                        ProductType(
                            8,
                            "Lịch Sử Mua Hàng",
                            "http://icons.iconarchive.com/icons/graphicloads/colorful-long-shadow/128/User-group-icon.png"
                        )
                    )
                }
            }
        }, object : Response.ErrorListener {
            override fun onErrorResponse(error: VolleyError) {
                CheckConnection.ShowToast_Short(applicationContext, error.toString())
            }
        })
        requestQueue.add(jsonArrayRequest)
    }

    private fun ActionViewFliper() {
        val mangquangcao = ArrayList<String>()
        mangquangcao.add("https://www.pos365.vn/storage/app/media/2019/10/quay-thu-ngan-shop-quan-ao/5.png")
        mangquangcao.add("https://fashionminhthu.com.vn/wp-content/uploads/2019/01/shop-ban-quan-ao-nam-dep-tphcm-yame-shop.jpg")
        mangquangcao.add("https://list.vn/wp-content/uploads/2018/11/th%E1%BB%9Di-trang-50.jpg")
        mangquangcao.add("https://media3.scdn.vn/img3/2019/7_3/SLrLFu_simg_de2fe0_500x500_maxb.jpg")
        mangquangcao.add("https://media.kenhtuyensinh.vn/images/cms/2018/08/top-5-shop-quan-ao-nam-re-dep-cho-sinh-vien-o-tphcm-3.jpg")
        mangquangcao.add("https://sakurafashion.vn/upload/images_294/54432364_2356989251180238_5168159053792149504_n.jpg")
        for (i in mangquangcao.indices) {
            val imageView = ImageView(applicationContext)
            Picasso.get().load(mangquangcao[i]).into(imageView)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            viewFlipper!!.addView(imageView)
        }
        viewFlipper!!.flipInterval = 5000
        viewFlipper!!.isAutoStart = true
        val animation_slide_in = AnimationUtils.loadAnimation(
            applicationContext, R.anim.slide_in_right
        )
        val animation_slide_out = AnimationUtils.loadAnimation(
            applicationContext, R.anim.slide_out_right
        )
        viewFlipper!!.inAnimation = animation_slide_in
        viewFlipper!!.inAnimation = animation_slide_out
    }

    private fun ActionBart() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar!!.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size)
        toolbar!!.setNavigationOnClickListener { drawerLayout!!.openDrawer(GravityCompat.START) }
    }

    private fun AnhXa() {
        recyclerView = findViewById(R.id.listviewTheLoai)
        toolbar = findViewById(R.id.btnShowUser)
        viewFlipper = findViewById(R.id.viewlipper)
        listViewProduct = findViewById(R.id.ListviewProduct)
        navigationView = findViewById(R.id.navigationview)
        listViewmanhinhchinh = findViewById(R.id.listviewManhinhchinh)
        drawerLayout = findViewById(R.id.drawerlayout)
        arrayListProductType = ArrayList<ProductType>()
        arrayListProductType!!.add(
            0,
            ProductType(
                0,
                "Trang Chủ",
                "https://tse4.mm.bing.net/th?id=OIP.JCCq1sFqS_2xfar05oek_gHaHa&pid=Api&P=0&w=300&h=300"
            )
        )
        productTypeAdapter = ProductTypeAdapter(arrayListProductType!!, applicationContext)
        listViewmanhinhchinh.setAdapter(productTypeAdapter)
        arrayListProduct = ArrayList<Product>()
        productAdapter = ProductAdapter(this, R.layout.item_product_new, arrayListProduct!!)
        listViewProduct.setAdapter(productAdapter)
        arrayListProductType1 = ArrayList<ProductType>()
        productTypeAdapter1 = ProductTypeAdapter1(arrayListProductType1!!, this)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.setLayoutManager(layoutManager)
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL)
        val dividerItemDecoration1 =
            DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL)
        val drawable = ContextCompat.getDrawable(
            applicationContext, R.drawable.custom_mau
        )
        dividerItemDecoration.setDrawable(drawable!!)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setItemAnimator(DefaultItemAnimator())
        recyclerView.setAdapter(productTypeAdapter1)
    }
}