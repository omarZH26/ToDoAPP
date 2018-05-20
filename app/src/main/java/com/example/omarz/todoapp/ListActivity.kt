package com.example.omarz.todoapp

import android.app.Activity
import android.os.Bundle

class ListActivity : Activity() {


    private var user = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        user = intent.extras.getString("user")

        





    }
}
