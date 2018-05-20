package com.example.omarz.todoapp

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.app.AlertDialog
import android.widget.EditText
import android.graphics.PorterDuff
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ListActivity : Activity() {

    var mAdapter: ArrayAdapter<String>? = null
    lateinit var lstTask: ListView
    private var user = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //dbHelper = DbHelper(this)

        lstTask = findViewById<View>(R.id.lstTask) as ListView

        loadTaskList()
    }

    private fun loadTaskList() {
        user = intent.extras.getString("user")
        val toDo = ArrayList<String>()
        val mDatabase = FirebaseDatabase.getInstance().reference.child("List").child(user)
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(dataSnapshot in dataSnapshot.children){
                    val lis = dataSnapshot.key
                    if(lis != null){
                        toDo.add(lis.toString())
                    }
                }

               // val taskList = dbHelper.taskList
                if (mAdapter == null) {
                    mAdapter = ArrayAdapter(this@ListActivity, R.layout.row, R.id.task_title, toDo)
                    lstTask.adapter = mAdapter
                } else {
                    mAdapter!!.clear()
                    mAdapter!!.addAll(toDo)
                    mAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        //Change menu icon color
        val icon = menu.getItem(0).icon
        icon.mutate()
        icon.setColorFilter(resources.getColor(android.R.color.white), PorterDuff.Mode.SRC_IN)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_task -> {
                val taskEditText = EditText(this)
                val dialog = AlertDialog.Builder(this)
                        .setTitle("Añadir nueva tarea")
                        .setMessage("¿Qué quieres hacer luego?")
                        .setView(taskEditText)
                        .setPositiveButton("Añadir") { dialog, which ->
                            val user = intent.extras.getString("user")
                            val mDatabase = FirebaseDatabase.getInstance().reference.child("List").child(user)
                            val task = taskEditText.text.toString()
                            mDatabase.child(task).setValue("")
                            loadTaskList()
                        }
                        .setNegativeButton("Cancelar", null)
                        .create()
                dialog.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteTask(view: View) {
        val user = intent.extras.getString("user")
        val parent = view.parent as View
        val taskTextView = parent.findViewById<TextView>(R.id.task_title) as TextView
        Log.e("String", taskTextView.text as String)
        val task = taskTextView.text.toString()
        val mDatabase = FirebaseDatabase.getInstance().reference.child("List").child(user)
        val currentUserBD = mDatabase.child(task)
        currentUserBD.removeValue()
       // println("Here $task")
       // dbHelper.deleteTask(task)
        loadTaskList()
    }


}
