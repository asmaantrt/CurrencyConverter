package com.example.currencyconverter

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginPageActivity : AppCompatActivity() {

    lateinit var btnLogin : Button
    lateinit var btnNewAccount : Button
    lateinit var EdtTextLogin : TextInputEditText
    lateinit var EdtTextPassword : TextInputEditText
    lateinit var EdtTextUserName : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        btnLogin = findViewById(R.id.Btn_login)
        btnNewAccount = findViewById(R.id.Btn_new_Account)
        EdtTextLogin = findViewById(R.id.Edt_email)
        EdtTextPassword = findViewById(R.id.Edt_pass)
        EdtTextUserName = findViewById(R.id.Edt_userName)


        btnLogin.setOnClickListener {
            var UserName = EdtTextUserName.text.toString()
        var myintent =Intent(this , MainActivity::class.java)
            myintent.putExtra("USER NAME" , UserName)
            startActivity(myintent)
        }
        }
}