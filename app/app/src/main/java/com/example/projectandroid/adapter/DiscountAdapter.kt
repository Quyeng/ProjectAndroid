package com.example.projectandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import com.example.projectandroid.R
import com.example.projectandroid.activity.DiscountActivity
import com.example.projectandroid.model.Discount
import java.util.*

class DiscountAdapter(context: DiscountActivity, layout: Int, arrayComment: ArrayList<Discount>) :
    BaseAdapter() {
    var x = 0
    private val context: DiscountActivity
    private val layout: Int
    private val arrayComment: ArrayList<Discount>
    override fun getCount(): Int {
        return arrayComment.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    private inner class ViewHolder {
        var textViewName: CheckBox? = null
    }

    override fun getView(i: Int, view: View, parent: ViewGroup): View {
        var view = view
        val holder: ViewHolder
        if (view == null) {
            holder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, null)
            holder.textViewName = view.findViewById(R.id.checkboxDiscount)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        val discount: Discount = arrayComment[i]
        holder.textViewName?.setText(discount.name)
        holder.textViewName!!.setOnClickListener {
            if (holder.textViewName!!.isChecked) {
                context.discountSanpham(discount.giadiscount)
            }
        }
        return view
    }

    init {
        this.context = context
        this.layout = layout
        this.arrayComment = arrayComment
    }
}