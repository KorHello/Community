package com.android.community.board

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.android.community.R
import com.android.community.utils.FBAuth

class BoardListLVAdapter(val boardList : MutableList<BoardModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(positon: Int): Any {
        return boardList[positon]
    }

    override fun getItemId(positon: Int): Long {
        return positon.toLong()
    }

    override fun getView(positon: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        view = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item, parent,false)

        val itemLinearLayoutView  = view?.findViewById<LinearLayout>(R.id.itemView)
        val title = view?.findViewById<TextView>(R.id.titleArea)
        val content = view?.findViewById<TextView>(R.id.contentArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)

        if (boardList[positon].uid.equals(FBAuth.getUid())) { // 작성자가 본인이라면
            itemLinearLayoutView?.setBackgroundColor(Color.parseColor("#ffa500"))
        }

        title!!.text = boardList[positon].title
        content!!.text = boardList[positon].content
        time!!.text = boardList[positon].time

        return view!!

    }
}