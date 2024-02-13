package com.android.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.android.community.auth.IntroActivity
import com.android.community.setting.SettingActivity
import com.android.community.utils.Utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        findViewById<ImageView>(R.id.settingBtn).setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        Utils.log("check","Hello World")

      /*  findViewById<Button>(R.id.logoutBtn).setOnClickListener{
            auth.signOut() // 로그아웃

            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP //기존 액티비티 날려버림
            startActivity(intent)

        }*/
    }
}