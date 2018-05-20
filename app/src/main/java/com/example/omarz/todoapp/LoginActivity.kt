package com.example.omarz.todoapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : Activity() {


    val users = ArrayList<String>()
    val pass = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        val mDatabaseRef = FirebaseDatabase.getInstance().reference.child("Users")

        mDatabaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(dataSnapshot in dataSnapshot.children){
                    val us = dataSnapshot
                    if(us != null){
                        var u = us.key
                        var p = us.value
                        users.add(u.toString())
                        pass.add(p.toString())
                    }
                }
                var nick = findViewById<EditText>(R.id.nick) as EditText
                var pasw = findViewById<EditText>(R.id.pasw) as EditText


                register.setOnClickListener{
                        var count = 0
                        while (count<users.size){
                            if((nick.text.toString()==users[count]) && (pasw.text.toString()==pass[count])){
                                val log = Intent(this@LoginActivity,ListActivity::class.java)
                                log.putExtra("user",users[count])
                                count=users.size
                                finish()
                                startActivity(log)
                            }
                            else{
                                Toast.makeText(this@LoginActivity, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show()
                                count+=1
                            }

                        }

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }

        })

    }
}
