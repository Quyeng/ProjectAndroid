package com.example.projectandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.projectandroid.R
import com.example.projectandroid.activity.HistoryActivity
import com.example.projectandroid.model.DetailCart
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class DetailCartAdapter(context: HistoryActivity, layout: Int, subjectsList: List<DetailCart>) :
    BaseAdapter() {
    var x = 0
    private val context: HistoryActivity
    private val layout: Int
    private val arraListProduct: List<DetailCart>
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
        var textViewngaymuahang: TextView? = null
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
            viewHolder!!.imageViewSanPham = view.findViewById(R.id.imageHinhSanPhamne)
            viewHolder.textViewNameSanPham = view.findViewById(R.id.textViewNameSanPhamne)
            viewHolder.textViewngaymuahang = view.findViewById(R.id.textviewngaymuahang)
            viewHolder.textViewPrice = view.findViewById(R.id.textviewGiaSanPhamne)
            viewHolder.txtsoluongdamua = view.findViewById(R.id.textviewSoluongdamuane)
            viewHolder.dongcartne = view.findViewById(R.id.dongcartnene)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val cart: DetailCart = arraListProduct[i] as DetailCart
        viewHolder!!.textViewNameSanPham!!.text = "Sản phẩm : " + cart.getTensanpham()
        viewHolder.textViewngaymuahang!!.text = "Ngày mua hàng : " + cart.getNgaymuahang()
        val decimalFormat = DecimalFormat("###,###,###")
        viewHolder.textViewPrice!!.text = "Price : " + decimalFormat
            .format(cart.getGiasanpham()) + " VND"
        viewHolder.txtsoluongdamua!!.text = "Số lượng đã mua : " + cart.getSoluong().toString() + ""
        Picasso.get().load(cart.getHinhanhsanpham())
            .into(viewHolder.imageViewSanPham)


        return view
    }

    init {
        this.context = context
        this.layout = layout
        arraListProduct = subjectsList
    }
}
