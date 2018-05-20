package com.example.omarz.todoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistroActivity : Activity() {


    private lateinit var mDatabase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        mDatabase = FirebaseDatabase.getInstance().reference.child("Users") // reference of Firebase

        val register = findViewById<View>(R.id.register) as Button
        var user = findViewById<EditText>(R.id.nick)
        var pass = findViewById<EditText>(R.id.pasw)

        register.setOnClickListener{
            val users = User.create()
            users.user = user.text.toString()
            users.pass = pass.text.toString()

           mDatabase.child(users.user).setValue(users.pass).addOnCompleteListener{
                if(it.isSuccessful){
                    val main = Intent(this,MainActivity::class.java)
                    startActivity(main)
                    finish()
                }
            }
        }
    }
}
