package com.android.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.android.community.auth.IntroActivity
import com.android.community.utils.FBAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.installations.Utils
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        if(FBAuth.getUid() == null) {
            Log.d("SplashActivity", "null")

            Handler().postDelayed({
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }, 3000)

        } else {
            Log.d("SplashActivity", "not null")

            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 3000)
        }


    }
}