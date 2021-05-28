package com.example.projectandroid.adapter
/*
Team 10
 */
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.projectandroid.R
import com.example.projectandroid.activity.ProductDetailActivity
import com.example.projectandroid.model.Product
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.util.*

class DetailProductAdapter(
    context: ProductDetailActivity,
    layout: Int,
    subjectsList: ArrayList<Product>
) :
    BaseAdapter() {
    var x = 0
    private val context: ProductDetailActivity
    private val layout: Int
    private val arrayProduct: ArrayList<Product>
    override fun getCount(): Int {
        return arrayProduct.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    private inner class ViewHolder {
        var imageViewProduct: ImageView? = null
        var textViewNameProduct: TextView? = null
        var textViewPriceProduct: TextView? = null
    }

    override fun getView(i: Int, view: View?, parent: ViewGroup): View? {
        var view = view
        val holder: ViewHolder
        if (view == null) {
            holder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, null)
            holder.imageViewProduct = view.findViewById(R.id.imageViewProduct)
            holder.textViewNameProduct = view.findViewById(R.id.textViewNameProduct)
            holder.textViewPriceProduct = view.findViewById(R.id.textViewPriceProduct)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        val product: Product = arrayProduct[i]
        val x: Int = arrayProduct[i].id
        holder.textViewNameProduct?.setText(product.nameProduct)
        val decimalFormat = DecimalFormat("###,###,###")
        holder.textViewPriceProduct!!.text = "Price : " + decimalFormat
            .format(product.priceProduct) + "VND"
        Picasso.get().load(product.imageProduct)
            .into(holder.imageViewProduct)
        return view
    }

    init {
        this.context = context
        this.layout = layout
        arrayProduct = subjectsList
    }
}
