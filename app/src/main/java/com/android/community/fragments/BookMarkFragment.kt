package com.android.community.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.community.R
import com.android.community.contentsList.BookmarkRVAdapter
import com.android.community.contentsList.ContentModel
import com.android.community.contentsList.ContentRVAdapter
import com.android.community.databinding.FragmentBookmarkBinding
import com.android.community.utils.FBAuth
import com.android.community.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BookMarkFragment : Fragment() {

    private lateinit var binding : FragmentBookmarkBinding

    val bookmarkIdLst = mutableListOf<String>()
    val items = ArrayList<ContentModel>()
    val itemkeyList = ArrayList<String>()

    lateinit var rvAdapter: BookmarkRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bookmark, container, false) // 바인딩은 무조건 최상위 있어야함

        // 북마크한 정보 가져오기
        getBookMarkData()


        rvAdapter = BookmarkRVAdapter(requireContext(), items, itemkeyList, bookmarkIdLst)

        val rv : RecyclerView = binding.bookmarkRV
        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(requireContext(), 2)



        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookMarkFragment_to_homeFragment)
        }

        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookMarkFragment_to_tipFragment)
        }

        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookMarkFragment_to_talkFragment)
        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookMarkFragment_to_storeFragment)
        }

        return binding.root
    }

    private fun getBookMarkData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {
                    bookmarkIdLst.add(dataModel.key.toString())
                }

                getCategoryData()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.bookmarkRef
            .child(FBAuth.getUid())
            .addValueEventListener(postListener)
    }

    private fun getCategoryData() {// 컨텐츠 데이터 가져오기

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(ContentModel::class.java)

                    // 북마크한 정보만 보여주기
                    if(bookmarkIdLst.contains(dataModel.key.toString()))  {
                        items.add(item!!) // null 체크
                        itemkeyList.add(dataModel.key.toString())
                    }
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.category1.addValueEventListener(postListener)
        FBRef.category2.addValueEventListener(postListener)
    }
}