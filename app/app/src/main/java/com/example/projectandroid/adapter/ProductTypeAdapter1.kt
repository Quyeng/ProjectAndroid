package com.example.projectandroid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectandroid.R
import com.example.projectandroid.activity.*
import com.example.projectandroid.model.ProductType
import com.squareup.picasso.Picasso
import java.util.*

class ProductTypeAdapter1(datashops: ArrayList<ProductType>, context: HomeActivity) :
    RecyclerView.Adapter<ProductTypeAdapter1.ViewHolder>() {
    var datashops: ArrayList<ProductType>
    var context: HomeActivity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.the_loai, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNameType.setText(datashops[position].namePT)
        Picasso.get().load(datashops[position].imagePT).into(holder.imgHinhAnh)
        val productType: ProductType = datashops[position]
        holder.itemView.setOnClickListener {
            when (position) {
                0 -> {
                    val intent = Intent(context, SmartPhoneActivity::class.java)
                    intent.putExtra("idProductType", 0)
                    context.startActivity(intent)
                }
                1 -> {
                    val intent = Intent(context, LaptopActivity::class.java)
                    intent.putExtra("idProductType", 1)
                    context.startActivity(intent)
                }
                2 -> {
                    val intent = Intent(context, ShirtActivity::class.java)
                    intent.putExtra("idProductType", 2)
                    context.startActivity(intent)
                }
                3 -> {
                    val intent = Intent(context, TrousersActivity::class.java)
                    intent.putExtra("idProductType", 3)
                    context.startActivity(intent)
                }
                4 -> {
                    val intent = Intent(context, ShoesActivity::class.java)
                    intent.putExtra("idProductType", 4)
                    context.startActivity(intent)
                }
                5 -> {
                    val intent = Intent(context, WatchActivity::class.java)
                    intent.putExtra("idProductType", 5)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return datashops.size
    }

    fun RemoveItem(position: Int) {
        datashops.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtNameType: TextView
        var imgHinhAnh: ImageView

        init {
            txtNameType = itemView.findViewById(R.id.txtNameType)
            imgHinhAnh = itemView.findViewById(R.id.imgHinhAnh)
        }
    }

    companion object {
        var i = 0
    }

    init {
        this.datashops = datashops
        this.context = context
    }
}