package com.android.community.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.android.community.R
import com.android.community.contentsList.BookMarkModel
import com.android.community.databinding.ActivityBoardWriteBinding
import com.android.community.databinding.FragmentTalkBinding
import com.android.community.utils.FBAuth
import com.android.community.utils.FBRef
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardWriteBinding

    private var isImageUpload = false

    var test : String?  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            val key = FBRef.boardRef.push().key.toString() //키 먼저 생성

            FBRef.boardRef
                .child(key) //게시글의 키값을 기준으로 작성 DB로 설명하면 기본키 같은 개념
                .setValue(BoardModel(title, content, uid, time))

            //이미지의 이름을 문서의 key으로 해서 이미지에 대한 정보 를 찾기 쉽게함
            imageUpLoad(key)//key값으로 데이터 동기화

            if (isImageUpload ) {
                imageUpLoad(key)
            }

            finish()
        }

        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI )
            isImageUpload = true
            startActivityForResult(gallery, 100)
        }

    }

    private fun imageUpLoad(key : String) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".png") //이미지 지정

        val imageView = binding.imageArea
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {//갤러리 데이터 받아오기
            binding.imageArea.setImageURI(data?.data) //옵셔널 필수
        }
    }
}