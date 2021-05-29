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
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide.with
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with
import com.example.projectandroid.R
import com.example.projectandroid.model.ProductType
import com.squareup.picasso.Picasso
import java.util.*

class ProductTypeAdapter(arrayListPT: ArrayList<ProductType>, context: Context) : BaseAdapter() {
    var arrayListPT: ArrayList<ProductType>
    var context: Context
    override fun getCount(): Int {
        return arrayListPT.size
    }

    override fun getItem(position: Int): Any {
        return arrayListPT[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class ViewHolder {
        lateinit var textViewProductType: TextView
        var imageViewProductType: ImageView? = null
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View? {
        var view = view
        var viewHolder: ViewHolder? = null
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_list_view_product_type, null)
            viewHolder.textViewProductType =
                view.findViewById<View>(R.id.textViewProductType) as TextView
            viewHolder.imageViewProductType =
                view.findViewById<View>(R.id.imageViewProductType) as ImageView
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val productType: ProductType = getItem(position) as ProductType
        viewHolder.textViewProductType.setText(productType.namePT)
        Picasso.get().load(productType.imagePT).into(viewHolder.imageViewProductType)
        return view
    }

    init {
        this.arrayListPT = arrayListPT
        this.context = context
    }
}
