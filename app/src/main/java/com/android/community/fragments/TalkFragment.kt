package com.android.community.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.community.R
import com.android.community.board.BoardInsideActivity
import com.android.community.board.BoardListLVAdapter
import com.android.community.board.BoardModel
import com.android.community.board.BoardWriteActivity
import com.android.community.contentsList.BookmarkRVAdapter
import com.android.community.databinding.FragmentTalkBinding
import com.android.community.databinding.FragmentTipBinding
import com.android.community.utils.FBAuth
import com.android.community.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TalkFragment : Fragment() {

    private lateinit var binding : FragmentTalkBinding

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>() //2. FB에 있는 데이터 ID기반으로 다시 데이터 받아오기

    private lateinit var boardRVAdapter : BoardListLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_talk, container, false)

        boardRVAdapter = BoardListLVAdapter(boardDataList)
        binding.boardListView.adapter = boardRVAdapter


        binding.boardListView.setOnItemClickListener { parent, view, position, id ->
            //1. listview 데이터 다른 액티비티 전달
            /*val intent = Intent(context, BoardInsideActivity::class.java)
            intent.putExtra("title",boardDataList[position].title)
            intent.putExtra("content",boardDataList[position].content)
            intent.putExtra("time",boardDataList[position].time)
            startActivity(intent)*/

            //2. FB에 있는 데이터 ID기반으로 다시 데이터 받아오기
            val intent = Intent(context, BoardInsideActivity::class.java)
            intent.putExtra("key",boardKeyList[position])
            startActivity(intent)
        }





        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }

        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookMarkFragment)
        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }
        getFBBoardData()
        return binding.root
    }

    private fun getFBBoardData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()

                for (dataModel in dataSnapshot.children) {

                    //dataModel.key

                    val item = dataModel.getValue(BoardModel::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString()) //2. FB에 있는 데이터 ID기반으로 다시 데이터 받아오기
                }
                boardKeyList.reverse()  //내림차순
                boardDataList.reverse() //내림차순
                boardRVAdapter.notifyDataSetChanged()
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.addValueEventListener(postListener)

    }

}