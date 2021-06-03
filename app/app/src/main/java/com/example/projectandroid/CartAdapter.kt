package com.example.projectandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter(private val data: List<Cart>) : RecyclerView.Adapter<CartAdapter.MyViewHolder>()  {

    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){

        fun bind(cart: Cart){
            val tvType = view.findViewById<TextView>(R.id.tv_type)
            val imgAvatar = view.findViewById<ImageView>(R.id.img_avatar)
            val tvTotal = view.findViewById<TextView>(R.id.tv_total)
            val tvCount = view.findViewById<TextView>(R.id.tv_count)

            tvType.text = cart.name
            tvTotal.text = "Total :" + cart.total
            tvCount.text = "Count :" + cart.count

            Glide.with(view.context).load(cart.link).centerCrop().into(imgAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }


}