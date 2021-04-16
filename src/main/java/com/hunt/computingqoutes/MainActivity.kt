package com.hunt.computingqoutes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
     lateinit var dbHelper:MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = MyDBHelper(this)
        addPerson.setOnClickListener {
            startActivity(Intent(this,AddPerson::class.java))
        }
        loadPersons()
    }

    private fun loadPersons() {
        val adaperPerson = AdapterInspiringPerson(this,dbHelper.getAllPersons())
        recycler_view.adapter = adaperPerson
    }

    override fun onResume() {
        super.onResume()
        loadPersons()
    }


}