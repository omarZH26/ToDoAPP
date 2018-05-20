package com.example.omarz.todoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnlogin = findViewById<View>(R.id.btnlogin) as Button
        btnlogin.setOnClickListener{
            val login = Intent(this,LoginActivity::class.java)
            startActivity(login)
        }
        val register = findViewById<View>(R.id.register) as Button
        register.setOnClickListener{
            val registro = Intent(this,RegistroActivity::class.java)
            startActivity(registro)
        }


    }
}
