package com.android.community.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {
    companion object {
        lateinit var myRef : DatabaseReference
        val database = Firebase.database

        val bookmarkRef = database.getReference("bookmark_list")
        val category1 = database.getReference("contents")
        val category2 = database.getReference("contents2")

        val boardRef = database.getReference("board")

        val commnetRef = database.getReference("comment")


    }
}