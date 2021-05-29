package com.example.projectandroid.adapter
/*
Team 10
 */
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.projectandroid.R
import com.example.projectandroid.activity.ProductDetailActivity
import com.example.projectandroid.activity.TrousersActivity
import com.example.projectandroid.model.Product
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class TrousersAdapter(context: TrousersActivity, layout: Int, subjectsList: List<Product>) :
    BaseAdapter() {
    var x = 0
    private val context: TrousersActivity
    private val layout: Int
    private val arraListProduct: List<Product>
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
        var textViewNamePhone: TextView? = null
        var textViewPricePhone: TextView? = null
        var textViewDescriptionPhone: TextView? = null
        var imageViewPhone: ImageView? = null
        var linearLayoutPhone: LinearLayout? = null
    }

    override fun getView(i: Int, view: View?, parent: ViewGroup): View? {
        var view = view
        var viewHolder: ViewHolder? = null
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, null)
            viewHolder!!.textViewNamePhone = view.findViewById(R.id.textViewNamePhone)
            viewHolder.textViewPricePhone = view.findViewById(R.id.textViewPricePhone)
            viewHolder.textViewDescriptionPhone = view.findViewById(R.id.textViewDescriptionPhone)
            viewHolder.imageViewPhone = view.findViewById(R.id.imageViewPhone)
            viewHolder.linearLayoutPhone = view.findViewById(R.id.linealayoutItem)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val product: Product = arraListProduct[i] as Product
        viewHolder!!.textViewNamePhone?.setText(product.nameProduct)
        viewHolder.textViewNamePhone!!.isSelected = true
        val decimalFormat = DecimalFormat("###,###,###")
        viewHolder.textViewPricePhone!!.text = "Price : " + decimalFormat
            .format(product.priceProduct) + " VND"
        viewHolder.textViewDescriptionPhone!!.maxLines = 2
        viewHolder.textViewDescriptionPhone!!.ellipsize = TextUtils.TruncateAt.END
        viewHolder.textViewDescriptionPhone!!.setText(product.descriptionProduct)
        Picasso.get().load(product.imageProduct)
            .into(viewHolder.imageViewPhone)
        viewHolder.linearLayoutPhone!!.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("information", product)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        return view
    }

    init {
        this.context = context
        this.layout = layout
        arraListProduct = subjectsList
    }
}