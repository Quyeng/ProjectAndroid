package com.example.projectandroid.adapter

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

    override fun getView(i: Int, view: View, parent: ViewGroup): View {
        var view = view
        var viewHolder: ViewHolder? = null
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, null)
            viewHolder!!.checkBox = view.findViewById(R.id.checkboxCart)
            viewHolder.imageViewSanPham = view.findViewById(R.id.imageHinhSanPham)
            viewHolder.textViewNameSanPham = view.findViewById(R.id.textViewNameSanPham)
            viewHolder.textViewThuongHieu = view.findViewById(R.id.textviewThuonghieu)
            viewHolder.textViewPrice = view.findViewById(R.id.textviewGiaSanPham)
            viewHolder.textViewSanPhamConLai = view.findViewById(R.id.textViewSanPhamConLai)
            viewHolder.txtsoluongdamua = view.findViewById(R.id.textviewSoluongdamua)
            viewHolder.btnCong = view.findViewById(R.id.btnCongCart)
            viewHolder.btnTru = view.findViewById(R.id.btntruCart)
            viewHolder.dongcartne = view.findViewById(R.id.dongcartne)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val cart: Cart = arraListProduct[i] as Cart
        viewHolder!!.textViewNameSanPham.setText(cart.getTensanpham())
        viewHolder.textViewThuongHieu.setText(cart.getTenhuonghieu())
        val decimalFormat = DecimalFormat("###,###,###")
        viewHolder.textViewPrice!!.text = "Price : " + decimalFormat
            .format(cart.getGiasanpham()) + " VND"
        viewHolder.textViewSanPhamConLai!!.text =
            "Còn lại " + cart.getSosanphamtonkho().toString() + " sản phẩm"
        viewHolder.txtsoluongdamua.setText(cart.getSoluong().toString() + "")
        Picasso.get().load(cart.getImageSanPham())
            .into(viewHolder.imageViewSanPham)


//        if(context.checkCheckBoxTatCa(1))
//        {
//            viewHolder.checkBox.setChecked(false);
//        }
        if (context.checkCheckBoxTatCaIsTrue()) {
            viewHolder.checkBox!!.isChecked = true
        }
        if (context.checkCheckBoxTatCaIsFalse()) {
            viewHolder.checkBox!!.isChecked = false
        }
        val finalViewHolder2 = viewHolder
        finalViewHolder2.btnCong!!.setOnClickListener {
            val soluong = finalViewHolder2.txtsoluongdamua!!.text.toString()
            var soluongdadoi = soluong.toInt()
            val giuso = soluongdadoi
            soluongdadoi = soluongdadoi + 1
            val sotruyen = soluongdadoi - giuso
            finalViewHolder2.txtsoluongdamua!!.text = "" + soluongdadoi
            context.TongTien(null, cart.getGiasanpham() * sotruyen, 0)
            Log.e("phong", "id don hang la : " + cart.getId())
            Log.e("phong", "so luong don hang la : $soluongdadoi")
            context.CapNhatDonHang(cart.getId(), soluongdadoi)
        }
        finalViewHolder2.btnTru!!.setOnClickListener {
            val soluong = finalViewHolder2.txtsoluongdamua!!.text.toString()
            var soluongdadoi = soluong.toInt()
            val giuso = soluongdadoi
            if (soluongdadoi > 0) {
                soluongdadoi = soluongdadoi - 1
                if (soluongdadoi > 0) {
                    val sotruyen = giuso - soluongdadoi
                    finalViewHolder2.txtsoluongdamua!!.text = "" + soluongdadoi
                    context.TongTien(null, 0, cart.getGiasanpham() * sotruyen)
                    Log.e("phong", "id don hang la : " + cart.getId())
                    Log.e("phong", "so luong don hang la : $soluongdadoi")
                    context.CapNhatDonHang(cart.getId(), soluongdadoi)
                } else {
                    val dialogXoa = AlertDialog.Builder(context)
                    dialogXoa.setMessage(
                        "Ban co muon xoa san phẩm  " + cart.getTensanpham()
                            .toString() + " này ra khỏi đơn hàng khong?"
                    )
                    dialogXoa.setPositiveButton(
                        "Co"
                    ) { dialog, which ->
                        context.deleteMonHoc(cart.getId(), cart.getGiasanpham())
                        context.TongTien(null, 0, cart.getGiasanpham())
                    }
                    dialogXoa.setNegativeButton(
                        "khong"
                    ) { dialog, which -> }
                    dialogXoa.show()
                }
            }
        }
        val finalViewHolder = viewHolder
        viewHolder.dongcartne!!.setOnClickListener {
            if (finalViewHolder.checkBox!!.isChecked) {
                finalViewHolder.checkBox!!.isChecked = false
                context.isFalseTatCa()
                context.TongTien(null, 0, cart.getGiasanpham() * cart.getSoluong())
            } else {
                context.isTrueTatCa(1)
                finalViewHolder.checkBox!!.isChecked = true
                context.TongTien(null, cart.getGiasanpham() * cart.getSoluong(), 0)
            }
        }
        val finalViewHolder1 = viewHolder
        viewHolder.checkBox!!.setOnClickListener {
            if (finalViewHolder1.checkBox!!.isChecked) {
                context.isTrueTatCa(1)
                context.TongTien(null, cart.getGiasanpham() * cart.getSoluong(), 0)
            } else {
                context.isFalseTatCa()
                context.TongTien(null, 0, cart.getGiasanpham() * cart.getSoluong())
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