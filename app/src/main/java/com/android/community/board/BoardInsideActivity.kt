package com.android.community.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.android.community.R
import com.android.community.comment.CommentLVAdapter
import com.android.community.comment.CommnetModel
import com.android.community.databinding.ActivityBoardInsideBinding
import com.android.community.utils.FBAuth
import com.android.community.utils.FBRef
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.Exception

class BoardInsideActivity : AppCompatActivity() { //1. listview 데이터 다른 액티비티 전달

    private lateinit var binding : ActivityBoardInsideBinding

    private lateinit var key :String

    private val commentDataList = mutableListOf<CommnetModel>()

    private lateinit var commentAdapter : CommentLVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        binding.boardSettingIcon.setOnClickListener {
            showDialog()
        }

        //1. listview 데이터 다른 액티비티 전달
        /*val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val time = intent.getStringExtra("time")

        binding.titleArea.text = title
        binding.contentArea.text = content
        binding.timeArea.text = time*/

        //2. FB에 있는 데이터 ID기반으로 다시 데이터 받아오기
        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        binding.commentBtn.setOnClickListener {
            insertCommnet(key)
        }

        getCommentData(key)

        commentAdapter = CommentLVAdapter(commentDataList)
        binding.commentLV.adapter = commentAdapter
    }

    fun insertCommnet(key : String) {
        FBRef.commnetRef
            .child(key)
            .push()
            .setValue(
                CommnetModel(
                    binding.commentArea.text.toString(),
                    FBAuth.getTime())
            )
        binding.commentArea.setText("")
    }

    fun getCommentData(key : String) {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                commentDataList.clear()

                for (dataModel in dataSnapshot.children) {
                    val item = dataModel.getValue(CommnetModel::class.java)
                    commentDataList.add(item!!)
                }
                commentAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.commnetRef.child(key).addValueEventListener(postListener)
    }


    private fun showDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/ 삭제")

        val alertDialog =  mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            var intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key",key)
            startActivity(intent)
        }
        alertDialog.findViewById<Button>(R.id.removeBtn)?.setOnClickListener {
            FBRef.boardRef.child(key).removeValue()
            finish()
        }
    }

    private fun getImageData(key: String){
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        //TODO :: 블랙화면 수정 필요
        storageReference.downloadUrl.addOnCompleteListener({task ->
            if (task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            } else {
                binding.getImageArea.isVisible = false
            }
        })

    }

    //2. FB에 있는 데이터 ID기반으로 다시 데이터 받아오기
    private fun getBoardData(key : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)

                    binding.titleArea.text = dataModel!!.title //TODO :: null체크 필요
                    binding.contentArea.text = dataModel!!.content //TODO :: null체크 필요
                    binding.timeArea.text = dataModel!!.time //TODO :: null체크 필요

                    val myUid = FBAuth.getUid()
                    val writerUid = dataModel.uid //글쓴이 UID

                    if (myUid.equals(writerUid)) {//작성자 == 본인
                        binding.boardSettingIcon.isVisible = true
                    } else {
                        Log.i("check","다른 사람글 작성")
                    }

                } catch (e : Exception) {
                    Log.d("check","삭제완료")
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }
}