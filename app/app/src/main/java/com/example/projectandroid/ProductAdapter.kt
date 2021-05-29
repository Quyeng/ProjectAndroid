package com.example.projectandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ProductAdapter(private val data: List<Product>) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun bind(product: Product){
            val tvType = view.findViewById<TextView>(R.id.tv_type)
            val imgAvatar = view.findViewById<ImageView>(R.id.img_avatar)
            val tvPrice = view.findViewById<TextView>(R.id.tv_price)

            tvType.text = product.name
            tvPrice.text = product.price

            Glide.with(view.context).load(product.link).centerCrop().into(imgAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }


}


