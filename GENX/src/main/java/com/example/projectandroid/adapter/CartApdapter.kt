package com.example.projectandroid.adapter
/*
Team 10
 */
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.projectandroid.R
import com.example.projectandroid.activity.CartActicity
import com.example.projectandroid.model.Cart
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class CartApdapter(context: CartActicity, layout: Int, subjectsList: List<Cart>) :
    BaseAdapter() {
    var x = 0
    private val context: CartActicity
    private val layout: Int
    private val arraListProduct: List<Cart>
    override fun getCount(): Int {
        return arraListProduct.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    private inner class ViewHolder {
        var checkBox: CheckBox? = null
        var textViewNameSanPham: TextView? = null
        var textViewPrice: TextView? = null
        var textViewThuongHieu: TextView? = null
        var textViewSanPhamConLai: TextView? = null
        var txtsoluongdamua: TextView? = null
        var imageViewSanPham: ImageView? = null
        var btnCong: Button? = null
        var btnTru: Button? = null
        var dongcartne: LinearLayout? = null
    }

    override fun getView(i: Int, view: View?, parent: ViewGroup?): View?{
        val view = view
        val viewHolder: ViewHolder?
        viewHolder = view?.tag as ViewHolder?
        val cart: Cart = arraListProduct[i]
        viewHolder?.textViewNameSanPham?.setText(cart.tensanpham)
        viewHolder?.textViewThuongHieu?.setText(cart.tenhuonghieu)
        val decimalFormat = DecimalFormat("###,###,###")
        viewHolder?.textViewPrice?.text = "Price : " + decimalFormat
            .format(cart.giasanpham) + " VND"
        viewHolder?.textViewSanPhamConLai?.text =
            "Còn lại " + cart.sosanphamtonkho.toString() + " sản phẩm"
        viewHolder?.txtsoluongdamua?.setText(cart.soluong.toString() + "")
//        Picasso.get().load(cart.imageSanPham).into(viewHolder!!.imageViewSanPham)


        if (context.checkCheckBoxTatCaIsTrue()) {
            viewHolder?.checkBox!!.isChecked = true
        }
        if (context.checkCheckBoxTatCaIsFalse()) {
            viewHolder?.checkBox!!.isChecked = false
        }
        val finalViewHolder2 = viewHolder
        finalViewHolder2?.btnCong!!.setOnClickListener {
            val soluong = finalViewHolder2?.txtsoluongdamua!!.text.toString()
            var soluongdadoi = soluong.toInt()
            val giuso = soluongdadoi
            soluongdadoi = soluongdadoi + 1
            val sotruyen = soluongdadoi - giuso
            finalViewHolder2?.txtsoluongdamua!!.text = "" + soluongdadoi
            context.TongTien(null, cart.giasanpham * sotruyen, 0)
            Log.e("vpq", "id don hang la : " + cart.id)
            Log.e("vpq", "so luong don hang la : $soluongdadoi")
            context.CapNhatDonHang(cart.id, soluongdadoi)
        }
        finalViewHolder2?.btnTru!!.setOnClickListener {
            val soluong = finalViewHolder2?.txtsoluongdamua!!.text.toString()
            var soluongdadoi = soluong.toInt()
            val giuso = soluongdadoi
            if (soluongdadoi > 0) {
                soluongdadoi = soluongdadoi - 1
                if (soluongdadoi > 0) {
                    val sotruyen = giuso - soluongdadoi
                    finalViewHolder2?.txtsoluongdamua!!.text = "" + soluongdadoi
                    context.TongTien(null, 0, cart.giasanpham * sotruyen)
                    Log.e("vpq", "id don hang la : " + cart.id)
                    Log.e("vpq", "so luong don hang la : $soluongdadoi")
                    context.CapNhatDonHang(cart.id, soluongdadoi)
                } else {
                    val dialogXoa = AlertDialog.Builder(context)
                    dialogXoa.setMessage(
                        "Ban co muon xoa san phẩm  " + cart.tensanpham
                            .toString() + " này ra khỏi đơn hàng khong?"
                    )
                    dialogXoa.setPositiveButton(
                        "YES"
                    ) { dialog, which ->
                        context.deleteMonHoc(cart.id, cart.giasanpham)
                        context.TongTien(null, 0, cart.giasanpham)
                    }
                    dialogXoa.setNegativeButton(
                        "NO"
                    ) { dialog, which -> }
                    dialogXoa.show()
                }
            }
        }
        val finalViewHolder = viewHolder
        viewHolder?.dongcartne!!.setOnClickListener {
            if (finalViewHolder?.checkBox!!.isChecked) {
                finalViewHolder?.checkBox!!.isChecked = false
                context.isFalseTatCa
                context.TongTien(null, 0, cart.giasanpham * cart.soluong)
            } else {
                context.isTrueTatCa(1)
                finalViewHolder?.checkBox!!.isChecked = true
                context.TongTien(null, cart.giasanpham * cart.soluong, 0)
            }
        }
        val finalViewHolder1 = viewHolder
        viewHolder?.checkBox!!.setOnClickListener {
            if (finalViewHolder1?.checkBox!!.isChecked) {
                context.isTrueTatCa(1)
                context.TongTien(null, cart.giasanpham * cart.soluong, 0)
            } else {
                context.isFalseTatCa
                context.TongTien(null, 0, cart.giasanpham * cart.soluong)
            }
        }
        return view
    }

    init {
        this.context = context
        this.layout = layout
        arraListProduct = subjectsList
    }
}