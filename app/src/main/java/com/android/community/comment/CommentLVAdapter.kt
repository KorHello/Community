package com.android.community.comment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.android.community.R
import com.android.community.utils.FBAuth

class CommentLVAdapter(val commentList : MutableList<CommnetModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return commentList.size
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = conver tView

        if (view == null)
        view = LayoutInflater.from(parent?.context).inflate(R.layout.comment_list_item, parent,false)

        val title = view?.findViewById<TextView>(R.id.titleArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)


        title!!.text = commentList[position].commentTitle
        time!!.text = commentList[position].commnetCreateTime

        return view!!


    }
}