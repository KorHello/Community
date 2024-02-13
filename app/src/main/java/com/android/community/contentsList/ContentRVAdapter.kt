package com.android.community.contentsList

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.community.R
import com.android.community.utils.FBAuth
import com.android.community.utils.FBRef
import com.bumptech.glide.Glide

class ContentRVAdapter(val context : Context,
                       val items : ArrayList<ContentModel> ,
                       val keyList : ArrayList<String>,
                       val bookmarkIdList : MutableList<String>)
    : RecyclerView.Adapter<ContentRVAdapter.Viewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentRVAdapter.Viewholder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_item, parent, false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: ContentRVAdapter.Viewholder, position: Int) {
        holder.binditem(items[position],keyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun binditem(item: ContentModel, key: String) {

            itemView.setOnClickListener {
                val intent = Intent(context, ContentShowActivity::class.java)
                intent.putExtra("url",item.webUrl)
                itemView.context.startActivity(intent)
            }

            val contenttitle = itemView.findViewById<TextView>(R.id.textArea)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val bookmarkArea = itemView.findViewById<ImageView>(R.id.bookmarkArea)

            if (bookmarkIdList.contains(key)){ // 키 비교하여 체크
                bookmarkArea.setImageResource(R.drawable.bookmark_color)
            } else {
                bookmarkArea.setImageResource(R.drawable.bookmark_white)
            }

            bookmarkArea.setOnClickListener {
                if (bookmarkIdList.contains(key)){ //북마크 있음

                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .removeValue()
                } else { //북마크 없음
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(key)
                        .setValue(BookMarkModel(true)) //북마크 UID값 삽입
                }

            }

            contenttitle.text = item.title
            Glide.with(context)
                .load(item.imageUrl) //경로
                .into(imageViewArea) // 삽입 및 실제 표시

        }
    }

}