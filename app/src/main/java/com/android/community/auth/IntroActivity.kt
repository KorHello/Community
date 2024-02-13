package com.android.community.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.community.MainActivity
import com.android.community.R
import com.android.community.databinding.ActivityIntroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO :: 공통 함수, 변수, 클래스 정리
class IntroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIntroBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro) // binding작업

        binding.loginBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.joinBtn.setOnClickListener{
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        binding.noAccountBtn.setOnClickListener{
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //기존 액티비티 날려버림
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, "Fail", Toast.LENGTH_LONG).show()
                    }
                }


        }

    }
}