package com.android.community.auth

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.community.MainActivity
import com.android.community.R
import com.android.community.databinding.ActivityIntroBinding
import com.android.community.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    private lateinit var binding : ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_join)

        auth = Firebase.auth

        binding.joinBtn.setOnClickListener{

            var isGoToJoin = true

            val email = binding.emailArea.text.toString()
            val password1 = binding.passwordArea1.text.toString()
            val password2 = binding.passwordArea2.text.toString()

            //null 값 체크
            if(email.isEmpty()){
                isGoToJoin = false
            }
            if(password1.isEmpty()) {
                isGoToJoin = false
            }
            if(password1.length < 6) {
                isGoToJoin = false
            }
            if(password2.isEmpty()) {
                isGoToJoin = false
            }
            if(!password1.equals(password2)) {
                isGoToJoin = false
            }

            if (isGoToJoin) {
                auth.createUserWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Sussces", Toast.LENGTH_LONG).show()

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
}