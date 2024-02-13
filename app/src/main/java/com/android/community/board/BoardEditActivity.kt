package com.android.community.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.android.community.R
import com.android.community.databinding.ActivityBoardEditBinding
import com.android.community.utils.FBAuth
import com.android.community.utils.FBRef
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.lang.Exception

class BoardEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardEditBinding

    private lateinit var key : String

    private lateinit var writeUid : String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)

        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        binding.editeBtn.setOnClickListener {
            editBoardData(key)
        }

    }

    private fun editBoardData(key :String) {

        FBRef.boardRef
            .child(key)
            .setValue(
                BoardModel(binding.titleArea.toString(),
                    binding.contentArea.text.toString(),
                    writeUid,
                    FBAuth.getTime())
            )
    }

    private fun getImageData(key: String) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

        // ImageView in your Activity
        val imageViewFromFB = binding.imageArea

        //TODO :: 블랙화면 수정 필요
        storageReference.downloadUrl.addOnCompleteListener({ task ->
            if (task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            } else {

            }
        })
    }

    //2. FB에 있는 데이터 ID기반으로 다시 데이터 받아오기
    private fun getBoardData(key: String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {
                    val dataModel = dataSnapshot.getValue(BoardModel::class.java)

                    binding.titleArea.setText(dataModel?.title ) //TODO :: null체크 필요
                    binding.contentArea.setText(dataModel?.content ) //TODO :: null체크 필요
                    writeUid = dataModel!!.uid


                } catch (e: Exception) {
                    Log.d("check", "삭제완료")
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(
                    "ContentListActivity",
                    "loadPost:onCancelled",
                    databaseError.toException()
                )
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }
}