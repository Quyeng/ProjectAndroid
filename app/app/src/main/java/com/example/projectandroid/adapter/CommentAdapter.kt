package com.example.projectandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.projectandroid.R
import com.example.projectandroid.activity.ProductDetailActivity
import com.example.projectandroid.model.Comment
import java.util.*

class CommentAdapter(context: ProductDetailActivity, layout: Int, arrayComment: ArrayList<Comment>) : BaseAdapter() {
    var x = 0
    private val context: ProductDetailActivity
    private val layout: Int
    private val arrayComment: ArrayList<Comment>
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
        var textViewName: TextView? = null
        var textViewnoidung: TextView? = null
    }

    override fun getView(i: Int, view: View, parent: ViewGroup): View {
        var view = view
        val holder: ViewHolder
        if (view == null) {
            holder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, null)
            holder.textViewName = view.findViewById(R.id.tenuserdacomment)
            holder.textViewnoidung = view.findViewById(R.id.noidungdacomment)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        val comment: Comment = arrayComment[i]
        holder.textViewName?.setText(comment.username)
        holder.textViewnoidung?.setText(comment.content + "")
        return view
    }

    init {
        this.context = context
        this.layout = layout
        this.arrayComment = arrayComment
    }
}
